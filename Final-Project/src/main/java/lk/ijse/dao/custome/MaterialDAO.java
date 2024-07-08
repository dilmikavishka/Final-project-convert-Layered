package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.entity.Material;

import java.sql.SQLException;
import java.util.List;

public interface MaterialDAO extends CrudDAO<Material> {
    public  List<String> getMaterialIds() throws SQLException, ClassNotFoundException;
  boolean updateCost(List<MaterialDetailDTO> bcList) throws SQLException, ClassNotFoundException;

 }
