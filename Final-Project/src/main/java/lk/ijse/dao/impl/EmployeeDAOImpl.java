package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.EmployeeDAO;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    public  List<String> getEmployeeIds() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT employeeId FROM employee";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/
        ResultSet resultSet = SQLUtil.execute("SELECT employeeId FROM employee");

        List<String> employeeList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString("employeeId");
            employeeList.add(id);
        }
        return employeeList;

    }

    public Employee searchById(String id) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM  employee WHERE employeeId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);*/

        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM  employee WHERE employeeId = ? ",id);

        if (resultSet.next()){
            Employee employee = new Employee(
            resultSet.getString("employeeId"),
            resultSet.getString("employeeName"),
            resultSet.getString("address"),
            resultSet.getString("employeeCon_Number"),
            Double.parseDouble(resultSet.getString("employeeSalary")));


            return employee;
        }
        return null;
    }

    public  boolean delete(String id) throws SQLException, ClassNotFoundException {
        /*String sql = "UPDATE employee SET status = 'DELETE' WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE employee SET status = 'DELETE' WHERE employeeId = ?",id);
    }

    public  boolean save(Employee employee) throws SQLException, ClassNotFoundException {
       /* String sql = "INSERT INTO employee VALUES (?,?,?,?,?,'ACTIVE')";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,employeeDTO.getId());
        pstm.setObject(2,employeeDTO.getName());
        pstm.setObject(3,employeeDTO.getAddress());
        pstm.setObject(4,employeeDTO.getTel());
        pstm.setObject(5,employeeDTO.getSalary());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?,'ACTIVE')",employee.getId(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getSalary());
    }

    public List<Employee> getAll() throws SQLException, ClassNotFoundException {
  /*      String sql = "SELECT * FROM  employee WHERE status = 'ACTIVE' ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);*/

        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM  employee WHERE status = 'ACTIVE' ");
        List<Employee> employeeList = new ArrayList<>();

        while(resultSet.next()){
            Employee employee = new Employee(
            resultSet.getString("employeeId"),
            resultSet.getString("employeeName"),
            resultSet.getString("address"),
            resultSet.getString("employeeCon_Number"),
            Double.parseDouble(resultSet.getString("employeeSalary")));
            employeeList.add(employee);
        }
        return employeeList;
    }

    public  boolean update(Employee employee) throws SQLException, ClassNotFoundException {
       /* String sql = "UPDATE employee SET employeeName = ?,address = ?,employeeCon_Number = ?,employeeSalary = ? WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,employeeDTO.getName());
        pstm.setObject(2,employeeDTO.getAddress());
        pstm.setObject(3,employeeDTO.getTel());
        pstm.setObject(4,employeeDTO.getSalary());
        pstm.setObject(5,employeeDTO.getId());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE employee SET employeeName = ?,address = ?,employeeCon_Number = ?,employeeSalary = ? WHERE employeeId = ?",employee.getName(),employee.getAddress(),employee.getTel(),employee.getSalary(),employee.getId());
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT employeeId FROM employee ORDER BY employeeId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/
        ResultSet rst = SQLUtil.execute("SELECT employeeId FROM employee ORDER BY employeeId DESC LIMIT 1");

        if (rst.next()) {
            String empId = rst.getString("employeeId");
            String prefix = "E";
            String[] split = empId.split(prefix); // Split using the prefix
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "E001";
    }
}
