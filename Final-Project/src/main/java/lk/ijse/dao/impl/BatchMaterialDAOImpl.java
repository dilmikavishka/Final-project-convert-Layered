package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.BatchMaterialDAO;
import lk.ijse.dto.BatchMaterialDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.BatchMaterial;

import java.sql.SQLException;
import java.util.List;

public class BatchMaterialDAOImpl implements BatchMaterialDAO {
    @Override
    public List<BatchMaterial> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public  boolean save(BatchMaterial batchMaterial) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO material_details  VALUES (?,?)",batchMaterial.getMaId(),batchMaterial.getBaId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(BatchMaterial dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BatchMaterial searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

}
