package lk.ijse.bo.impl;

import lk.ijse.bo.custome.CustomerBO;
import lk.ijse.dao.custome.CustomerDAO;
import lk.ijse.dao.impl.CustomerDAOImpl;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customers = customerDAO.getAll();
        for (Customer customer : customers){
            customerDTOS.add(new CustomerDTO(customer.getId(),customer.getName(),customer.getTel(),customer.getAddress()));
        }
        return customerDTOS;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getId(),dto.getName(),dto.getTel(),dto.getAddress()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(),dto.getName(),dto.getTel(),dto.getAddress()));
    }

    @Override
    public CustomerDTO searchByIdCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.searchById(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getTel(), customer.getAddress());
    }

    @Override
    public String generateNextIdCustomer() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNextId();
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
       return customerDAO.getCustomerIds();
    }




}
