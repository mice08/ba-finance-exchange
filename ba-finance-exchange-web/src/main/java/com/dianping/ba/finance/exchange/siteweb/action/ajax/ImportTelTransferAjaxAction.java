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
import org.apache.commons.lang3.StringUtils;

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
    private static final String EMPTY_FIELD = "emptyFieldRows";
    private static final String NEGATIVE_OR_ZERO_AMOUNT = "negativeOrZeroAmountRows";
    private static final String DUPICATE_TRADE_NO = "duplicateTradNoRows";

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
            handleTelTransferList(telTransferDTOList, invalidMsgMMap);
            if (!invalidMsgMMap.isEmpty()) {
                msg.putAll(invalidMsgMMap.asMap());
            }
            code = SUCCESS_CODE;
            return SUCCESS;
        } catch(Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] ImportTelTransferAjaxAction.importTelTransfer error! telTransferFile=%s", telTransferFile.getCanonicalPath()), e);
            invalidFileMsg = INVALID_FILE_MSG;
            code = SUCCESS_CODE;
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
            StringBuilder emptyErrSb = new StringBuilder();
            if (hasEmptyField(telTransferDTO, emptyErrSb)) {
                invalidMsgMMap.put(EMPTY_FIELD, String.format("第%d行：%s", r, emptyErrSb.toString()));
                continue;
            }
            if (!DateUtils.isValidDate(telTransferDTO.getBankReceiveDate(), DATE_FORMAT)) {
                valid = false;
                invalidMsgMMap.put(INVALID_DATE, String.format("第%d行：日期格式错误 %s", r, telTransferDTO.getBankReceiveDate()));
            }
            if (!DecimalUtils.isValidBigDecimal(telTransferDTO.getAmount())) {
                valid = false;
                invalidMsgMMap.put(INVALID_AMOUNT, String.format("第%d行：金额格式错误 %s", r, telTransferDTO.getAmount()));
            } else {
                BigDecimal amount = DecimalUtils.parseBigDecimal(telTransferDTO.getAmount());
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    invalidMsgMMap.put(NEGATIVE_OR_ZERO_AMOUNT, String.format("第%d行：金额必须大于0 %s", r, telTransferDTO.getAmount()));
                }
            }
        }
        return valid;
    }

    private boolean hasEmptyField(TelTransferDTO telTransferDTO, StringBuilder emptyErrSb) {
        boolean hasEmptyField = false;
        String flowId = telTransferDTO.getBankFlowId();
        if (StringUtils.isBlank(flowId)) {
            hasEmptyField = true;
            emptyErrSb.append("交易流水为空;");
        }
        String bankReceiveDate = telTransferDTO.getBankReceiveDate();
        if (StringUtils.isBlank(bankReceiveDate)) {
            hasEmptyField = true;
            emptyErrSb.append("到款日期为空;");
        }
        String amount = telTransferDTO.getAmount();
        if (StringUtils.isBlank(amount)) {
            hasEmptyField = true;
            emptyErrSb.append("金额为空;");
        }

        String payerAccountName = telTransferDTO.getPayerAccountName();
        if (StringUtils.isBlank(payerAccountName)) {
            hasEmptyField = true;
            emptyErrSb.append("付款方账户名为空;");
        }

        String payerAccountNo = telTransferDTO.getPayerAccountNo();
        if (StringUtils.isBlank(payerAccountNo)) {
            hasEmptyField = true;
            emptyErrSb.append("付款方账号为空;");
        }
        String payerBankName = telTransferDTO.getPayerBankName();
        if (StringUtils.isBlank(payerBankName)) {
            hasEmptyField = true;
            emptyErrSb.append("付款方开户行为空;");
        }
        return hasEmptyField;
    }


    private boolean hasDuplicateFlowId(List<TelTransferDTO> telTransferDTOList, Multimap<String, String> invalidMsgMMap) {
        boolean hasDuplicate = false;
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


    private void handleTelTransferList(List<TelTransferDTO> telTransferDTOList, Multimap<String, String> invalidMsgMMap) {
        BigDecimal totalAmount = new BigDecimal(0);
        int totalCount = 0;
        for (TelTransferDTO telTransferDTO : telTransferDTOList) {
            ReceiveOrderData roData = buildReceiveOrderData(telTransferDTO);
            int roId = receiveOrderService.createReceiveOrder(roData);
            if (roId <= 0) {
                invalidMsgMMap.put(DUPICATE_TRADE_NO, String.format("导入失败，交易流水为 %s", telTransferDTO.getBankFlowId()));
                continue;
            }
            totalCount++;
            totalAmount = totalAmount.add(roData.getReceiveAmount());
        }
        msg.put("totalCount", totalCount);
        msg.put("totalAmount", totalAmount);
    }

    private ReceiveOrderData buildReceiveOrderData(TelTransferDTO telTransferDTO) {
        ReceiveOrderData roData = new ReceiveOrderData();
        roData.setTradeNo(telTransferDTO.getBankFlowId());
        roData.setBankReceiveTime(DateUtils.formatDate(DATE_FORMAT, telTransferDTO.getBankReceiveDate()));
        roData.setMemo(telTransferDTO.getBankTradeType() + "-" + telTransferDTO.getMemo());
        roData.setReceiveAmount(DecimalUtils.parseBigDecimal(telTransferDTO.getAmount()));
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
