package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.QueryDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public String calculateNetTotalOrder(String orderId) throws SQLException, ClassNotFoundException {
        double netTotal = 0.0;
            ResultSet rst = SQLUtil.execute("SELECT SUM(b.Price * od.qty) " + "FROM batch b " + "JOIN order_details od ON b.batchId = od.batchId " + "WHERE od.orderId = ?",orderId);
                while (rst.next()) {
                    double c = rst.getDouble(1);
                    netTotal=netTotal+c;
                }
                return String.valueOf((netTotal));
            }

    @Override
    public String calculateNetTotalBatch(String batId) throws SQLException, ClassNotFoundException {
        double netTotal = 0.0;
           ResultSet resultSet = SQLUtil.execute("SELECT SUM(mid.qty * m.price) AS batch_cost FROM Material_Details mid JOIN Material m on mid.materialId = m.materialId JOIN Batch b ON mid.batchId = b.batchId WHERE b.batchId = ?",batId);
                if (resultSet.next()) {
                    netTotal = resultSet.getDouble("batch_cost");
                }
                return String.valueOf(netTotal);
            }
    }


