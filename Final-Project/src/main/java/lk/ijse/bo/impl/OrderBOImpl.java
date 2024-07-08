package lk.ijse.bo.impl;

import lk.ijse.bo.custome.OrderBO;
import lk.ijse.dao.custome.OrderDAO;
import lk.ijse.dao.impl.OrderDAOImpl;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OredrDetailDTO;
import lk.ijse.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public List<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        List<Order> orders = orderDAO.getAll();
        for (Order order : orders) {
            orderDTOS.add(new OrderDTO(order.getOId(),order.getDate(),order.getCusId()));
        }
        return orderDTOS;
    }

    @Override
    public boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new Order(dto.getOId(),dto.getDate(),dto.getCusId()));
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Order(dto.getOId(),dto.getDate(),dto.getCusId()));
    }

    @Override
    public OrderDTO searchByIdOrder(String id) throws SQLException, ClassNotFoundException {
        Order orders = orderDAO.searchById(id);
        return new OrderDTO(orders.getOId(),orders.getDate(),orders.getCusId());
    }

    @Override
    public String generateNextIdOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNextId();
    }
}
