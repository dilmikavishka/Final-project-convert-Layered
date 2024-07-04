package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.Order;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<OrderDTO> {
    public  List<String> getOrderIds() throws SQLException, ClassNotFoundException ;

  /*  public  OrderDTO searchByOrderId(String id) throws SQLException, ClassNotFoundException ;

    public  boolean deleteOrder(String oId) throws SQLException, ClassNotFoundException ;

    public  boolean saveOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException ;

    public  List<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException ;

    public  boolean updateOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException ;

    public  String generateNextId() throws SQLException, ClassNotFoundException;*/
  public  boolean saveOrder(OrderDTO order) throws SQLException, ClassNotFoundException;
}
