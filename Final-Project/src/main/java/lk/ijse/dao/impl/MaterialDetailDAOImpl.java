package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.MaterialDetailDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.MaterialDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDetailDAOImpl implements MaterialDetailDAO {

    public List<MaterialDetail> getAll() throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM material_details WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
*/
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM material_details WHERE  status = 'ACTIVE'");
        List<MaterialDetail> mdList = new ArrayList<>();

        while (resultSet.next()){
            MaterialDetail materialDetail = new MaterialDetail(
            resultSet.getString(1),
            resultSet.getString(2),
            Integer.parseInt(resultSet.getString(3)));

            mdList.add(materialDetail);
        }
        return mdList;
    }

    @Override
    public boolean save(MaterialDetail md) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO material_details VALUES(?, ?, ?, 'ACTIVE')";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, md.getBatId());
        pstm.setString(2, md.getMatId());
        pstm.setInt(3, md.getQty());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(MaterialDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    public  MaterialDetail searchById(String id) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM  material_details WHERE batchId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);*/
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM  material_details WHERE batchId = ? ",id);

        if (resultSet.next()){
            MaterialDetail materialDetail = new MaterialDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Integer.parseInt(resultSet.getString(3)));

            return materialDetail;
        }
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean saveMd(List<MaterialDetailDTO> bcList) throws SQLException, ClassNotFoundException {
        for (MaterialDetailDTO md : bcList) {
            boolean isSaved = SQLUtil.execute("INSERT INTO material_details VALUES(?, ?, ?, 'ACTIVE')",md.getBatId(),md.getMatId(),md.getQty());
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }


}
