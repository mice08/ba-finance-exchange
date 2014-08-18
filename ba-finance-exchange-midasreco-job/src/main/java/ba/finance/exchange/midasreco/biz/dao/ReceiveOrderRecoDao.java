package ba.finance.exchange.midasreco.biz.dao;

import ba.finance.exchange.midasreco.api.datas.ReceiveOrderRecoData;
import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public interface ReceiveOrderRecoDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    void insertReceiveOrderRecoDatas(@DAOParam("list") List<ReceiveOrderRecoData> list);
}
