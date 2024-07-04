package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BatchDTO;

import java.sql.SQLException;
import java.util.List;

public interface BatchBO extends SuperBO {
    public List<BatchDTO> getAllBatch() throws SQLException, ClassNotFoundException ;
    public  boolean saveBatch(BatchDTO dto) throws SQLException, ClassNotFoundException ;
    public  boolean deleteBatch(String id) throws SQLException, ClassNotFoundException ;
    public  boolean updateBatch(BatchDTO dto) throws SQLException, ClassNotFoundException ;
    public  BatchDTO searchByIdBatch(String id) throws SQLException, ClassNotFoundException;
    public  String generateNextIdBatch() throws SQLException, ClassNotFoundException;
}
