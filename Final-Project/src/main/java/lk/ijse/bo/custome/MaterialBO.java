package lk.ijse.bo.custome;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MaterialBO extends SuperBO {
    public List<String> getMaterialIds() throws SQLException ;
    public boolean updateCost(List<MaterialDetailDTO> bcList) throws SQLException, ClassNotFoundException ;
    public List<MaterialDTO> getAllMaterial() throws SQLException, ClassNotFoundException ;
    public  boolean saveMaterial(MaterialDTO material) throws SQLException, ClassNotFoundException ;
    public  boolean updateMaterial(MaterialDTO material) throws SQLException, ClassNotFoundException ;
    public  MaterialDTO searchByIdMaterial(String id) throws SQLException, ClassNotFoundException ;
    public  String generateNextIdMaterial() throws SQLException, ClassNotFoundException ;
    public  boolean deleteMaterial(String id) throws SQLException, ClassNotFoundException ;
}
