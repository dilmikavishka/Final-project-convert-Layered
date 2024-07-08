package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.BatchDAO;
import lk.ijse.dto.BatchDTO;
import lk.ijse.dto.OredrDetailDTO;
import lk.ijse.entity.Batch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchDAOImpl implements BatchDAO {
    public  List<String> getBatchIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT batchId FROM batch");
        List<String> batchList = new ArrayList<>();
        while (resultSet.next()) {
            batchList.add(resultSet.getString(1));
        }
        return batchList;
    }

    @Override
    public boolean qtyUpdate(List<OredrDetailDTO> odList) throws SQLException, ClassNotFoundException {
        for (OredrDetailDTO od : odList) {
            boolean isUpdateQty = updateQty(od.getBaId(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }
    public  boolean updateQty(String batId, int qty) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE batch SET QtyOnHand = QtyOnHand - ? WHERE batchId = ?",qty,batId);
    }

    public  boolean delete(String id) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("UPDATE batch SET status = 'DELETE' WHERE batchId = ?",id);
    }

    public  boolean save(Batch batch) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "INSERT INTO batch VALUES (?,?,?,?,?,?,?,?,'ACTIVE')",batch.getBatchId(),batch.getBatchColor(),batch.getDes(),batch.getQtyOnHand(),batch.getDate(),batch.getEmployeeId(),batch.getOrderId(),batch.getPrice());
    }

    public List<Batch> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM  batch WHERE status = 'ACTIVE' ");
        List<Batch> batchList = new ArrayList<>();
        while(resultSet.next()){
            Batch batch = new Batch(
            resultSet.getString("batchId"),
            resultSet.getString("batchColor"),
            resultSet.getString("desingDescription"),
            Integer.parseInt(resultSet.getString("QtyOnHand")),
            Date.valueOf(resultSet.getString("Date")),
            resultSet.getString("employeeId"),
            resultSet.getString("orderId"),
            Double.parseDouble(resultSet.getString("Price")));

            batchList.add(batch);
        }
        return batchList;
    }

    public  boolean update(Batch batch) throws SQLException, ClassNotFoundException {
     return SQLUtil.execute("UPDATE batch SET batchColor = ?,desingDescription = ?,QtyOnHand = ? ,Date = ?,employeeId = ?,orderId = ?,Price = ? WHERE batchId = ?",batch.getBatchColor(),batch.getDes(),batch.getQtyOnHand(),batch.getDate(),batch.getEmployeeId(),batch.getOrderId(),batch.getPrice(),batch.getBatchId());
    }

    public  Batch searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM  batch WHERE batchId = ? ",id);
        if (resultSet.next()){
            Batch batch = new Batch(
                    resultSet.getString("batchId"),
                    resultSet.getString("batchColor"),
                    resultSet.getString("desingDescription"),
                    Integer.parseInt(resultSet.getString("QtyOnHand")),
                    Date.valueOf(resultSet.getString("Date")),
                    resultSet.getString("employeeId"),
                    resultSet.getString("orderId"),
                    Double.parseDouble(resultSet.getString("Price")));

            return batch;
        }
        return null;
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
       ResultSet rst = SQLUtil.execute("SELECT batchId FROM batch ORDER BY batchId DESC LIMIT 1");
        if (rst.next()) {
            String batchId = rst.getString("batchId");
            String prefix = "B";
            String[] split = batchId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "B001";

    }
}
