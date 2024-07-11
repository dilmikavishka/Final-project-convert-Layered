package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.DashboardDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.Material;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardDAOImpl implements DashboardDAO {

    @Override
    public int getActiveCustomerCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS active_customer_count FROM customer WHERE status = 'ACTIVE'");
        if(resultSet.next()) {
            return resultSet.getInt("active_customer_count");
        }
        return 0;
    }

    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS active_order_count FROM Orders WHERE status = 'ACTIVE'");
        if(resultSet.next()) {
            return resultSet.getInt("active_order_count");
        }
        return 0;
    }

    @Override
    public int getEmployeeCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLUtil.execute("SELECT COUNT(*) AS active_employee_count FROM Employee WHERE status = 'ACTIVE'");

        if(resultSet.next()) {
            return resultSet.getInt("active_employee_count");
        }
        return 0;
    }

    @Override
    public Map<String, Double> getOrdersByDay() throws SQLException, ClassNotFoundException {
        Map<String, Double> OrderByDay = new HashMap<>();
            ResultSet resultSet = SQLUtil.execute("SELECT orderDate, COUNT(*) AS order_count FROM orders GROUP BY orderDate");
                while (resultSet.next()) {
                    String date = resultSet.getString("orderDate");
                    double orderCount = resultSet.getDouble("order_count");
                    OrderByDay.put(date, orderCount);
                }

                return OrderByDay;
    }

    @Override
    public Map<String, Double> getPaymentsByDay() throws SQLException, ClassNotFoundException {
        Map<String, Double> paymentsByDay = new HashMap<>();
            ResultSet resultSet = SQLUtil.execute("SELECT paymentDate, SUM(amount) AS total_amount FROM payment GROUP BY paymentDate");
              while (resultSet.next()) {
                String date = resultSet.getString("paymentDate");
                double totalAmount = resultSet.getDouble("total_amount");
                paymentsByDay.put(date, totalAmount);
             }
            return paymentsByDay;
    }

    @Override
    public List<Material> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM  material WHERE status = 'ACTIVE' ");
        List<Material> materialList = new ArrayList<>();

        while(resultSet.next()){
            Material material = new Material(
            resultSet.getString(1),
            resultSet.getString(2),
            Date.valueOf(resultSet.getString(3)),
            Integer.parseInt(resultSet.getString(4)),
            resultSet.getString(5),
            Double.parseDouble(resultSet.getString(6)));
            materialList.add(material);
        }
        return materialList;
    }
}
