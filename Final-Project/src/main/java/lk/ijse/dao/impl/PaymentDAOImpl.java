package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.PaymentDAO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Payment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM  payment WHERE status = 'ACTIVE' ");
        List<Payment> payList = new ArrayList<>();
        while(resultSet.next()){
            Payment payment = new Payment(
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
        return SQLUtil.execute("UPDATE payment SET status = 'DELETE' WHERE paymentId = ?",id);
    }

    public  boolean save(Payment payment) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO payment VALUES (?,?,?,?,?,'ACTIVE')",payment.getPaymentId(),payment.getPaymentDate(),payment.getAmount(),payment.getType(),payment.getOId());
    }

    public  boolean update(Payment payment) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE payment SET paymentDate = ?,amount = ?,type = ?,orderId = ? WHERE paymentId = ?",payment.getPaymentDate(),payment.getAmount(),payment.getType(),payment.getOId(),payment.getPaymentId());
    }

    public  Payment searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM  payment WHERE paymentId = ? ",id);
        if (resultSet.next()){
            Payment payment = new Payment(
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
        ResultSet rst = SQLUtil.execute("SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1");
        if (rst.next()) {
            String paymentId = rst.getString("paymentId");
            String prefix = "P";
            String[] split = paymentId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;
        }
        return "P001";
    }

    public  List<String> getPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT paymentId FROM payment");
        List<String> orderList = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString("paymentId");
            orderList.add(id);
        }
        return orderList;
    }

}
