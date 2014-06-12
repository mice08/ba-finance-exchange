package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;
import com.dianping.finance.common.util.LionConfigUtils;
import com.dianping.finance.common.util.ListUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-2-19
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
public class ImportRefundAjaxAction extends AjaxBaseAction {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.ImportRefundAjaxAction");

    private static final String INVALID_REFUND_FILE_MSG = "导入退票文件格式错误！正确格式为两列，第一列为退票付款单号，第二列为退票原因。";

    private File refundFile;

    private String errorExcelIds;

    private Map<String,String> invalidRefundMap = new HashMap<String, String>();

    private String excelInvalidMsg = "";

    private int code;

    private Map<String, Object> msg = new HashMap<String, Object>();

    private ExecutorService executorService;

    private PayOrderService payOrderService;

    public String importRefund() throws Exception {
        int loginId = getLoginId();
        try {
            List<RefundDTO> refundDTOList = readExcel();
            if (CollectionUtils.isEmpty(refundDTOList)) {
                excelInvalidMsg = INVALID_REFUND_FILE_MSG;
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            StringBuilder sb = new StringBuilder();
            if (!isRefundInfoValid(refundDTOList, sb)) {
                excelInvalidMsg = sb.toString();
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            handleRefundList(refundDTOList, loginId);
            code = SUCCESS_CODE;
            return SUCCESS;
        } catch(Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] ImportRefundAjaxAction.importRefund error! refundFilePath=%s", refundFile.getCanonicalPath()), e);
            excelInvalidMsg = INVALID_REFUND_FILE_MSG;
            code = ERROR_CODE;
            return SUCCESS;
        }
    }

    private boolean isRefundInfoValid(List<RefundDTO> refundDTOList, StringBuilder sb) {
        // TODO PayCode 格式未定，暂不校验Id格式
        return !hasDuplicateRefundId(refundDTOList, sb);
    }


    private boolean hasDuplicateRefundId(List<RefundDTO> refundDTOList, StringBuilder sb) {
        Set<String> idSet = Sets.newHashSet();
        Set<String> duplicateSet = Sets.newHashSet();
        for (RefundDTO refundDTO : refundDTOList) {
            String refundId = refundDTO.getRefundId();
            if (idSet.contains(refundId)) {
                duplicateSet.add(refundId);
                continue;
            }
            idSet.add(refundId);
        }
        if (!duplicateSet.isEmpty()) {
            invalidRefundMap.put("duplicateIds", duplicateSet.toString());
            sb.append("重复的ID号").append(duplicateSet);
        }
        return !duplicateSet.isEmpty();
    }


    private void handleRefundList(List<RefundDTO> refundDTOList, final int loginId) {
        int groupSize = Integer.valueOf(LionConfigUtils.getProperty("ba-finance-exchange-web.refund.group.size", "1000"));
        List<List<RefundDTO>> refundDTOGroupList = ListUtils.generateListGroup(refundDTOList, groupSize);
        // 使用同步的LinkedList，CopyOnWrite更耗内存
        final List<RefundResultDTO> refundResultDTOList = Collections.synchronizedList(new LinkedList<RefundResultDTO>());
        final CountDownLatch doneSignal = new CountDownLatch(refundDTOGroupList.size());
        for (final List<RefundDTO> refundSegmentList : refundDTOGroupList) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        RefundResultDTO refundResultDTO = payOrderService.refundPayOrder(refundSegmentList, loginId);
                        refundResultDTOList.add(refundResultDTO);
                    } finally {
                        doneSignal.countDown();
                    }
                }
            });
        }

        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            MONITOR_LOGGER.error(String.format("severity=[1] ImportRefundAjaxAction.importRefund error!"), e);
        }

        RefundResultDTO allRefundResultDTO = mergeToOne(refundResultDTOList);
        generateResultMessage(allRefundResultDTO);
    }

    private void generateResultMessage(RefundResultDTO allRefundResultDTO) {
        if (!allRefundResultDTO.containFailedResult()) {
            msg.put("successCount", allRefundResultDTO.getSuccessCount());
            msg.put("refundTotalAmount", allRefundResultDTO.getRefundTotalAmount());
            return;
        }

        Map<RefundFailedReason, StringBuilder> errorInfoMap = extractErrorInfo(allRefundResultDTO);
        StringBuilder notFoundRefundIdsSb = errorInfoMap.get(RefundFailedReason.INFO_EMPTY);
        if (notFoundRefundIdsSb != null) {
            invalidRefundMap.put("notFoundRefundIds", notFoundRefundIdsSb.toString());
        }
        StringBuilder statusErrorIdsSb = errorInfoMap.get(RefundFailedReason.STATUS_ERROR);
        if (statusErrorIdsSb != null) {
            invalidRefundMap.put("statusErrorIds", statusErrorIdsSb.toString());
        }
    }

    private RefundResultDTO mergeToOne(List<RefundResultDTO> refundResultDTOList) {
        RefundResultDTO allRefundResultDTO = new RefundResultDTO();
        for (RefundResultDTO refundResultDTO : refundResultDTOList) {
            allRefundResultDTO.mergeFromOtherResult(refundResultDTO);
        }
        return allRefundResultDTO;
    }

    private Map<RefundFailedReason, StringBuilder> extractErrorInfo(RefundResultDTO refundResultDTO){
        Map<RefundFailedReason, StringBuilder> errorInfoMap = Maps.newHashMap();
        for(Map.Entry<String, RefundFailedReason> entry: refundResultDTO.getRefundFailedMap().entrySet()){
            String refundId = entry.getKey();
            RefundFailedReason reason = entry.getValue();
            StringBuilder errMsg = errorInfoMap.get(reason);
            if(errMsg == null) {
                errMsg = new StringBuilder(refundId);
                errorInfoMap.put(reason, errMsg);
                continue;
            }
            errMsg.append(",").append(refundId);
        }
        return errorInfoMap;
    }

    private List<RefundDTO> readExcel() throws IOException, BiffException {
        List<RefundDTO> refundDTOList = new ArrayList<RefundDTO>();
        Workbook book = Workbook.getWorkbook(refundFile);
        try {
            Sheet sheet = book.getSheet(0);
            refundDTOList = getRefundDTOListFromSheet(sheet);
        } finally {
            book.close();
        }
        return refundDTOList;
    }

	private List<RefundDTO> getRefundDTOListFromSheet(Sheet sheet) {
		List<RefundDTO> refundDTOList = new ArrayList<RefundDTO>();
        int columns = sheet.getColumns();
        if (columns < 2) {
            return refundDTOList;
        }
		int rows = sheet.getRows();
		for (int i = 1; i < rows; i++) {
			String refundId = sheet.getCell(0, i).getContents();
			String refundReason = sheet.getCell(1, i).getContents();
			if (StringUtils.isBlank(refundId)) {
				continue;
			}
			RefundDTO refundDTO = new RefundDTO();
			refundDTO.setRefundId(refundId.trim());
			refundDTO.setRefundReason(refundReason);
			refundDTOList.add(refundDTO);
		}
		return refundDTOList;
	}

    @Override
    protected void jsonExecute() throws Exception {
        msg.put(msgKey, "");
        return;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public Map<String, Object> getMsg() {
        return this.msg;
    }

    public File getRefundFile() {
        return refundFile;
    }

    public void setRefundFile(File refundFile) {
        this.refundFile = refundFile;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(Map<String, Object> msg) {
        this.msg = msg;
    }

    public String getErrorExcelIds() {
        return errorExcelIds;
    }

    public void setErrorExcelIds(String errorExcelIds) {
        this.errorExcelIds = errorExcelIds;
    }

    public String getExcelInvalidMsg() {
        return excelInvalidMsg;
    }

    public void setExcelInvalidMsg(String excelInvalidMsg) {
        this.excelInvalidMsg = excelInvalidMsg;
    }

    public Map<String, String> getInvalidRefundMap() {
        return invalidRefundMap;
    }

    public void setInvalidRefundMap(Map<String, String> invalidRefundMap) {
        this.invalidRefundMap = invalidRefundMap;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }
}
