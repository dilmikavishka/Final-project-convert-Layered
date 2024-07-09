package lk.ijse.bo.impl;

import lk.ijse.bo.custome.BatchCostBO;
import lk.ijse.bo.custome.MaterialDetailBO;
import lk.ijse.dao.custome.BatchDAO;
import lk.ijse.dao.custome.MaterialDAO;
import lk.ijse.dao.custome.MaterialDetailDAO;
import lk.ijse.dao.custome.QueryDAO;
import lk.ijse.dao.impl.BatchDAOImpl;
import lk.ijse.dao.impl.MaterialDAOImpl;
import lk.ijse.dao.impl.MaterialDetailDAOImpl;
import lk.ijse.dao.impl.QueryDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BatchCostDTO;
import lk.ijse.entity.MaterialDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BatchCostBOImpl implements BatchCostBO {
    MaterialDAO materialDAO = new MaterialDAOImpl();
    BatchDAO batchDAO = new BatchDAOImpl();
    MaterialDetailDAO materialDetailDAO = new MaterialDetailDAOImpl();
    QueryDAO queryDAO = new QueryDAOImpl();

    public  boolean placeCost(BatchCostDTO bc) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isMaterialUpdate = materialDAO.updateCost(bc.getBcList());
            if (isMaterialUpdate) {
                boolean isMaterialDetailSave = materialDetailDAO.saveMd(bc.getBcList());
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

    @Override
    public String calculateNetTotalBatch(String batId) throws SQLException, ClassNotFoundException {
        return queryDAO.calculateNetTotalBatch(batId);
    }


    @Override
    public List<String> getMaterialIds() throws SQLException, ClassNotFoundException {
        return materialDAO.getMaterialIds();
    }

    @Override
    public List<String> getBatchIds() throws SQLException, ClassNotFoundException {
        return batchDAO.getBatchIds();
    }
}
