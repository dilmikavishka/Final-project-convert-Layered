package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
   public  List<String> getPaymentId() throws SQLException, ClassNotFoundException;

}
