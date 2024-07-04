package lk.ijse.bo.impl;

import lk.ijse.bo.custome.MachineBO;
import lk.ijse.dao.custome.MachineDAO;
import lk.ijse.dao.impl.MachineDAOImpl;
import lk.ijse.dto.MachineDTO;
import lk.ijse.entity.Machine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineBOImpl implements MachineBO {
    MachineDAO machineDAO  = new MachineDAOImpl();

    @Override
    public List<MachineDTO> getAllMachine() throws SQLException, ClassNotFoundException {
        List<MachineDTO> machineDTOS = new ArrayList<>();
        List<Machine> machines = machineDAO.getAll();
        for (Machine machine : machines){
            machineDTOS.add(new MachineDTO(machine.getMaId(),machine.getName(),machine.getDescription()));
        }
        return machineDTOS;
    }

    @Override
    public boolean saveMachine(MachineDTO dto) throws SQLException, ClassNotFoundException {
        return machineDAO.save(new Machine(dto.getMaId(),dto.getName(),dto.getDescription()));
    }

    @Override
    public boolean deleteMachine(String id) throws SQLException, ClassNotFoundException {
        return machineDAO.delete(id);
    }

    @Override
    public boolean updateMachine(MachineDTO dto) throws SQLException, ClassNotFoundException {
        return machineDAO.update(new Machine(dto.getMaId(),dto.getName(),dto.getDescription()));
    }

    @Override
    public MachineDTO searchByIdMachine(String id) throws SQLException, ClassNotFoundException {
        Machine machine = machineDAO.searchById(id);
        return new MachineDTO(machine.getMaId(),machine.getName(),machine.getDescription());
    }

    @Override
    public String generateNextIdMachine() throws SQLException, ClassNotFoundException {
        return machineDAO.generateNextId();
    }
}
