package com.dianping.ba.finance.exchange.payresult.job
import com.dianping.ba.finance.bankorder.api.FSBankQueryService
import com.dianping.ba.finance.bankorder.api.dtos.BaseResponseDTO
import com.dianping.ba.finance.bankorder.api.enums.OrderError
import com.dianping.ba.finance.exchange.api.PayOrderService
import com.dianping.ba.finance.exchange.api.datas.PayOrderData
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus
import com.dianping.core.type.PageModel
import spock.lang.Specification
/**
 * Created by noahshen on 15/3/10.
 */
class PaymentStatusCheckerTest extends Specification {

    PaymentStatusChecker paymentStatusCheckerStub

    PayOrderService payOrderServiceMock

    FSBankQueryService fsBankQueryServiceMock

    void setup() {
        paymentStatusCheckerStub = []

        payOrderServiceMock = Mock()
        paymentStatusCheckerStub.payOrderService = payOrderServiceMock
        fsBankQueryServiceMock = Mock()
        paymentStatusCheckerStub.fsBankQueryService = fsBankQueryServiceMock
    }

    def "CheckPaymentStatus_PaySuccess"() {
        setup:

        when:
        paymentStatusCheckerStub.checkPaymentStatus()

        then:
        2 * payOrderServiceMock.paginatePayOrderListByStatus(PayOrderStatus.BANK_PAYING.value(), _ as Integer, _ as Integer) >> { args ->
            if (args[1] == 1) {
                PayOrderData payOrderData = [poId: 123]
                PageModel pm = []
                pm.records = [payOrderData]
                return pm
            }
        }
        1 * fsBankQueryServiceMock.queryPayResult(_ as String) >> { poIdStr ->
            BaseResponseDTO responseDTO = [code: OrderError.PAY_SUCCESS.code]
            responseDTO
        }
    }

    def "CheckPaymentStatus_SYSTEM_ERROR"() {
        setup:

        when:
        paymentStatusCheckerStub.checkPaymentStatus()

        then:
        2 * payOrderServiceMock.paginatePayOrderListByStatus(PayOrderStatus.BANK_PAYING.value(), _ as Integer, _ as Integer) >> { args ->
            if (args[1] == 1) {
                PayOrderData payOrderData = [poId: 123]
                PageModel pm = []
                pm.records = [payOrderData]
                return pm
            }
        }
        1 * fsBankQueryServiceMock.queryPayResult(_ as String) >> { poIdStr ->
            BaseResponseDTO responseDTO = [code: OrderError.SYSTEM_ERROR.code]
            responseDTO
        }
    }

    def "CheckPaymentStatus_QUERY_ERROR"() {
        setup:

        when:
        paymentStatusCheckerStub.checkPaymentStatus()

        then:
        2 * payOrderServiceMock.paginatePayOrderListByStatus(PayOrderStatus.BANK_PAYING.value(), _ as Integer, _ as Integer) >> { args ->
            if (args[1] == 1) {
                PayOrderData payOrderData = [poId: 123]
                PageModel pm = []
                pm.records = [payOrderData]
                return pm
            }
        }
        1 * fsBankQueryServiceMock.queryPayResult(_ as String) >> { poIdStr ->
            BaseResponseDTO responseDTO = [code: OrderError.QUERY_ERROR.code]
            responseDTO
        }
    }

    def "CheckPaymentStatus_PAY_RESULT_UNKNOWN"() {
        setup:

        when:
        paymentStatusCheckerStub.checkPaymentStatus()

        then:
        2 * payOrderServiceMock.paginatePayOrderListByStatus(PayOrderStatus.BANK_PAYING.value(), _ as Integer, _ as Integer) >> { args ->
            if (args[1] == 1) {
                PayOrderData payOrderData = [poId: 123]
                PageModel pm = []
                pm.records = [payOrderData]
                return pm
            }
        }
        1 * fsBankQueryServiceMock.queryPayResult(_ as String) >> { poIdStr ->
            BaseResponseDTO responseDTO = [code: OrderError.PAY_RESULT_UNKNOWN.code]
            responseDTO
        }
    }

    def "CheckPaymentStatus_PAY_RESULT_PENDING"() {
        setup:

        when:
        paymentStatusCheckerStub.checkPaymentStatus()

        then:
        2 * payOrderServiceMock.paginatePayOrderListByStatus(PayOrderStatus.BANK_PAYING.value(), _ as Integer, _ as Integer) >> { args ->
            if (args[1] == 1) {
                PayOrderData payOrderData = [poId: 123]
                PageModel pm = []
                pm.records = [payOrderData]
                return pm
            }
        }
        1 * fsBankQueryServiceMock.queryPayResult(_ as String) >> { poIdStr ->
            BaseResponseDTO responseDTO = [code: OrderError.PAY_RESULT_PENDING.code]
            responseDTO
        }
    }

    def "CheckPaymentStatus_ACCOUNT_NO_EMPTY"() {
        setup:

        when:
        paymentStatusCheckerStub.checkPaymentStatus()

        then:
        2 * payOrderServiceMock.paginatePayOrderListByStatus(PayOrderStatus.BANK_PAYING.value(), _ as Integer, _ as Integer) >> { args ->
            if (args[1] == 1) {
                PayOrderData payOrderData = [poId: 123]
                PageModel pm = []
                pm.records = [payOrderData]
                return pm
            }
        }
        1 * fsBankQueryServiceMock.queryPayResult(_ as String) >> { poIdStr ->
            BaseResponseDTO responseDTO = [code: OrderError.ACCOUNT_NO_EMPTY.code]
            responseDTO
        }
    }
}
