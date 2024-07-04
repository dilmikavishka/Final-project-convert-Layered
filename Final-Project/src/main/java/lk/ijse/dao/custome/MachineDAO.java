package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.MachineDTO;
import lk.ijse.entity.Machine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MachineDAO extends CrudDAO<Machine> {
   /* public  List<MachineDTO> getAllMachine() throws SQLException, ClassNotFoundException ;

    public  boolean deleteMachine(String maId) throws SQLException, ClassNotFoundException ;

    public  boolean saveMachine(MachineDTO machineDTO) throws SQLException, ClassNotFoundException ;

    public  boolean updateMachine(MachineDTO machineDTO) throws SQLException, ClassNotFoundException ;

    public  MachineDTO searchByMachineId(String maId) throws SQLException, ClassNotFoundException ;

    public  String generateNextId() throws SQLException, ClassNotFoundException;*/
}
