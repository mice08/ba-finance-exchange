package ba.finance.exchange.midasreco.api;

import ba.finance.exchange.midasreco.api.datas.ReceiveOrderRecoData;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public interface ReceiveOrderRecoService {

    void insertReceiveOrderRecoDatas(List<ReceiveOrderRecoData> list);
}
