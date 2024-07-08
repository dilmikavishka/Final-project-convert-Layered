package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.PaymentDTO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentBO extends SuperBO {
    public List<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException ;
    public  boolean deletePayment(String id) throws SQLException, ClassNotFoundException ;
    public  boolean savePayment(PaymentDTO payment) throws SQLException, ClassNotFoundException ;
    public  boolean updatePayment(PaymentDTO payment) throws SQLException, ClassNotFoundException ;
    public  PaymentDTO searchByIdPayment(String id) throws SQLException, ClassNotFoundException ;
    public  String generateNextIdPayment() throws SQLException, ClassNotFoundException ;
    public  List<String> getPaymentId() throws SQLException, ClassNotFoundException ;
}
