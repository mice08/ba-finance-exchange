package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ExpenseService;
import com.dianping.ba.finance.exchange.api.datas.ExpensePayRequestData;
import com.dianping.ba.finance.exchange.api.dtos.ExpensePayRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.biz.dao.PayRequestDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/9/29.
 */
public class ExpenseServiceObject implements ExpenseService {

    private PayRequestDao payRequestDao;

    @Override
    public List<ExpensePayRequestDTO> findExpenseDataByDate(Date startTime, Date endTime) {
        List<ExpensePayRequestData> expensePayOrderDataList = payRequestDao.findExpensePayDataByDate(BusinessType.EXPENSE.value(), startTime, endTime);
        List<ExpensePayRequestDTO> expensePayOrderDTOList = buildExpensePayOrderDTOList(expensePayOrderDataList);
        return expensePayOrderDTOList;
    }

    private List<ExpensePayRequestDTO> buildExpensePayOrderDTOList(List<ExpensePayRequestData> expensePayOrderDataList) {
        List<ExpensePayRequestDTO> expensePayOrderDTOList = new ArrayList<ExpensePayRequestDTO>();
        for(ExpensePayRequestData expensePayOrderData : expensePayOrderDataList) {
            ExpensePayRequestDTO expensePayOrderDTO = new ExpensePayRequestDTO();
            expensePayOrderDTO.setPaySequence(expensePayOrderData.getPaySequence());
            expensePayOrderDTO.setTotalPayAmount(expensePayOrderData.getTotalPayAmount());
            expensePayOrderDTOList.add(expensePayOrderDTO);
        }
        return expensePayOrderDTOList;
    }

    public void setPayRequestDao(PayRequestDao payRequestDao) {
        this.payRequestDao = payRequestDao;
    }
}
