package lk.ijse.dao.impl;

import lk.ijse.dao.custome.MachineDAO;
import lk.ijse.dao.custome.MaterialDAO;
import lk.ijse.dao.custome.MaterialDetailDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BatchCostDTO;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.entity.BatchCost;
import lk.ijse.repository.MaterialDetailRepo;
import lk.ijse.repository.MaterialRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BatchCostDAOImpl {

    public static boolean placeCost(BatchCostDTO bc) throws SQLException {
        MaterialDAO materialDAO = new MaterialDAOImpl();
        MaterialDetailDAO materialDetailDAO = new MaterialDetailDAOImpl();

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isMaterialUpdate = materialDAO.updateCost(bc.getBcList());
            System.out.println("ddd");
            if (isMaterialUpdate) {
                boolean isMaterialDetailSave = materialDetailDAO.save(bc.getBcList());
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


}
