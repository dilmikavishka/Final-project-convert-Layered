package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.dto.MachineDTO;

import java.sql.SQLException;
import java.util.List;

public interface MachineBO extends SuperBO {
    public List<MachineDTO> getAllMachine() throws SQLException, ClassNotFoundException ;
    public  boolean saveMachine(MachineDTO dto) throws SQLException, ClassNotFoundException ;
    public  boolean deleteMachine(String id) throws SQLException, ClassNotFoundException ;
    public  boolean updateMachine(MachineDTO dto) throws SQLException, ClassNotFoundException ;
    public  MachineDTO searchByIdMachine(String id) throws SQLException, ClassNotFoundException;
    public  String generateNextIdMachine() throws SQLException, ClassNotFoundException;
}
