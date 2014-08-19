package ba.finance.exchange.midasreco.biz.dao;

import com.dianping.ba.finance.exchange.midasreco.api.datas.InvoiceRecoData;
import com.dianping.ba.finance.exchange.midasreco.biz.dao.InvoiceRecoDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/config/spring/common/appcontext-*.xml", "classpath*:/config/spring/local/appcontext-*.xml"})
public class InvoiceRecoDaoTest {

    @Autowired
    InvoiceRecoDao invoiceRecoDao;

    @Test
    public void testInsertInvoiceRecoDatas() throws Exception {
        InvoiceRecoData invoiceRecoData1 = new InvoiceRecoData();
        invoiceRecoData1.setId(114);
        invoiceRecoData1.setInvoiceAmount(BigDecimal.TEN);
        InvoiceRecoData invoiceRecoData2 = new InvoiceRecoData();
        invoiceRecoData2.setId(112);
        invoiceRecoData2.setInvoiceAmount(BigDecimal.TEN);
        List<InvoiceRecoData> invoiceRecoDataList = new ArrayList<InvoiceRecoData>();
        invoiceRecoDataList.add(invoiceRecoData1);
        invoiceRecoDataList.add(invoiceRecoData2);
        invoiceRecoDao.insertInvoiceRecoDatas(invoiceRecoDataList);
    }
}