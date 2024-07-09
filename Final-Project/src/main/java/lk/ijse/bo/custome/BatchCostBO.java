package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BatchCostDTO;

import java.sql.SQLException;
import java.util.List;

public interface BatchCostBO extends SuperBO {

    List<String> getMaterialIds() throws SQLException, ClassNotFoundException;

    List<String> getBatchIds() throws SQLException, ClassNotFoundException;

    boolean placeCost(BatchCostDTO bc) throws SQLException;

    String calculateNetTotalBatch(String batId) throws SQLException, ClassNotFoundException;
}
