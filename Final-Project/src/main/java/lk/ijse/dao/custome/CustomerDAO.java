package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    public  List<String> getCustomerIds() throws SQLException, ClassNotFoundException;
    public  Customer searchByTel(String tel) throws SQLException, ClassNotFoundException;
    List<String> getCustomerTel() throws SQLException, ClassNotFoundException;
}
