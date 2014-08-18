package ba.finance.exchange.midasreco.biz.dao;

import ba.finance.exchange.midasreco.api.datas.InvoiceRecoData;
import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public interface InvoiceRecoDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    void insertInvoiceRecoDatas(@DAOParam("list") List<InvoiceRecoData> list);

}
