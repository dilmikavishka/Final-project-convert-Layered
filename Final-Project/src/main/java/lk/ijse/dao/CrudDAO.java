package lk.ijse.dao;

import lk.ijse.entity.Batch;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    public List<T> getAll() throws SQLException, ClassNotFoundException ;
    public  boolean save(T dto) throws SQLException, ClassNotFoundException ;
    public  boolean delete(String id) throws SQLException, ClassNotFoundException ;
    public  boolean update(T dto) throws SQLException, ClassNotFoundException ;
    public  T searchById(String id) throws SQLException, ClassNotFoundException;
    public  String generateNextId() throws SQLException, ClassNotFoundException;
}
