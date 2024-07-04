package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.BatchCost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BatchCostRepo {
    public static boolean placeCost(BatchCost bc) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isMaterialUpdate = MaterialRepo.update(bc.getBcList());
            System.out.println("ddd");
            if (isMaterialUpdate) {
                boolean isMaterialDetailSave = MaterialDetailRepo.save(bc.getBcList());
                System.out.println("sss");
                if (isMaterialDetailSave) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public static String calculateNetTotal(String batId) {
        double netTotal = 0.0;

        String sql = "SELECT SUM(mid.qty * m.price) AS batch_cost FROM Material_Details mid JOIN Material m on mid.materialId = m.materialId JOIN Batch b ON mid.batchId = b.batchId WHERE b.batchId = ?";

        try (PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setString(1, batId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    netTotal = resultSet.getDouble("batch_cost");
                }
                return String.valueOf(netTotal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
