package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.OrderDetailDAO;
import lk.ijse.dto.OredrDetailDTO;
import lk.ijse.entity.Batch;
import lk.ijse.entity.OredrDetail;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    public  boolean Save(List<OredrDetailDTO> odList) throws SQLException, ClassNotFoundException {
        for (OredrDetailDTO od : odList) {
            boolean isSaved = saveOd(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    public boolean saveOd(OredrDetailDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO order_details (batchId, orderId, Qty) VALUES (?, ?, ?)",dto.getBaId(),dto.getOId(),dto.getQty());
    }


    @Override
    public List<OredrDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OredrDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OredrDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OredrDetail searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }
}
