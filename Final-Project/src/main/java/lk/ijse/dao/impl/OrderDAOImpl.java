package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.OrderDAO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.Batch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public  List<String> getOrderIds() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT orderId FROM orders";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/
        ResultSet resultSet = SQLUtil.execute("SELECT orderId FROM orders");
        List<String> orderList = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString("orderId");
            orderList.add(id);
        }
        return orderList;
    }

    public  OrderDTO searchById(String id) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM orders WHERE orderId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);*/
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM orders WHERE orderId = ?",id);
        if (resultSet.next()){
            OrderDTO order = new OrderDTO(
            resultSet.getString("orderId"),
            Date.valueOf(resultSet.getString("orderDate")),
            resultSet.getString("customerId"));

            return order;
        }

        return null;
    }

   /* @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");

        if (rst.next()) {
            String batchId = rst.getString("orderId");
            String prefix = "O";
            String[] split = batchId.split(prefix); // Split using the prefix
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "O001";
    }*/

    public  boolean delete(String oId) throws SQLException, ClassNotFoundException {
        /*String sql = "UPDATE orders SET status = 'DELETE' WHERE orderId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, oId);
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE orders SET status = 'DELETE' WHERE orderId = ?",oId);
    }

    public  boolean saveOrder(OrderDTO order) throws SQLException, ClassNotFoundException {
    /*    String sql = "INSERT INTO orders VALUES(?,?,?,'ACTIVE')";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,orderDTO.getOId());
        pstm.setObject(2,orderDTO.getDate());
        pstm.setObject(3,orderDTO.getCusId());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO orders VALUES(?,?,?,'ACTIVE')",order.getOId(),order.getDate(),order.getCusId());
    }

    public List<OrderDTO> getAll() throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM orders WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

    */    ResultSet resultSet = SQLUtil.execute("SELECT * FROM orders WHERE  status = 'ACTIVE'");

        List<OrderDTO> orderList = new ArrayList<>();

        while (resultSet.next()){
            OrderDTO order = new OrderDTO(
            resultSet.getString("orderId"),
            Date.valueOf(resultSet.getString("orderDate")),
            resultSet.getString("customerId"));

            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public boolean save(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    public  boolean update(OrderDTO order) throws SQLException, ClassNotFoundException {
     /*   String sql = "UPDATE orders SET orderDate = ? , customerId = ?  WHERE orderId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,orderDTO.getDate());
        pstm.setObject(2,orderDTO.getCusId());
        pstm.setObject(3,orderDTO.getOId());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE orders SET orderDate = ? , customerId = ?  WHERE orderId = ?",order.getDate(),order.getCusId(),order.getOId());
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");

        if (rst.next()) {
            String batchId = rst.getString("orderId");
            String prefix = "O";
            String[] split = batchId.split(prefix); // Split using the prefix
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "O001";
    }
}