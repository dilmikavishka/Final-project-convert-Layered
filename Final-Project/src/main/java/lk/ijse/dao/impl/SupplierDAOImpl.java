package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.SupplierDAO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    public  List<String> getSupplierId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT supplierId FROM supplier");

        List<String> payList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            payList.add(id);
        }
        return payList;
    }

    public Supplier searchById(String supId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier WHERE supplierId = ?",supId);
        if (resultSet.next()){
            Supplier supplier = new Supplier(
            resultSet.getString(1),
            resultSet.getString(2),
            Date.valueOf(resultSet.getString(3)),
            resultSet.getString(4),
            resultSet.getString(5));

            return supplier;
        }
        return null;
    }

    public  boolean delete(String supId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET status = 'DELETE' WHERE supplierId = ?",supId);
    }

    public  boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier VALUES(?,?,?,?,?,'ACTIVE')",supplier.getSupId(),supplier.getName(),supplier.getDate(),supplier.getTel(),supplier.getPayId());
    }

    public List<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM supplier WHERE  status = 'ACTIVE'");
        List<Supplier> supplierList = new ArrayList<>();
        while (resultSet.next()){
            Supplier supplier = new Supplier(
            resultSet.getString(1),
            resultSet.getString(2),
            Date.valueOf(resultSet.getString(3)),
            resultSet.getString(4),
            resultSet.getString(5));

            supplierList.add(supplier);
        }
        return supplierList;
    }

    public  boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET supplierName = ? , supplierDate = ? , supplierC0n_Number = ?,paymentId = ?  WHERE supplierId = ?",supplier.getName(),supplier.getDate(),supplier.getTel(),supplier.getPayId(),supplier.getSupId());
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supplierId FROM supplier ORDER BY supplierId DESC LIMIT 1");
        if (rst.next()) {
            String supId = rst.getString(1);
            String prefix = "S";
            String[] split = supId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;
        }
        return "S001";
    }

}
