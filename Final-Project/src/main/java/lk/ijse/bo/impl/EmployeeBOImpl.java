package lk.ijse.bo.impl;

import lk.ijse.bo.custome.EmployeeBO;
import lk.ijse.dao.custome.EmployeeDAO;
import lk.ijse.dao.impl.EmployeeDAOImpl;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Override
    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        List<Employee> employees = employeeDAO.getAll();
        for (Employee employee : employees){
            employeeDTOS.add(new EmployeeDTO(employee.getId(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getSalary()));
        }
        return employeeDTOS;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(dto.getId(),dto.getName(),dto.getAddress(),dto.getTel(),dto.getSalary()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getId(),dto.getName(),dto.getAddress(),dto.getTel(),dto.getSalary()));

    }

    @Override
    public EmployeeDTO searchByIdEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.searchById(id);
        return new EmployeeDTO(employee.getId(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getSalary());
    }

    @Override
    public String generateNextIdEmployee() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNextId();
    }
}
