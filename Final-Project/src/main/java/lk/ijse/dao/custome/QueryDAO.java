package lk.ijse.dao.custome;

import lk.ijse.dao.SuperDAO;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {
    String calculateNetTotalOrder(String orderId) throws SQLException, ClassNotFoundException;
    String calculateNetTotalBatch(String batId) throws SQLException, ClassNotFoundException;
}
