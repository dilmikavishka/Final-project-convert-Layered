package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.OredrDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OredrDetailDTO> {
    public  boolean Save(List<OredrDetailDTO> odList) throws SQLException, ClassNotFoundException;
}
