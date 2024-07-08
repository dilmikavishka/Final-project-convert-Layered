package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OredrDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    public List<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException ;
    public boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException ;
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException ;
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException ;
    public OrderDTO searchByIdOrder(String id) throws SQLException, ClassNotFoundException ;
    public String generateNextIdOrder() throws SQLException, ClassNotFoundException ;
}
