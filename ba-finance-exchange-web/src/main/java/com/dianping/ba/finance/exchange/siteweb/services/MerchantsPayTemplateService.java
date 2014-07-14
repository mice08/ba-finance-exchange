package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.api.enums.BankAccountType;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.siteweb.beans.*;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.StringUtils;
import com.google.common.collect.Lists;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 招商银行的导出支付的模板
 */
public class MerchantsPayTemplateService implements PayTemplateService {

    private String templateName;

    private Map<Integer, BusinessExportInfoBean> businessExportInfoBeanMap;

    private String[] commonProperties;

    private String[] commonColumns;

    private DisplayFormat[] commonColumnFormats;

    public void init() {
        commonProperties = new String[]{
                "payCode",
                "debitSideId",
                "bankAccountNo",
                "bankAccountName",
                "bankName",
                "bankProvince",
                "bankCity",
                "debitSideEmail",
                "currency",
                "payerBranchBank",
                "settleType",
                "payerAccountNo",
                "expectedDate",
                "expectedTime",
                "use",
                "orderAmount",
                "debitSideBankNo",
                "debitSideBankName",
                "businessSummary"
        };
        commonColumns = new String[]{
                "业务参考号",
                "收款人编号",
                "收款人帐号",
                "收款人名称",
                "收方开户支行",
                "收款人所在省",
                "收款人所在市",
                "收方邮件地址",
                "币种",
                "付款分行",
                "结算方式",
                "付方帐号",
                "期望日",
                "期望时间",
                "用途",
                "金额",
                "收方联行号",
                "收方银行",
                "业务摘要"
        };

        commonColumnFormats = new DisplayFormat[]{
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.FLOAT,
                NumberFormats.TEXT,
                NumberFormats.TEXT,
                NumberFormats.TEXT
        };
    }

    @Log(severity = 1)
    @ReturnDefault
    @Override
    public void createExcelAndDownload(HttpServletResponse response, String fileName, List<PayOrderExportBean> exportBeanList) throws Exception {
        List<MerchantsTemplateBean> merchantsTemplateBeanLinkedList = buildToMerchantsTemplateBeanLinkedList(exportBeanList);
        initResponse(response, fileName);
        OutputStream os = response.getOutputStream();
        exportExcel(os, merchantsTemplateBeanLinkedList);
    }

    private List<MerchantsTemplateBean> buildToMerchantsTemplateBeanLinkedList(List<PayOrderExportBean> exportBeanList) {
        String todayDate = DateUtil.formatDateToString(new Date(), "yyyyMMdd");
        List<MerchantsTemplateBean> merchantsTemplateBeanLinkedList = Lists.newLinkedList();
        for (PayOrderExportBean exportBean : exportBeanList) {
            MerchantsTemplateBean templateBean = new MerchantsTemplateBean();
            templateBean.setPoId(exportBean.getPoId());
            templateBean.setPayCode(exportBean.getPayCode());
            templateBean.setOrderAmount(exportBean.getPayAmount());
            templateBean.setBankName(exportBean.getBankName());
            templateBean.setBankAccountName(exportBean.getBankAccountName());
            templateBean.setBankAccountNo(exportBean.getBankAccountNo());
            templateBean.setBankProvince(exportBean.getBankProvince());
            templateBean.setBankCity(exportBean.getBankCity());
            templateBean.setUse("大众点评网");
            templateBean.setExpectedDate(todayDate);
            BusinessExportInfoBean exportInfoBean = businessExportInfoBeanMap.get(exportBean.getBusinessType());
            if (exportInfoBean != null) {
                templateBean.setBusinessSummary(exportInfoBean.getBusinessSummary());
                templateBean.setCurrency(exportInfoBean.getCurrency());
                templateBean.setDebitSideBankName(exportInfoBean.getDebitSideBankName());
                templateBean.setDebitSideBankNo(exportInfoBean.getDebitSideBankNo());
                templateBean.setPayerAccountNo(exportInfoBean.getPayerAccountNo());
                templateBean.setPayerBranchBank(exportInfoBean.getPayerBranchBank());
                templateBean.setSettleType(exportInfoBean.getSettleType());
                templateBean.setUse(exportInfoBean.getUse());
            }
            merchantsTemplateBeanLinkedList.add(templateBean);
        }
        return merchantsTemplateBeanLinkedList;
    }

