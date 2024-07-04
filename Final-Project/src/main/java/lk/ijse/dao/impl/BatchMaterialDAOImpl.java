package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.BatchMaterialDAO;
import lk.ijse.dto.BatchMaterialDTO;
import lk.ijse.entity.Batch;

import java.sql.SQLException;
import java.util.List;

public class BatchMaterialDAOImpl implements BatchMaterialDAO {
    @Override
    public List<BatchMaterialDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public  boolean save(BatchMaterialDTO batchMaterial) throws SQLException, ClassNotFoundException {
       /* String sql = "INSERT INTO material_details  VALUES (?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, batchMaterialDTO.getMaId());
        pstm.setObject(2, batchMaterialDTO.getBaId());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO material_details  VALUES (?,?)",batchMaterial.getMaId(),batchMaterial.getMaId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(BatchMaterialDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BatchMaterialDTO searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

}
