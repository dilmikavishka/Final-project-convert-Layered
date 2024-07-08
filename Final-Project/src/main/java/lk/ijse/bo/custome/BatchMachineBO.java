package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.BatchMachineDTO;

import java.sql.SQLException;
import java.util.List;

public interface BatchMachineBO extends SuperBO {
    public List<BatchMachineDTO> getAllBatchMachine() throws SQLException, ClassNotFoundException ;
    public  boolean saveBatchMachine(BatchMachineDTO batchMachine) throws SQLException, ClassNotFoundException ;
    public boolean deleteBatchMachine(String id) throws SQLException, ClassNotFoundException ;
    public boolean updateBatchMachine(BatchMachineDTO dto) throws SQLException, ClassNotFoundException ;
    public BatchMachineDTO searchByIdBatchMachine(String id) throws SQLException, ClassNotFoundException ;
    public String generateNextIdBatchMachine() throws SQLException, ClassNotFoundException ;
}
