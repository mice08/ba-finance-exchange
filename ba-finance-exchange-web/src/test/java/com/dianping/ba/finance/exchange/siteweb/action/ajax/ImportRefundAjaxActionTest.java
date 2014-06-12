package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ImportRefundAjaxActionTest {


    private ImportRefundAjaxAction importRefundAjaxActionStub;

    private ExecutorService executorService;

    private PayOrderService payOrderServiceMock;


    @Before
    public void runBeforeTest() {
        importRefundAjaxActionStub = new ImportRefundAjaxAction();

        payOrderServiceMock = mock(PayOrderService.class);
        importRefundAjaxActionStub.setPayOrderService(payOrderServiceMock);

        executorService = Executors.newSingleThreadExecutor();
        importRefundAjaxActionStub.setExecutorService(executorService);
    }

    @Test
    public void testImportRefundWhenFileDuplicateIds() throws Exception {
        String path = getClass().getResource("duplicateInvalid.xls").getPath();
        importRefundAjaxActionStub.setRefundFile(new File(path));

        String actual = importRefundAjaxActionStub.importRefund();

        Assert.assertEquals("success", actual);
        Assert.assertEquals("[P234]", importRefundAjaxActionStub.getInvalidRefundMap().get("duplicateIds"));
    }

    @Test
    public void testImportRefundWhenFileMissingColumn() throws Exception {
        String path = getClass().getResource("columnMissing.xls").getPath();
        importRefundAjaxActionStub.setRefundFile(new File(path));

        String actual = importRefundAjaxActionStub.importRefund();

        Assert.assertEquals("success", actual);
        Assert.assertEquals("导入退票文件格式错误！正确格式为两列，第一列为退票付款单号，第二列为退票原因。", importRefundAjaxActionStub.getExcelInvalidMsg());
    }

    @Test
    public void testImportRefundWhenFileFormatError() throws Exception {
        String path = getClass().getResource("formatError.txt").getPath();
        importRefundAjaxActionStub.setRefundFile(new File(path));

        String actual = importRefundAjaxActionStub.importRefund();

        Assert.assertEquals("success", actual);
        Assert.assertEquals("导入退票文件格式错误！正确格式为两列，第一列为退票付款单号，第二列为退票原因。", importRefundAjaxActionStub.getExcelInvalidMsg());
    }

    @Test
    public void testImportRefundFailWhenSystemError() throws Exception {
        String path = getClass().getResource("error.xls").getPath();
        importRefundAjaxActionStub.setRefundFile(new File(path));
        RefundResultDTO refundResultDTO = new RefundResultDTO();
        when(payOrderServiceMock.refundPayOrder(anyList(), anyInt())).thenReturn(refundResultDTO);

        importRefundAjaxActionStub.setMsg(null);

        String actual = importRefundAjaxActionStub.importRefund();

        Assert.assertEquals("success", actual);
        Assert.assertEquals(500, importRefundAjaxActionStub.getCode());

    }

    @Test
    public void testImportRefundFailWhenRefundIdError() throws Exception {
        String path = getClass().getResource("error.xls").getPath();
        importRefundAjaxActionStub.setRefundFile(new File(path));
        RefundResultDTO refundResultDTO = new RefundResultDTO();
        Map<String, RefundFailedReason> reasonMap = new HashMap<String, RefundFailedReason>();
        reasonMap.put("P123", RefundFailedReason.INFO_EMPTY);
        refundResultDTO.setRefundFailedMap(reasonMap);
        when(payOrderServiceMock.refundPayOrder(anyList(), anyInt())).thenReturn(refundResultDTO);

        String actual = importRefundAjaxActionStub.importRefund();

        Assert.assertEquals("success", actual);
        Assert.assertEquals(200, importRefundAjaxActionStub.getCode());
        Assert.assertEquals("P123", importRefundAjaxActionStub.getInvalidRefundMap().get("notFoundRefundIds"));

    }

    @Test
    public void testImportRefundSuccess() throws Exception {
        String path = getClass().getResource("error.xls").getPath();
        importRefundAjaxActionStub.setRefundFile(new File(path));
        RefundResultDTO refundResultDTO = new RefundResultDTO();
        refundResultDTO.setRefundTotalAmount(BigDecimal.TEN);
        when(payOrderServiceMock.refundPayOrder(anyList(), anyInt())).thenReturn(refundResultDTO);

        String actual = importRefundAjaxActionStub.importRefund();

        Assert.assertEquals("success", actual);
        Assert.assertEquals(200, importRefundAjaxActionStub.getCode());
        Assert.assertEquals(1, importRefundAjaxActionStub.getMsg().get("successCount"));
        Assert.assertEquals("10", importRefundAjaxActionStub.getMsg().get("refundTotalAmount").toString());

    }

    @Test
    public void testImportRefundSuccessWhenExcelContainsEmptyRow() throws Exception {
        String path = getClass().getResource("excelWithEmptyRow.xls").getPath();
        importRefundAjaxActionStub.setRefundFile(new File(path));
        RefundResultDTO refundResultDTO = new RefundResultDTO();
        refundResultDTO.setRefundTotalAmount(BigDecimal.TEN);
        when(payOrderServiceMock.refundPayOrder(anyList(), anyInt())).thenReturn(refundResultDTO);

        String actual = importRefundAjaxActionStub.importRefund();

        Assert.assertEquals("success", actual);
        Assert.assertEquals(200, importRefundAjaxActionStub.getCode());
        Assert.assertEquals(1, importRefundAjaxActionStub.getMsg().get("successCount"));
        Assert.assertEquals("10", importRefundAjaxActionStub.getMsg().get("refundTotalAmount").toString());

    }


    @Test
    public void testImportRefundSuccessWhenColumnHasTab() throws Exception {
        String path = getClass().getResource("columnHasTab.xls").getPath();
        importRefundAjaxActionStub.setRefundFile(new File(path));
        RefundResultDTO refundResultDTO = new RefundResultDTO();
        refundResultDTO.setRefundTotalAmount(BigDecimal.TEN);
        when(payOrderServiceMock.refundPayOrder(anyList(), anyInt())).thenReturn(refundResultDTO);

        String actual = importRefundAjaxActionStub.importRefund();

        Assert.assertEquals("success", actual);
        Assert.assertEquals(200, importRefundAjaxActionStub.getCode());
        Assert.assertEquals(1, importRefundAjaxActionStub.getMsg().get("successCount"));
        Assert.assertEquals("10", importRefundAjaxActionStub.getMsg().get("refundTotalAmount").toString());

    }
}