package lk.ijse.bo.impl;

import lk.ijse.bo.custome.OrderDetailBO;
import lk.ijse.dto.OredrDetailDTO;
import lk.ijse.entity.OredrDetail;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailBOImpl implements OrderDetailBO {
    @Override
    public List<OredrDetailDTO> getAllOd() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveOd(OredrDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public boolean deleteOd(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateOd(OredrDetailDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OredrDetailDTO searchByIdOd(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextIdOd() throws SQLException, ClassNotFoundException {
        return null;
    }
}
