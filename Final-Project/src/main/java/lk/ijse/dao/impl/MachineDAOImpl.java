package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.MachineDAO;
import lk.ijse.dto.MachineDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Machine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineDAOImpl implements MachineDAO {
    public List<Machine> getAll() throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM Machine WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);*/

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Machine WHERE  status = 'ACTIVE'");

        List<Machine> machineList = new ArrayList<>();

        while (resultSet.next()){
            Machine machine = new Machine(
            resultSet.getString("machineId"),
            resultSet.getString("machineName"),
            resultSet.getString("description"));

            machineList.add(machine);
        }
        return machineList;
    }

    public  boolean delete(String maId) throws SQLException, ClassNotFoundException {
      /*  String sql = "UPDATE Machine SET status = 'DELETE' WHERE machineId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, maId);
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE Machine SET status = 'DELETE' WHERE machineId = ?",maId);
    }

    public  boolean save(Machine machine) throws SQLException, ClassNotFoundException {
        /*String sql = "INSERT INTO Machine VALUES(?,?,?,'ACTIVE')";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,machineDTO.getMaId());
        pstm.setObject(2,machineDTO.getName());
        pstm.setObject(3,machineDTO.getDescription());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO Machine VALUES(?,?,?,'ACTIVE')",machine.getMaId(),machine.getName(),machine.getDescription());
    }

    public  boolean update(Machine machine) throws SQLException, ClassNotFoundException {
  /*      String sql = "UPDATE Machine SET machineName = ?,description = ?   WHERE machineId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,machineDTO.getName());
        pstm.setObject(2,machineDTO.getDescription());
        pstm.setObject(3,machineDTO.getMaId());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute( "UPDATE Machine SET machineName = ?,description = ?   WHERE machineId = ?" ,machine.getName(),machine.getDescription(),machine.getMaId());

    }

    public  Machine searchById(String maId) throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM Machine WHERE machineId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, maId);*/

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Machine WHERE machineId = ?",maId);

        if (resultSet.next()){
            Machine machine = new Machine(
                    resultSet.getString("machineId"),
                    resultSet.getString("machineName"),
                    resultSet.getString("description"));

            return machine;
        }

        return null;
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT machineId FROM Machine ORDER BY machineId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/
        ResultSet rst = SQLUtil.execute("SELECT machineId FROM Machine ORDER BY machineId DESC LIMIT 1");

        if (rst.next()) {
            String batchId = rst.getString("machineId");
            String prefix = "M";
            String[] split = batchId.split(prefix); // Split using the prefix
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "M001";
    }
}
