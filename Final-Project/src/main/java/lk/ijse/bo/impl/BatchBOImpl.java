package lk.ijse.bo.impl;

import lk.ijse.bo.custome.BatchBO;
import lk.ijse.dao.custome.BatchDAO;
import lk.ijse.dao.impl.BatchDAOImpl;
import lk.ijse.dto.BatchDTO;
import lk.ijse.entity.Batch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchBOImpl implements BatchBO {
    BatchDAO  batchDAO = new BatchDAOImpl();

    @Override
    public List<BatchDTO> getAllBatch() throws SQLException, ClassNotFoundException {
        List<BatchDTO> batchDTOS = new ArrayList<>();
        List<Batch> batches = batchDAO.getAll();
        for (Batch batch : batches){
            batchDTOS.add(new BatchDTO(batch.getBatchId(),batch.getBatchColor(),batch.getDes(),batch.getQtyOnHand(),batch.getDate(),batch.getEmployeeId(),batch.getOrderId(),batch.getPrice()));
        }
        return batchDTOS;
    }

    @Override
    public boolean saveBatch(BatchDTO dto) throws SQLException, ClassNotFoundException {
        return batchDAO.save(new Batch(dto.getBatchId(),dto.getBatchColor(),dto.getDes(),dto.getQtyOnHand(),dto.getDate(),dto.getEmployeeId(),dto.getOrderId(),dto.getPrice()));
    }

    @Override
    public boolean deleteBatch(String id) throws SQLException, ClassNotFoundException {
        return batchDAO.delete(id);
    }

    @Override
    public boolean updateBatch(BatchDTO dto) throws SQLException, ClassNotFoundException {
        return batchDAO.update(new Batch(dto.getBatchId(),dto.getBatchColor(),dto.getDes(),dto.getQtyOnHand(),dto.getDate(),dto.getEmployeeId(),dto.getOrderId(),dto.getPrice()));

    }

    @Override
    public BatchDTO searchByIdBatch(String id) throws SQLException, ClassNotFoundException {
        Batch batch = batchDAO.searchById(id);
        return new BatchDTO(batch.getBatchId(),batch.getBatchColor(),batch.getDes(),batch.getQtyOnHand(),batch.getDate(),batch.getEmployeeId(),batch.getOrderId(), batch.getPrice());
    }

    @Override
    public String generateNextIdBatch() throws SQLException, ClassNotFoundException {
        return batchDAO.generateNextId();
    }
}
