package lk.ijse.bo.impl;

import lk.ijse.bo.custome.OrderBO;
import lk.ijse.bo.custome.PlaceOrderBO;
import lk.ijse.dao.custome.QueryDAO;
import lk.ijse.dao.impl.QueryDAOImpl;
import lk.ijse.dao.custome.BatchDAO;
import lk.ijse.dao.custome.CustomerDAO;
import lk.ijse.dao.custome.OrderDetailDAO;
import lk.ijse.dao.impl.BatchDAOImpl;
import lk.ijse.dao.impl.CustomerDAOImpl;
import lk.ijse.dao.impl.OrderDetailDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BatchDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OredrDetailDTO;
import lk.ijse.entity.Batch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    BatchDAO batchDAO = new BatchDAOImpl();
    OrderBO orderBO = new OrderBOImpl();

    CustomerDAO customerDAO = new CustomerDAOImpl();

    QueryDAO queryDAO = new QueryDAOImpl();

    public  boolean placeOrder(OrderDTO order, List<OredrDetailDTO> odList) throws SQLException {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = orderBO.saveOrder(order);
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

    @Override
    public List<String> getCustomerTel() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerTel();
    }

    @Override
    public String calculateNetTotalOrder(String orderId) throws SQLException, ClassNotFoundException {
        return queryDAO.calculateNetTotalOrder(orderId);
    }

    @Override
    public BatchDTO searchByIdBatch(String batId) throws SQLException, ClassNotFoundException {
        Batch batch = batchDAO.searchById(batId);
        return new BatchDTO(batch.getBatchId(),batch.getBatchColor(),batch.getDes(),batch.getQtyOnHand(),batch.getDate(),batch.getEmployeeId(),batch.getOrderId(), batch.getPrice());

    }

    @Override
    public List<String> getBatchIds() throws SQLException, ClassNotFoundException {
        return batchDAO.getBatchIds();
    }
}
