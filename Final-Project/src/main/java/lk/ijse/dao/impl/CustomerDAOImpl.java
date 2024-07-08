package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.CustomerDAO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    public  Customer searchByTel(String tel) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE customerCon_Number = ?",tel);
        if (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));

            return customer;
        }

        return null;
    }

    @Override
    public List<String> getCustomerTel() throws SQLException, ClassNotFoundException {
        List<String> telList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute( "SELECT customerCon_Number FROM customer");
        while (resultSet.next()) {
            String tel = resultSet.getString("customerCon_Number");
            telList.add(tel);
        }
        return telList;
    }


    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
      /*  String sql = "SELECT * FROM customer WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);*/

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE  status = 'ACTIVE'");

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            Customer customer = new Customer(
            resultSet.getString("customerId"),
            resultSet.getString("customerName"),
            resultSet.getString("customerCon_Number"),
            resultSet.getString("customeraddress"));

            cusList.add(customer);
        }
        return cusList;


    }

    public  boolean delete(String id) throws SQLException, ClassNotFoundException {
       /* String sql = "UPDATE customer SET status = 'DELETE' WHERE customerId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE customer SET status = 'DELETE' WHERE customerId = ?",id);
    }

    public  boolean save(Customer customer) throws SQLException, ClassNotFoundException {
      /*  String sql = "INSERT INTO customer VALUES(?,?,?,?,'ACTIVE')";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, customerDTO.getId());
        pstm.setObject(2, customerDTO.getName());
        pstm.setObject(3, customerDTO.getTel());
        pstm.setObject(4, customerDTO.getAddress());

        return pstm.executeUpdate() > 0;*/

        return SQLUtil.execute("INSERT INTO customer VALUES(?,?,?,?,'ACTIVE')",customer.getId(),customer.getName(),customer.getTel(),customer.getAddress());
    }

    public  boolean update(Customer customer) throws SQLException, ClassNotFoundException {
       /* String sql = "UPDATE customer SET customerName = ? , customerCon_Number = ? , customeraddress = ?  WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1, customerDTO.getName());
        pstm.setObject(2, customerDTO.getTel());
        pstm.setObject(3, customerDTO.getAddress());
        pstm.setObject(4, customerDTO.getId());

        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE customer SET customerName = ? , customerCon_Number = ? , customeraddress = ?  WHERE customerId = ?",customer.getName(),customer.getTel(),customer.getAddress(),customer.getId());
    }

    public  Customer searchById(String id) throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM customer WHERE customerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);*/

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE customerId = ?",id);
        if (resultSet.next()) {
            Customer customer = new Customer(
            resultSet.getString("customerId"),
            resultSet.getString("customerName"),
            resultSet.getString("customerCon_Number"),
            resultSet.getString("customeraddress"));

            return customer;
        }

        return null;
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);*/

        ResultSet rst = SQLUtil.execute("SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1");

        if (rst.next()) {
            String batchId = rst.getString("customerId");
            String prefix = "C";
            String[] split = batchId.split(prefix); // Split using the prefix
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "C001";
    }

    public  List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
      /*  String sql = "SELECT customerId FROM customer";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);*/

        ResultSet resultSet =SQLUtil.execute("SELECT customerId FROM customer");

        List<String> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString("customerId");
            cusList.add(id);
        }
        return cusList;
    }
}
