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

public interface OrderDAO extends CrudDAO<Order> {
    public  List<String> getOrderIds() throws SQLException, ClassNotFoundException ;
}
