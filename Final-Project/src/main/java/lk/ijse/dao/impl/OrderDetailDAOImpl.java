package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.OrderDetailDAO;
import lk.ijse.dto.OredrDetailDTO;
import lk.ijse.entity.Batch;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    public  boolean Save(List<OredrDetailDTO> odList) throws SQLException, ClassNotFoundException {
        for (OredrDetailDTO od : odList) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<OredrDetailDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OredrDetailDTO dto) throws SQLException, ClassNotFoundException {
       /* String sql = "INSERT INTO order_details (batchId, orderId, Qty) VALUES (?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, dto.getBaId());
        pstm.setString(2, dto.getOId());
        pstm.setString(3, String.valueOf(dto.getQty()));

        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO order_details (batchId, orderId, Qty) VALUES (?, ?, ?)",dto.getBaId(),dto.getOId(),dto.getQty());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OredrDetailDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OredrDetailDTO searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }
}
