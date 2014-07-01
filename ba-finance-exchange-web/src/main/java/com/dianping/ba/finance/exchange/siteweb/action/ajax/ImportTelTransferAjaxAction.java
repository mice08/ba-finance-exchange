package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveBankService;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.dtos.TelTransferDTO;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
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
import java.util.List;
import java.util.Map;

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

    private int bankId;

    private String invalidFileMsg = "";

    private int code;

    private Map<String, Object> msg = Maps.newHashMap();

    private ReceiveOrderService receiveOrderService;

    private ReceiveBankService receiveBankService;

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
            BigDecimal totalAmount = handleTelTransferList(telTransferDTOList);
            msg.put("totalCount", telTransferDTOList.size());
            msg.put("totalAmount", totalAmount);
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


    private BigDecimal handleTelTransferList(List<TelTransferDTO> telTransferDTOList) {
        BigDecimal totalAmount = new BigDecimal(0);
        for (TelTransferDTO telTransferDTO : telTransferDTOList) {
            ReceiveOrderData roData = buildReceiveOrderData(telTransferDTO);
            receiveOrderService.createReceiveOrder(roData);
            totalAmount = totalAmount.add(roData.getReceiveAmount());
        }
        return totalAmount;
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
        roData.setStatus(ReceiveOrderStatus.UNCONFIRMED.value());

        ReceiveBankData bankData = receiveBankService.loadReceiveBankByBankId(bankId);
        roData.setBusinessType(bankData.getBusinessType());
        roData.setBankID(bankData.getBankId());
        return roData;
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

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
        this.receiveOrderService = receiveOrderService;
    }

    public void setReceiveBankService(ReceiveBankService receiveBankService) {
        this.receiveBankService = receiveBankService;
    }
}
