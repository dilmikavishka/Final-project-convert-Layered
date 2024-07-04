package lk.ijse.dao;

import java.sql.SQLException;

public interface QueryDAO {

    String calculateNetTotalOrder(String orderId) throws SQLException, ClassNotFoundException;

    String calculateNetTotalBatch(String batId) throws SQLException, ClassNotFoundException;
}
