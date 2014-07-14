package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderExportBean;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by noahshen on 14-7-14.
 */
public interface PayTemplateService {

    void createExcelAndDownload(HttpServletResponse response, String fileName, List<PayOrderExportBean> exportBeanList) throws Exception;
}
