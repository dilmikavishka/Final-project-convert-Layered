package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.OredrDetailDTO;
import lk.ijse.entity.OredrDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailBO extends SuperBO {
    public List<OredrDetailDTO> getAllOd() throws SQLException, ClassNotFoundException ;

    public boolean saveOd(OredrDetail dto) throws SQLException, ClassNotFoundException;

    public boolean deleteOd(String id) throws SQLException, ClassNotFoundException ;

    public boolean updateOd(OredrDetailDTO dto) throws SQLException, ClassNotFoundException ;

    public OredrDetailDTO searchByIdOd(String id) throws SQLException, ClassNotFoundException ;

    public String generateNextIdOd() throws SQLException, ClassNotFoundException ;
}
