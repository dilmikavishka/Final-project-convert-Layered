package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public List<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException ;
    public  boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException ;
    public  boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException ;
    public  boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException ;
    public  CustomerDTO searchByIdCustomer(String id) throws SQLException, ClassNotFoundException;
    public  String generateNextIdCustomer() throws SQLException, ClassNotFoundException;
    public  List<String> getCustomerIds() throws SQLException, ClassNotFoundException;



}
