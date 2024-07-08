package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dto.MaterialDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MaterialDetailBO extends SuperBO {
    public List<MaterialDetailDTO> getAllMaterialDetail() throws SQLException, ClassNotFoundException ;

    public boolean saveMaterialDetail(MaterialDetailDTO md) throws SQLException, ClassNotFoundException ;

    public boolean deleteMaterialDetail(String id) throws SQLException, ClassNotFoundException ;

    public boolean updateMaterialDetail(MaterialDetailDTO dto) throws SQLException, ClassNotFoundException ;

    public  MaterialDetailDTO searchByIdMaterialDetail(String id) throws SQLException, ClassNotFoundException ;

    public String generateNextIdMaterialDetail() throws SQLException, ClassNotFoundException ;


}
