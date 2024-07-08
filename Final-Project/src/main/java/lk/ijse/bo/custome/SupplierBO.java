package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.SupplierDTO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierBO extends SuperBO {
    public List<String> getSupplierId() throws SQLException, ClassNotFoundException ;
    public SupplierDTO searchByIdSupplier(String supId) throws SQLException, ClassNotFoundException ;
    public  boolean deleteSupplier(String supId) throws SQLException, ClassNotFoundException ;
    public  boolean saveSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException ;
    public List<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException ;
    public  boolean updateSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException ;
    public  String generateNextIdSupplier() throws SQLException, ClassNotFoundException ;
}
