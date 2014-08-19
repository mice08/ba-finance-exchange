package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderRecoData;
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

public class ReceiveOrderRecoDaoTest {

    @Autowired
    ReceiveOrderRecoDao receiveOrderRecoDao;

    @Test
    public void testInsertReceiveOrderRecoDatas() throws Exception {
        ReceiveOrderRecoData receiveOrderRecoData1 = new ReceiveOrderRecoData();
        receiveOrderRecoData1.setId(100);
        receiveOrderRecoData1.setBatchId("12");
        receiveOrderRecoData1.setReceiveAmount(BigDecimal.valueOf(10.2));
        ReceiveOrderRecoData receiveOrderRecoData2 = new ReceiveOrderRecoData();
        receiveOrderRecoData2.setId(104);
        receiveOrderRecoData2.setBatchId("12");
        receiveOrderRecoData2.setReceiveAmount(BigDecimal.valueOf(10.2));

        List<ReceiveOrderRecoData> receiveOrderRecoDataList = new ArrayList<ReceiveOrderRecoData>();
        receiveOrderRecoDataList.add(receiveOrderRecoData1);
        receiveOrderRecoDataList.add(receiveOrderRecoData2);

        receiveOrderRecoDao.insertReceiveOrderRecoDatas(receiveOrderRecoDataList);
    }
}