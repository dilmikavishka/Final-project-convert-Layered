package lk.ijse.bo.impl;

import lk.ijse.bo.custome.BatchMaterialBO;
import lk.ijse.dao.custome.BatchMaterialDAO;
import lk.ijse.dao.impl.BatchMaterialDAOImpl;
import lk.ijse.dto.BatchMaterialDTO;
import lk.ijse.entity.BatchMaterial;

import java.sql.SQLException;
import java.util.List;

public class BatchMaterialBOImpl implements BatchMaterialBO {
    BatchMaterialDAO batchMaterialDAO = new BatchMaterialDAOImpl();
    @Override
    public List<BatchMaterialDTO> getAllBatchMaterial() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveBatchMaterial(BatchMaterialDTO batchMaterial) throws SQLException, ClassNotFoundException {
        return batchMaterialDAO.save(new BatchMaterial(batchMaterial.getMaId(),batchMaterial.getBaId()));
    }

    @Override
    public boolean deleteBatchMaterial(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateBatchMaterial(BatchMaterialDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BatchMaterialDTO searchByIdBatchMaterial(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextIdBatchMaterial() throws SQLException, ClassNotFoundException {
        return null;
    }
}
