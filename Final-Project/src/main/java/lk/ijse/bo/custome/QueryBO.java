package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;

import java.sql.SQLException;

public interface QueryBO extends SuperBO {
    String calculateNetTotalOrder(String orderId) throws SQLException, ClassNotFoundException;

    String calculateNetTotalBatch(String batId) throws SQLException, ClassNotFoundException;
}
