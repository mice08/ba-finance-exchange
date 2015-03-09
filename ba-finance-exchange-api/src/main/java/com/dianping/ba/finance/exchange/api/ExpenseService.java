package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.ExpensePayRequestDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/9/29.
 */
public interface ExpenseService {
    /**
     * 根据时间查询费用付款单
     * @param startTime
     * @param endTime
     * @return
     */
    List<ExpensePayRequestDTO> findExpenseDataByDate(Date startTime, Date endTime);
}
