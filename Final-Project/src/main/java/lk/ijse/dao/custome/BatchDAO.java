package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.BatchDTO;
import lk.ijse.dto.OredrDetailDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Customer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BatchDAO extends CrudDAO<Batch> {
    public  List<String> getBatchIds() throws SQLException, ClassNotFoundException;
    boolean qtyUpdate(List<OredrDetailDTO> odList) throws SQLException, ClassNotFoundException;
    public  boolean updateQty(String batId, int qty) throws SQLException, ClassNotFoundException;
}
