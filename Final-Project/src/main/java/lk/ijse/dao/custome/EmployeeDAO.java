package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeDAO extends CrudDAO<Employee> {
    public  List<String> getEmployeeIds() throws SQLException, ClassNotFoundException;

   /* public  EmployeeDTO searchByEmployeeId(String id) throws SQLException, ClassNotFoundException ;

    public  boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException ;

    public  boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    public  List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException ;

    public  boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException ;

    public  String generateNextId() throws SQLException, ClassNotFoundException;*/
}
