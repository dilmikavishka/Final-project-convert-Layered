package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.BatchMaterialDTO;

import java.sql.SQLException;
import java.util.List;

public interface BatchMaterialBO extends SuperBO {
    public List<BatchMaterialDTO> getAllBatchMaterial() throws SQLException, ClassNotFoundException ;
    public  boolean saveBatchMaterial(BatchMaterialDTO batchMaterial) throws SQLException, ClassNotFoundException ;
    public boolean deleteBatchMaterial(String id) throws SQLException, ClassNotFoundException ;
    public boolean updateBatchMaterial(BatchMaterialDTO dto) throws SQLException, ClassNotFoundException ;
    public BatchMaterialDTO searchByIdBatchMaterial(String id) throws SQLException, ClassNotFoundException ;
    public String generateNextIdBatchMaterial() throws SQLException, ClassNotFoundException;
}
