package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.PaymentDAO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Batch;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    public List<PaymentDTO> getAll() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM  payment WHERE status = 'ACTIVE' ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM  payment WHERE status = 'ACTIVE' ");

        List<PaymentDTO> payList = new ArrayList<>();

        while(resultSet.next()){
            PaymentDTO payment = new PaymentDTO(
            resultSet.getString("paymentId"),
            Date.valueOf(resultSet.getString("paymentDate")),
            Double.parseDouble(resultSet.getString("amount")),
            resultSet.getString("type"),
            resultSet.getString("orderId"));

            payList.add(payment);
        }
        return payList;
    }

    public  boolean delete(String id) throws SQLException, ClassNotFoundException {
      /*  String sql = "UPDATE payment SET status = 'DELETE' WHERE paymentId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE payment SET status = 'DELETE' WHERE paymentId = ?",id);
    }

    public  boolean save(PaymentDTO payment) throws SQLException, ClassNotFoundException {
       /* String sql = "INSERT INTO payment VALUES (?,?,?,?,?,'ACTIVE')";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,paymentDTO.getPaymentId());
        pstm.setObject(2,paymentDTO.getPaymentDate());
        pstm.setObject(3,paymentDTO.getAmount());
        pstm.setObject(4,paymentDTO.getType());
        pstm.setObject(5,paymentDTO.getOId());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO payment VALUES (?,?,?,?,?,'ACTIVE')",payment.getPaymentId(),payment.getPaymentDate(),payment.getAmount(),payment.getType(),payment.getOId());
    }

    public  boolean update(PaymentDTO payment) throws SQLException, ClassNotFoundException {
       /* String sql = "UPDATE payment SET paymentDate = ?,amount = ?,type = ?,orderId = ? WHERE paymentId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,paymentDTO.getPaymentDate());
        pstm.setObject(2,paymentDTO.getAmount());
        pstm.setObject(3,paymentDTO.getType());
        pstm.setObject(4,paymentDTO.getOId());
        pstm.setObject(5,paymentDTO.getPaymentId());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE payment SET paymentDate = ?,amount = ?,type = ?,orderId = ? WHERE paymentId = ?",payment.getPaymentDate(),payment.getAmount(),payment.getType(),payment.getOId(),payment.getPaymentId());
    }

    public  PaymentDTO searchById(String id) throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM  payment WHERE paymentId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);*/

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM  payment WHERE paymentId = ? ",id);

        if (resultSet.next()){
            PaymentDTO payment = new PaymentDTO(
                    resultSet.getString("paymentId"),
                    Date.valueOf(resultSet.getString("paymentDate")),
                    Double.parseDouble(resultSet.getString("amount")),
                    resultSet.getString("type"),
                    resultSet.getString("orderId"));

            return payment;
        }
        return null;
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);*/

        ResultSet rst = SQLUtil.execute("SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1");

        if (rst.next()) {
            String batchId = rst.getString("paymentId");
            String prefix = "P";
            String[] split = batchId.split(prefix); // Split using the prefix
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "P001";
    }

    public  List<String> getPaymentId() throws SQLException, ClassNotFoundException {
/*
        String sql = "SELECT paymentId FROM payment";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/

        ResultSet resultSet = SQLUtil.execute("SELECT paymentId FROM payment");

        List<String> orderList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString("paymentId");
            orderList.add(id);
        }
        return orderList;
    }
}
