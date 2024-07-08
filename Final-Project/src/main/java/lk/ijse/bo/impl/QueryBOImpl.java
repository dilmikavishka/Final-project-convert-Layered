package lk.ijse.bo.impl;

import lk.ijse.bo.custome.QueryBO;
import lk.ijse.dao.custome.QueryDAO;
import lk.ijse.dao.impl.QueryDAOImpl;

import java.sql.SQLException;

public class QueryBOImpl implements QueryBO {
    QueryDAO queryDAO = new QueryDAOImpl();

    @Override
    public String calculateNetTotalOrder(String orderId) throws SQLException, ClassNotFoundException {
        return queryDAO.calculateNetTotalOrder(orderId);
    }

    @Override
    public String calculateNetTotalBatch(String batId) throws SQLException, ClassNotFoundException {
        return queryDAO.calculateNetTotalBatch(batId);
    }
}