    private void exportExcel(OutputStream os, List<MerchantsTemplateBean> merchantsTemplateBeanLinkedList) throws IOException, WriteException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        WritableWorkbook excelBook = null;
        try {
            excelBook = Workbook.createWorkbook(os);
            exportCommon(excelBook, merchantsTemplateBeanLinkedList);
            excelBook.write();
        } finally {
            if (excelBook != null) {
                excelBook.close();
            }
            os.close();
        }
    }

    private void exportCommon(WritableWorkbook excelBook, List<MerchantsTemplateBean> merchantsTemplateBeanLinkedList) throws WriteException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (CollectionUtils.isEmpty(merchantsTemplateBeanLinkedList)) {
            return;
        }
        WritableSheet commonSheet = excelBook.createSheet("支付模板", 0);
        createSheetHeader(commonSheet, commonColumns);
        createSheetBody(commonSheet, commonColumnFormats, commonProperties, merchantsTemplateBeanLinkedList);
    }

    private void createSheetBody(WritableSheet commonSheet,
                                 DisplayFormat[] formats,
                                 String[] commonProperties,
                                 List<?> templateBeanList) throws WriteException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (int i = 0; i < templateBeanList.size(); i++) {
            Object obj = templateBeanList.get(i);
            for (int j = 0; j < commonProperties.length; j++) {
                WritableCellFormat bodyFormat = createCellFormat(formats[j]);
                if (obj instanceof Map) {
                    Map<?, ?> objMap = (Map<?, ?>) obj;
                    Label label = new Label(j, i + 1, String.valueOf(objMap.get(commonProperties[j].toLowerCase())), bodyFormat);
                    commonSheet.addCell(label);
                } else {
                    String item = BeanUtils.getProperty(obj, commonProperties[j]);
                    if (item == null) {
                        item = "";
                    }
                    if (!formats[j].equals(NumberFormats.TEXT)
                            && StringUtils.isDecimal(item)) {
                        double num = Double.valueOf(item).doubleValue();
                        Number number = new Number(j, i + 1, num, bodyFormat);
                        commonSheet.addCell(number);
                    } else {
                        Label label = new Label(j, i + 1, item, bodyFormat);
                        commonSheet.addCell(label);
                    }
                }
                commonSheet.setRowView(i + 1, 350);
            }
        }
    }

    private WritableCellFormat createCellFormat(DisplayFormat format) throws WriteException {
        DisplayFormat f;
        if (format == NumberFormats.FLOAT) {
            f = new NumberFormat("##,###,###,###,##0.00");
        } else {
            f = format;
        }
        WritableCellFormat bodyFormat = new WritableCellFormat(f);
        bodyFormat.setAlignment(Alignment.LEFT);
        bodyFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        bodyFormat.setBorder(Border.NONE, jxl.format.BorderLineStyle.THIN);
        return bodyFormat;
    }

    private void createSheetHeader(WritableSheet commonSheet, String[] commonColumns) throws WriteException {
        WritableCellFormat headerFormat = new WritableCellFormat();
        headerFormat.setAlignment(Alignment.LEFT);
        headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        headerFormat.setBorder(Border.NONE, jxl.format.BorderLineStyle.THIN);
        for (int i = 0; i < commonColumns.length; i++) {
            Label label = new Label(i, 0, commonColumns[i], headerFormat);
            commonSheet.addCell(label);
            commonSheet.setColumnView(i, 20);
            commonSheet.setRowView(0, 500);
        }
    }

    private void initResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        String downloadFileName = getEncodedFileName(fileName);
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + downloadFileName);
        response.setContentType("application/x-download");
    }

    private String getEncodedFileName(String fileName) throws UnsupportedEncodingException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String downloadFileName = String.format("%s_%s.xls", fileName, df.format(new Date()));
        return new String(downloadFileName.getBytes(System.getProperty("file.encoding")), "ISO-8859-1");
    }

    private void groupByAccountType(List<PayOrderExportBean> exportBeanList,
                                    List<CommonTemplateBean> commonTemplateBeanList,
                                    List<SameBankPersonalTemplateBean> sameBankPersonalTemplateBeanList) {
        for (PayOrderExportBean payOrderExportBean : exportBeanList) {
            if (isSameBankAndPersonal(payOrderExportBean)) {
                // 行内对私
                SameBankPersonalTemplateBean templateBean = buildSameBankPersonalTemplateBean(payOrderExportBean);
                sameBankPersonalTemplateBeanList.add(templateBean);
            } else {
                CommonTemplateBean templateBean = buildCommonTemplateBean(payOrderExportBean);
                commonTemplateBeanList.add(templateBean);
            }
        }
    }

    private boolean isSameBankAndPersonal(PayOrderExportBean payOrderExportBean) {
        int accountType = payOrderExportBean.getBankAccountType();
        String bankName = payOrderExportBean.getBankName();
        if (bankName == null) {
            bankName = "";
        }
        return BankAccountType.PERSONAL.value() == accountType && bankName.contains("民生");
    }

    private CommonTemplateBean buildCommonTemplateBean(PayOrderExportBean payOrderExportBean) {
        CommonTemplateBean templateBean = new CommonTemplateBean();
        templateBean.setPayCode(payOrderExportBean.getPayCode());
        templateBean.setPayAmount(payOrderExportBean.getPayAmount());
        templateBean.setBankAccountNo(payOrderExportBean.getBankAccountNo());
        templateBean.setBankAccountName(payOrderExportBean.getBankAccountName());
        int accountTypeInTemplate = payOrderExportBean.getBankAccountType() == 1 ? 0 : 1;
        templateBean.setBankAccountType(accountTypeInTemplate);
        BusinessType businessType = BusinessType.valueOf(payOrderExportBean.getBusinessType());
        templateBean.setUsage("大众点评-" + businessType.toString());
        String bankCode = payOrderExportBean.getBankCode();
        if (bankCode == null) {
            bankCode = "";
        }
        String fullBranchName = payOrderExportBean.getBankFullBranchName();
        if (fullBranchName == null) {
            fullBranchName = "";
        }
        templateBean.setBankCodeAndFullBranchName(bankCode + "&" + fullBranchName);
        return templateBean;
    }

    private SameBankPersonalTemplateBean buildSameBankPersonalTemplateBean(PayOrderExportBean payOrderExportBean) {
        SameBankPersonalTemplateBean templateBean = new SameBankPersonalTemplateBean();
        templateBean.setBankAccountName(payOrderExportBean.getBankAccountName());
        templateBean.setBankAccountNo(payOrderExportBean.getBankAccountNo());
        templateBean.setPayAmount(payOrderExportBean.getPayAmount());
        return templateBean;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setBusinessExportInfoBeanMap(Map<Integer, BusinessExportInfoBean> businessExportInfoBeanMap) {
        this.businessExportInfoBeanMap = businessExportInfoBeanMap;
    }
}
