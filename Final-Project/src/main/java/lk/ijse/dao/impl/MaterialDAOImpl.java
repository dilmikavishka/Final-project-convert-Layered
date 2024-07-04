package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.MaterialDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAOImpl implements MaterialDAO {
    public  List<String> getMaterialIds() throws SQLException {
        String sql = "SELECT materialId FROM material";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> matList = new ArrayList<>();
        while (resultSet.next()) {
            matList.add(resultSet.getString(1));
        }
        return matList;
    }

    @Override
    public boolean updateCost(List<MaterialDetailDTO> bcList) throws SQLException, ClassNotFoundException {
        for (MaterialDetailDTO md : bcList) {
            boolean isUpdateQty =SQLUtil.execute("UPDATE material SET materialQty = materialQty - ? WHERE materialId = ?",md.getQty(),md.getMatId());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }


    public List<MaterialDTO> getAll() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM  material WHERE status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);*/

        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM  material WHERE status = 'ACTIVE'");
        List<MaterialDTO> materialList = new ArrayList<>();
        while(resultSet.next()){
            MaterialDTO material = new MaterialDTO(
            resultSet.getString("materialId"),
            resultSet.getString("name"),
            Date.valueOf(resultSet.getString("date")),
            Integer.parseInt(resultSet.getString("materialQty")),
            resultSet.getString("supplierId"),
            Double.parseDouble(resultSet.getString("price")));
            materialList.add(material);
        }
        return materialList;
    }

    public  boolean save(MaterialDTO material) throws SQLException, ClassNotFoundException {
       /* String sql = "INSERT INTO material VALUES (?,?,?,?,?,?,'ACTIVE')";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,materialDTO.getId());
        pstm.setObject(2,materialDTO.getName());
        pstm.setObject(3,materialDTO.getDate());
        pstm.setObject(4,materialDTO.getMatQty());
        pstm.setObject(5,materialDTO.getSupId());
        pstm.setObject(6,materialDTO.getPrice());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO material VALUES (?,?,?,?,?,?,'ACTIVE')",material.getId(),material.getName(),material.getDate(),material.getMatQty(),material.getSupId(),material.getPrice());
    }

    public  boolean update(MaterialDTO material) throws SQLException, ClassNotFoundException {
       /* String sql = "UPDATE material SET name = ?, date = ?, materialQty = ?, supplierId = ?,price = ? WHERE materialId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,materialDTO.getName());
        pstm.setObject(2,materialDTO.getDate());
        pstm.setObject(3,materialDTO.getMatQty());
        pstm.setObject(4,materialDTO.getSupId());
        pstm.setObject(5,materialDTO.getPrice());
        pstm.setObject(6,materialDTO.getId());
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE material SET name = ?, date = ?, materialQty = ?, supplierId = ?,price = ? WHERE materialId = ?",material.getName(),material.getDate(),material.getMatQty(),material.getSupId(),material.getPrice(),material.getId());

    }

    public  MaterialDTO searchById(String id) throws SQLException, ClassNotFoundException {
      /*  String sql = "SELECT * FROM  material WHERE materialId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);*/

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM  material WHERE materialId = ? ",id);

        if (resultSet.next()){
            MaterialDTO material = new MaterialDTO(
                    resultSet.getString("materialId"),
                    resultSet.getString("name"),
                    Date.valueOf(resultSet.getString("date")),
                    Integer.parseInt(resultSet.getString("materialQty")),
                    resultSet.getString("supplierId"),
                    Double.parseDouble(resultSet.getString("price")));

            return material;
        }
        return null;
    }

    public  String generateNextId() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT materialId FROM material ORDER BY materialId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/
        ResultSet rst = SQLUtil.execute("SELECT materialId FROM material ORDER BY materialId DESC LIMIT 1");

        if (rst.next()) {
            String batchId = rst.getString("materialId");
            String prefix = "M";
            String[] split = batchId.split(prefix); // Split using the prefix
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;

        }
        return "M001";

    }

    public  boolean delete(String id) throws SQLException, ClassNotFoundException {
   /*     String sql = "UPDATE material SET status = 'DELETE' WHERE materialId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);
        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE material SET status = 'DELETE' WHERE materialId = ?",id);

    }
}