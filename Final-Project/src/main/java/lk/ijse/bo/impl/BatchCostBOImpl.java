package lk.ijse.bo.impl;

import lk.ijse.bo.custome.BatchCostBO;
import lk.ijse.bo.custome.MaterialDetailBO;
import lk.ijse.dao.custome.MaterialDAO;
import lk.ijse.dao.custome.MaterialDetailDAO;
import lk.ijse.dao.impl.MaterialDAOImpl;
import lk.ijse.dao.impl.MaterialDetailDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BatchCostDTO;
import lk.ijse.entity.MaterialDetail;

import java.sql.Connection;
import java.sql.SQLException;

public class BatchCostBOImpl implements BatchCostBO {

    public static boolean placeCost(BatchCostDTO bc) throws SQLException {
        MaterialDAO materialDAO = new MaterialDAOImpl();
        MaterialDetailDAO materialDetailDAO = new MaterialDetailDAOImpl();

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isMaterialUpdate = materialDAO.updateCost(bc.getBcList());
            System.out.println("ddd");
            if (isMaterialUpdate) {
                boolean isMaterialDetailSave = materialDetailDAO.saveMd(bc.getBcList());
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
