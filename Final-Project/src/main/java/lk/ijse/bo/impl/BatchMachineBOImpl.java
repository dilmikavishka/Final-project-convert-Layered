package lk.ijse.bo.impl;

import lk.ijse.bo.custome.BatchMachineBO;
import lk.ijse.dao.custome.BatchMachineDAO;
import lk.ijse.dao.impl.BatchMachinDAOImpl;
import lk.ijse.dto.BatchMachineDTO;
import lk.ijse.entity.BatchMachine;

import java.sql.SQLException;
import java.util.List;

public class BatchMachineBOImpl implements BatchMachineBO {

    BatchMachineDAO batchMachineDAO = new BatchMachinDAOImpl();
    @Override
    public List<BatchMachineDTO> getAllBatchMachine() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveBatchMachine(BatchMachineDTO batchMachine) throws SQLException, ClassNotFoundException {
        return batchMachineDAO.save(new BatchMachine(batchMachine.getMaId(),batchMachine.getBaId(),batchMachine.getDate()));
    }

    @Override
    public boolean deleteBatchMachine(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateBatchMachine(BatchMachineDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BatchMachineDTO searchByIdBatchMachine(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextIdBatchMachine() throws SQLException, ClassNotFoundException {
        return null;
    }
}
