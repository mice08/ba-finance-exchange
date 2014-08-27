package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.siteweb.beans.BusinessExportInfoBean;
import com.dianping.ba.finance.exchange.siteweb.beans.MerchantsTemplateBean;
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderExportBean;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
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
 * 招商银行的导出支付的模板
 */
public class MerchantsPayTemplateService implements PayTemplateService {

    private String templateName;

    private Map<Integer, BusinessExportInfoBean> businessExportInfoBeanMap;

    private String[] commonProperties;

    private String[] commonColumns;

    private int[] commonColumnFormats;

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

        commonColumnFormats = new int[]{
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
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_NUMERIC,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING,
                Cell.CELL_TYPE_STRING
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

    private void exportExcel(OutputStream os, List<MerchantsTemplateBean> merchantsTemplateBeanLinkedList) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
        try {
            exportCommon(wb, merchantsTemplateBeanLinkedList, os);
        } finally {
            os.close();
        }
    }

    private void exportCommon(Workbook workbook, List<MerchantsTemplateBean> merchantsTemplateBeanLinkedList, OutputStream os) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (CollectionUtils.isEmpty(merchantsTemplateBeanLinkedList)) {
            return;
        }
        Sheet sheet = workbook.createSheet("支付模板");
        createSheetHeader(workbook, sheet, commonColumns);
        createSheetBody(workbook, sheet, commonColumnFormats, commonProperties, merchantsTemplateBeanLinkedList);
        workbook.write(os);
    }


    private void createSheetHeader(Workbook workbook, Sheet sheet, String[] commonColumns) {
        Row row = sheet.createRow(0);
        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        headCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        headCellStyle.setBorderTop(CellStyle.BORDER_THIN);

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

        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);

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
