package lk.ijse.dao.custome;

import lk.ijse.entity.Material;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DashboardDAO {
    int getActiveCustomerCount() throws SQLException, ClassNotFoundException;

    int getOrderCount() throws SQLException, ClassNotFoundException;

    int getEmployeeCount() throws SQLException, ClassNotFoundException;

    Map<String, Double> getOrdersByDay() throws SQLException, ClassNotFoundException;

    Map<String, Double> getPaymentsByDay() throws SQLException, ClassNotFoundException;

    List<Material> getAll() throws SQLException, ClassNotFoundException;
}
