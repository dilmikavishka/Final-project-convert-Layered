package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {
    public List<String> getSupplierId() throws SQLException, ClassNotFoundException;

   /* public  SupplierDTO searchBySupplierId(String supId) throws SQLException ;

    public  boolean deleteSupplier(String supId) throws SQLException ;

    public  boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException ;

    public  List<SupplierDTO> getAllSupplier() throws SQLException ;

    public  boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException ;

    public  String generateNextId() throws SQLException;*/
}
