package com.dianping.ba.finance.exchange.siteweb.services;

import au.com.bytecode.opencsv.CSVWriter;
import com.dianping.ba.finance.exchange.siteweb.beans.ReceiveOrderBean;
import jxl.write.WriteException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/10/13.
 */
public class CSVExportService {

    public void createCSVAndDownload(HttpServletResponse response, String fileName, List<ReceiveOrderBean> exportBeanList) throws IOException, WriteException {
        List<String[]> records = generateRecords(exportBeanList);
        initResponse(response, fileName);
        PrintWriter out = response.getWriter();
        CSVWriter writer = new CSVWriter(out);
        writer.writeAll(records);
        out.flush();
        out.close();
    }

    private List<String[]> generateRecords(List<ReceiveOrderBean> exportBeanList) {
        List<String[]> records = new ArrayList<String[]>();
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("收款单ID");
        columnNames.add("客户名");
        columnNames.add("收款金额");
        columnNames.add("收款方式");
        columnNames.add("到款日期");
        columnNames.add("系统入账日期");
        columnNames.add("款项类型");
        columnNames.add("合同号");
        columnNames.add("付款方户名");
        columnNames.add("状态");
        records.add(columnNames.toArray(new String[columnNames.size()]));

        for(ReceiveOrderBean receiveOrderBean : exportBeanList) {
            List<String> record = new ArrayList<String>();
            record.add(String.valueOf(receiveOrderBean.getRoId()));
            record.add(receiveOrderBean.getCustomerName());
            record.add(receiveOrderBean.getReceiveAmount());
            record.add(receiveOrderBean.getPayChannel());
            record.add(receiveOrderBean.getBankReceiveTime());
            record.add(receiveOrderBean.getReceiveTime());
            record.add(receiveOrderBean.getReceiveType());
            record.add(receiveOrderBean.getBizContent());
            record.add(receiveOrderBean.getPayerName());
            record.add(receiveOrderBean.getStatus());
            records.add(record.toArray(new String[record.size()]));
        }
        return records;
    }


    private void initResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        String downloadFileName = getEncodedFileName(fileName);
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + downloadFileName);
        response.setContentType("application/x-download");
        response.setCharacterEncoding("GBK");
    }

    private String getEncodedFileName(String fileName) throws UnsupportedEncodingException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String downloadFileName = String.format("%s_%s.csv", fileName, df.format(new Date()));
        return new String(downloadFileName.getBytes(System.getProperty("file.encoding")), "ISO-8859-1");
    }
}
