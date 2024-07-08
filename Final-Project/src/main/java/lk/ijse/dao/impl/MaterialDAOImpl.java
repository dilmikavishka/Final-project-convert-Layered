package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.MaterialDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.entity.Material;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAOImpl implements MaterialDAO {
    public  List<String> getMaterialIds() throws SQLException, ClassNotFoundException {
      ResultSet resultSet = SQLUtil.execute("SELECT materialId FROM material") ;
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


    public List<Material> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM  material WHERE status = 'ACTIVE'");
        List<Material> materialList = new ArrayList<>();
        while(resultSet.next()){
            Material material = new Material(
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

    public  boolean save(Material material) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO material VALUES (?,?,?,?,?,?,'ACTIVE')",material.getId(),material.getName(),material.getDate(),material.getMatQty(),material.getSupId(),material.getPrice());
    }

    public  boolean update(Material material) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE material SET name = ?, date = ?, materialQty = ?, supplierId = ?,price = ? WHERE materialId = ?",material.getName(),material.getDate(),material.getMatQty(),material.getSupId(),material.getPrice(),material.getId());

    }

    public  Material searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM  material WHERE materialId = ? ",id);
        if (resultSet.next()){
            Material material = new Material(
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
      ResultSet rst = SQLUtil.execute("SELECT materialId FROM material ORDER BY materialId DESC LIMIT 1");
        if (rst.next()) {
            String materialId = rst.getString("materialId");
            String prefix = "M";
            String[] split = materialId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            String nextId = prefix + String.format("%03d", ++idNum);
            return nextId;
        }
        return "M001";

    }

    public  boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE material SET status = 'DELETE' WHERE materialId = ?",id);

    }
}
