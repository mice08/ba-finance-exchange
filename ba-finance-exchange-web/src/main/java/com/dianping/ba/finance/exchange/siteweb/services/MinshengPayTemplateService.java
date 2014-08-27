package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.api.enums.BankAccountType;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.siteweb.beans.CommonTemplateBean;
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderExportBean;
import com.dianping.ba.finance.exchange.siteweb.beans.SameBankPersonalTemplateBean;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
 * 民生银行的导出支付的模板
 */
public class MinshengPayTemplateService implements PayTemplateService {

    private String templateName;

    private String[] commonProperties;

    private String[] commonColumns;

    private int[] commonColumnFormats;

    private String[] sameBankPersonalProperties;

    private String[] sameBankPersonalColumns;

    private int[] sameBankPersonalFormats;

    public void init() {
        initCommon();
        initSameBankPersonal();
    }

    private void initCommon() {
        commonProperties = new String[] {
                "formType",
                "payCode",
                "customerId",
                "reservationFlag",
                "payerAccountNo",
                "payAmount",
                "bankAccountNo",
                "bankAccountName",
                "bankAccountType",
                "subClientNo",
                "subAccountNo",
                "subAccountName",
                "subAccountBankName",
                "usage",
                "remitChannel",
                "notifyReceiver",
                "phone",
                "email",
                "bankCodeAndFullBranchName"
        };
        commonColumns = new String[] {
                "制单类型",
                "企业自制凭证号",
                "客户号",
                "预约标志",
                "付款账号",
                "交易金额",
                "收款账号",
                "收款人姓名",
                "收款账户类型",
                "子客户号",
                "子付款账号",
                "子付款账户名",
                "子付款账户开户行名",
                "用途",
                "汇路",
                "是否通知收款人",
                "手机号码",
                "邮箱",
                "支付行号&支付行名称"
        };

        commonColumnFormats = new int[] {
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_NUMERIC,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING
        };
    }

    private void initSameBankPersonal() {
        sameBankPersonalProperties = new String[] {
                "bankAccountNo",
                "payAmount",
                "bankAccountName"
        };
        sameBankPersonalColumns = new String[] {
                "个人帐号",
                "金额",
                "姓名"
        };
        sameBankPersonalFormats = new int[] {
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_NUMERIC,
                Cell.CELL_TYPE_STRING
        };
    }

    @Log(severity = 1)
    @ReturnDefault
    @Override
    public void createExcelAndDownload(HttpServletResponse response, String fileName, List<PayOrderExportBean> exportBeanList) throws Exception {
        List<CommonTemplateBean> commonTemplateBeanList = Lists.newLinkedList();
        List<SameBankPersonalTemplateBean> sameBankPersonalTemplateBeanList = Lists.newLinkedList();
        // 按照账户类型分组
        groupByAccountType(exportBeanList, commonTemplateBeanList, sameBankPersonalTemplateBeanList);

        initResponse(response, fileName);
        OutputStream os = response.getOutputStream();
        exportExcel(os, commonTemplateBeanList, sameBankPersonalTemplateBeanList);
    }

    private void exportExcel(OutputStream os, List<CommonTemplateBean> commonTemplateBeanList, List<SameBankPersonalTemplateBean> sameBankPersonalTemplateBeanList) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
        try {
            exportSameBankPersonal(wb, sameBankPersonalTemplateBeanList);
            exportCommon(wb, commonTemplateBeanList);
            wb.write(os);
        } finally {
            os.close();
        }
    }

    private void exportSameBankPersonal(Workbook workbook, List<SameBankPersonalTemplateBean> sameBankPersonalTemplateBeanList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (CollectionUtils.isEmpty(sameBankPersonalTemplateBeanList)) {
            return;
        }
        Sheet sheet = workbook.createSheet("行内对私支付模板");
        sheet.setDefaultColumnWidth(20);
        createSheetHeader(workbook, sheet, sameBankPersonalColumns);
        createSheetBody(workbook, sheet, sameBankPersonalFormats, sameBankPersonalProperties, sameBankPersonalTemplateBeanList);

    }

    private void exportCommon(Workbook workbook, List<CommonTemplateBean> commonTemplateBeanList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (CollectionUtils.isEmpty(commonTemplateBeanList)) {
            return;
        }
        Sheet sheet = workbook.createSheet("通用支付模板");
        sheet.setDefaultColumnWidth(20);
        createSheetHeader(workbook, sheet, commonColumns);
        createSheetBody(workbook, sheet, commonColumnFormats, commonProperties, commonTemplateBeanList);
    }

    private void createSheetHeader(Workbook workbook, Sheet sheet, String[] commonColumns) {
        Row row = sheet.createRow(0);
        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        headCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headCellStyle.setBorderBottom(CellStyle.BORDER_NONE);
        headCellStyle.setBorderLeft(CellStyle.BORDER_NONE);
        headCellStyle.setBorderRight(CellStyle.BORDER_NONE);
        headCellStyle.setBorderTop(CellStyle.BORDER_NONE);

        for (int i = 0; i < commonColumns.length; i++) {
            Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue(commonColumns[i]);
        }
    }


    private void createSheetBody(Workbook workbook,
                                 Sheet sheet,
                                 int[] formats,
                                 String[] commonProperties,
                                 List<?> templateBeanList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (int i = 0; i < templateBeanList.size(); i++) {
            Object obj = templateBeanList.get(i);
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < commonProperties.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellType(formats[j]);
                CellStyle cellStyle = createCellStyle(workbook, formats[j]);
                cell.setCellStyle(cellStyle);

                if (obj instanceof Map) {
                    Map<?, ?> objMap = (Map<?, ?>) obj;
                    cell.setCellValue(String.valueOf(objMap.get(commonProperties[j].toLowerCase())));
                } else {
                    String item = BeanUtils.getProperty(obj, commonProperties[j]);
                    if (item == null) {
                        item = "";
                    }
                    cell.setCellValue(item);
                }
            }
        }
    }

    private CellStyle createCellStyle(Workbook workbook, int format) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        cellStyle.setBorderBottom(CellStyle.BORDER_NONE);
        cellStyle.setBorderLeft(CellStyle.BORDER_NONE);
        cellStyle.setBorderRight(CellStyle.BORDER_NONE);
        cellStyle.setBorderTop(CellStyle.BORDER_NONE);

        if (format == Cell.CELL_TYPE_NUMERIC) {
            CreationHelper creationHelper = workbook.getCreationHelper();
            short numberFormat = creationHelper.createDataFormat().getFormat("##,###,###,###,##0.00");
            cellStyle.setDataFormat(numberFormat);
        }
        return cellStyle;

    }
    private void initResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        String downloadFileName = getEncodedFileName(fileName);
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + downloadFileName);
        response.setContentType("application/x-download");
    }

    private String getEncodedFileName(String fileName) throws UnsupportedEncodingException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String downloadFileName = String.format("%s_%s.xlsx", fileName, df.format(new Date()));
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
}
