package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.BatchMachineDAO;
import lk.ijse.dto.BatchMachineDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.BatchMachine;

import java.sql.SQLException;
import java.util.List;

public class BatchMachinDAOImpl implements BatchMachineDAO {
    @Override
    public List<BatchMachine> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public  boolean save(BatchMachine batchMachine) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO machine_details  VALUES (?,?,?)",batchMachine.getMaId(),batchMachine.getBaId(),batchMachine.getDate());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(BatchMachine dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BatchMachine searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }


}
