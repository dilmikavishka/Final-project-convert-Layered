package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BatchDTO;
import lk.ijse.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException ;
    public  boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException ;
    public  boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException ;
    public  boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException ;
    public  EmployeeDTO searchByIdEmployee(String id) throws SQLException, ClassNotFoundException;
    public  String generateNextIdEmployee() throws SQLException, ClassNotFoundException;
}
