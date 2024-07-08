package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BatchDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OredrDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    List<String> getBatchIds() throws SQLException, ClassNotFoundException;

    boolean placeOrder(OrderDTO order, List<OredrDetailDTO> odList) throws SQLException;

    List<String> getCustomerTel() throws SQLException, ClassNotFoundException;

    String calculateNetTotalOrder(String orderId) throws SQLException, ClassNotFoundException;

    BatchDTO searchByIdBatch(String batId) throws SQLException, ClassNotFoundException;
}
