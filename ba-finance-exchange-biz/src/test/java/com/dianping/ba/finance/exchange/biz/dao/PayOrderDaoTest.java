package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.*;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.FlowType;
import com.dianping.ba.finance.exchange.api.enums.SourceType;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.util.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 14-2-12
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/config/spring/common/appcontext-*.xml", "classpath*:/config/spring/local/appcontext-*.xml"})

public class PayOrderDaoTest {
    @Autowired
    private PayOrderDao payOrderDao;

    @Test
    public void testInsertPayOrder() {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setAddTime(Calendar.getInstance().getTime());
        payOrderData.setAddLoginId(-1);
        payOrderData.setUpdateLoginId(-1);
        payOrderData.setUpdateTime(Calendar.getInstance().getTime());
        payOrderData.setBankAccountName("1111");
        payOrderData.setPaySequence("1");
        payOrderData.setPayAmount(BigDecimal.ONE);

        payOrderDao.insertPayOrder(payOrderData);
    }

}
