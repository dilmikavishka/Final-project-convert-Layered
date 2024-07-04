package lk.ijse.bo.impl;

import lk.ijse.dao.custome.BatchDAO;
import lk.ijse.dao.custome.OrderDAO;
import lk.ijse.dao.custome.OrderDetailDAO;
import lk.ijse.dao.impl.BatchDAOImpl;
import lk.ijse.dao.impl.OrderDAOImpl;
import lk.ijse.dao.impl.OrderDetailDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OredrDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderBOImpl {


    public static boolean placeOrder(OrderDTO order, List<OredrDetailDTO> odList) throws SQLException {
        OrderDAO orderDAO = new OrderDAOImpl();
        BatchDAO batchDAO = new BatchDAOImpl();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = orderDAO.saveOrder(order);
            System.out.println("oooooooooo");
            if (isOrderSaved) {
                boolean isQtyUpdated = batchDAO.qtyUpdate(odList);
                System.out.println("bbbbbbbb");
                if (isQtyUpdated){
                    boolean isOrderDetailSaved = orderDetailDAO.Save(odList);
                    System.out.println("odddddddddd");
                    if (isOrderDetailSaved){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }catch (Exception e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

}
