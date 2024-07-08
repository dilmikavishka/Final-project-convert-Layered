package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.OrderDAO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public  List<String> getOrderIds() throws SQLException, ClassNotFoundException {
      ResultSet resultSet = SQLUtil.execute("SELECT orderId FROM orders");
      List<String> orderList = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString("orderId");
            orderList.add(id);
        }
        return orderList;
    }
    public Order searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM orders WHERE orderId = ?",id);
        if (resultSet.next()){
            Order order = new Order(
            resultSet.getString("orderId"),
            Date.valueOf(resultSet.getString("orderDate")),
            resultSet.getString("customerId"));
            return order;
        }
        return null;
    }
    public  boolean delete(String oId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE orders SET status = 'DELETE' WHERE orderId = ?",oId);
    }

    public  boolean save(Order order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders VALUES(?,?,?,'ACTIVE')",order.getOId(),order.getDate(),order.getCusId());
    }

    public List<Order> getAll() throws SQLException, ClassNotFoundException {
      ResultSet resultSet = SQLUtil.execute("SELECT * FROM orders WHERE  status = 'ACTIVE'");
        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            Order order = new Order(
            resultSet.getString("orderId"),
            Date.valueOf(resultSet.getString("orderDate")),
            resultSet.getString("customerId"));

            orderList.add(order);
        }
        return orderList;
    }


    public  boolean update(Order order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE orders SET orderDate = ? , customerId = ?  WHERE orderId = ?",order.getDate(),order.getCusId(),order.getOId());
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
        if (rst.next()) {
            String orderId = rst.getString("orderId");
            String prefix = "O";
            String[] split = orderId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "O001";
    }
}
