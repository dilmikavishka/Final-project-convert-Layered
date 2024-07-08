package lk.ijse.bo.impl;

import lk.ijse.bo.custome.SupplierBO;
import lk.ijse.dao.custome.SupplierDAO;
import lk.ijse.dao.impl.SupplierDAOImpl;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = new SupplierDAOImpl();
    @Override
    public List<String> getSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getSupplierId();
    }

    @Override
    public SupplierDTO searchByIdSupplier(String supId) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.searchById(supId);
        return new SupplierDTO(supplier.getSupId(),supplier.getName(),supplier.getDate(),supplier.getTel(),supplier.getPayId());
    }

    @Override
    public boolean deleteSupplier(String supId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supId);
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplier.getSupId(),supplier.getName(),supplier.getDate(),supplier.getTel(),supplier.getPayId()));
    }

    @Override
    public List<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> supplierDTOS = new ArrayList<>();
        List<Supplier> suppliers = supplierDAO.getAll();
        for (Supplier supplier : suppliers) {
            supplierDTOS.add(new SupplierDTO(supplier.getSupId(),supplier.getName(),supplier.getDate(),supplier.getTel(),supplier.getPayId()));
        }
        return supplierDTOS;
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplier.getSupId(),supplier.getName(),supplier.getDate(),supplier.getTel(),supplier.getPayId()));

    }

    @Override
    public String generateNextIdSupplier() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNextId();
    }
}
