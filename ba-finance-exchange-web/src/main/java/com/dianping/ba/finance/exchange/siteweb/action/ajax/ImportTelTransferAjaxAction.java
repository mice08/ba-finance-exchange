package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.api.dtos.TelTransferDTO;
import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;
import com.dianping.finance.common.util.DateUtils;
import com.dianping.finance.common.util.DecimalUtils;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 *
 */
public class ImportTelTransferAjaxAction extends AjaxBaseAction {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.ImportTelTransferAjaxAction");


    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * 导入文件的列声明
     */
    private static final String[] TEMPLATE_COLUMN = {
            "bankFlowId",
            "bankReceiveDate",
            "bankTradeType",
            "amount",
            "payerAccountName",
            "payerAccountNo",
            "payerBankName",
            "memo"
    };

    private static final String INVALID_DATE = "invalidDateRows";
    private static final String INVALID_AMOUNT = "invalidAmountRows";
    private static final String DUPLICATE_ID = "duplicateIdRows";

    private static final String INVALID_FILE_MSG = "导入收款文件格式错误！";

    private File telTransferFile;

    private Map<String,String> invalidRefundMap = new HashMap<String, String>();

    private String invalidFileMsg = "";

    private int code;

    private Map<String, Object> msg = Maps.newHashMap();

    private ExecutorService executorService;

    private ReceiveOrderService receiveOrderService;

    public String importTelTransfer() throws Exception {
        try {
            Multimap<String, String> invalidMsgMMap = LinkedListMultimap.create();

            List<TelTransferDTO> telTransferDTOList = readExcel();
            if (CollectionUtils.isEmpty(telTransferDTOList)) {
                invalidFileMsg = INVALID_FILE_MSG;
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            if (!isTelTransferInfoValid(telTransferDTOList, invalidMsgMMap)) {
                msg.putAll(invalidMsgMMap.asMap());
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            handleTelTransferList(telTransferDTOList);
            code = SUCCESS_CODE;
            return SUCCESS;
        } catch(Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] ImportTelTransferAjaxAction.importTelTransfer error! telTransferFile=%s", telTransferFile.getCanonicalPath()), e);
            invalidFileMsg = INVALID_FILE_MSG;
            code = ERROR_CODE;
            return SUCCESS;
        }
    }

    private boolean isTelTransferInfoValid(List<TelTransferDTO> telTransferDTOList, Multimap<String, String> invalidMsgMMap) {
        return isFieldValid(telTransferDTOList, invalidMsgMMap) && !hasDuplicateFlowId(telTransferDTOList, invalidMsgMMap);
    }

    private boolean isFieldValid(List<TelTransferDTO> telTransferDTOList, Multimap<String, String> invalidMsgMMap) {
        boolean valid = true;
        for (int r = 0; r < telTransferDTOList.size(); ++r) {
            TelTransferDTO telTransferDTO = telTransferDTOList.get(r);
            if (!DateUtils.isValidDate(telTransferDTO.getBankReceiveDate(), DATE_FORMAT)) {
                valid = false;
                invalidMsgMMap.put(INVALID_DATE, String.format("第%d行：日期格式错误 %s", r, telTransferDTO.getBankReceiveDate()));
            }
            if (!DecimalUtils.isValidBigDecimal(telTransferDTO.getAmount())) {
                valid = false;
                invalidMsgMMap.put(INVALID_AMOUNT, String.format("第%d行：金额格式错误 %s", r, telTransferDTO.getAmount()));
            }
        }
        return valid;
    }


    private boolean hasDuplicateFlowId(List<TelTransferDTO> telTransferDTOList, Multimap<String, String> invalidMsgMMap) {
        boolean hasDuplicate = true;
        Map<String, Integer> bankFlowIdRowMap = Maps.newHashMap();
        for (int r = 0; r < telTransferDTOList.size(); ++r) {
            TelTransferDTO telTransferDTO = telTransferDTOList.get(r);
            String bankFlowId = telTransferDTO.getBankFlowId();
            Integer duplicateRow = bankFlowIdRowMap.get(bankFlowId);
            if (duplicateRow == null) {
                bankFlowIdRowMap.put(bankFlowId, r);
                continue;
            }
            hasDuplicate = true;
            invalidMsgMMap.put(DUPLICATE_ID, String.format("第%d行与第%d行，交易流水重复，ID=%s", duplicateRow, r, bankFlowId));
        }
        return hasDuplicate;
    }


    private void handleTelTransferList(List<TelTransferDTO> telTransferDTOList) {
        BigDecimal totlaAmount = new BigDecimal(0);
        for (TelTransferDTO telTransferDTO : telTransferDTOList) {
            ReceiveOrderData roData = buildReceiveOrderData(telTransferDTO);
            receiveOrderService.createReceiveOrder(roData);
        }

//        generateResultMessage(allRefundResultDTO);
    }

    private ReceiveOrderData buildReceiveOrderData(TelTransferDTO telTransferDTO) {
        ReceiveOrderData roData = new ReceiveOrderData();
        roData.setTradeNo(telTransferDTO.getBankFlowId());
        roData.setBankReceiveTime(DateUtils.formatDate(DATE_FORMAT, telTransferDTO.getBankReceiveDate()));
        roData.setMemo(telTransferDTO.getBankTradeType() + "-" + telTransferDTO.getMemo());
        roData.setReceiveAmount(new BigDecimal(telTransferDTO.getAmount()));
        roData.setPayerAccountName(telTransferDTO.getPayerAccountName());
        roData.setPayerAccountNo(telTransferDTO.getPayerAccountNo());
        roData.setPayerBankName(telTransferDTO.getPayerBankName());
        return roData;
    }

    private void generateResultMessage(RefundResultDTO allRefundResultDTO) {
        if (allRefundResultDTO.getSuccessCount() > 0) {
            msg.put("totalCount", allRefundResultDTO.getSuccessCount());
            msg.put("refundTotalAmount", allRefundResultDTO.getRefundTotalAmount());
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

    private List<TelTransferDTO> readExcel() throws Exception {
        Workbook book = Workbook.getWorkbook(telTransferFile);
        try {
            Sheet sheet = book.getSheet(0);
            return buildTelTransferDTOFromSheet(sheet);
        } finally {
            book.close();
        }
    }

	private List<TelTransferDTO> buildTelTransferDTOFromSheet(Sheet sheet) throws InvocationTargetException, IllegalAccessException {
        int columns = sheet.getColumns();
        if (columns < TEMPLATE_COLUMN.length) {
            return Collections.emptyList();
        }
        List<TelTransferDTO> telTransferDTOList = Lists.newLinkedList();
        int rows = sheet.getRows();
		for (int r = 1; r < rows; r++) {
            TelTransferDTO telTransferDTO = new TelTransferDTO();
            for (int c = 0; c < TEMPLATE_COLUMN.length; ++c) {
                String value = sheet.getCell(c, r).getContents();
                BeanUtils.setProperty(telTransferDTO, TEMPLATE_COLUMN[c], value);
            }
            telTransferDTOList.add(telTransferDTO);
		}
		return telTransferDTOList;
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

    public void setTelTransferFile(File telTransferFile) {
        this.telTransferFile = telTransferFile;
    }

    public String getInvalidFileMsg() {
        return invalidFileMsg;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
        this.receiveOrderService = receiveOrderService;
    }
}
