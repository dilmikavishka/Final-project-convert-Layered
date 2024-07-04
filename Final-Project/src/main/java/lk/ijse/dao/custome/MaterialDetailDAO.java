package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.entity.MaterialDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MaterialDetailDAO extends CrudDAO<MaterialDetailDTO> {
   // public boolean saveMat(MaterialDetailDTO md) throws SQLException;

    boolean save(List<MaterialDetailDTO> bcList) throws SQLException, ClassNotFoundException;

   /* public  List<MaterialDetailDTO> getAllMaterial() throws SQLException, ClassNotFoundException ;

    public  MaterialDetailDTO searchByMaterialId(String id) throws SQLException, ClassNotFoundException;*/
}
