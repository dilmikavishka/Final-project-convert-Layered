package lk.ijse.dao.custome;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface MaterialDAO extends CrudDAO<MaterialDTO> {
   /* public  List<MaterialDTO> getAllMaterial() throws SQLException, ClassNotFoundException ;

    public  boolean saveMaterial(MaterialDTO materialDTO) throws SQLException, ClassNotFoundException ;

    public  boolean updateMaterial(MaterialDTO materialDTO) throws SQLException, ClassNotFoundException ;

    public  MaterialDTO searchByMaterialId(String id) throws SQLException, ClassNotFoundException ;

    public  String ganerateNextId() throws SQLException, ClassNotFoundException ;

    public  boolean deleteMaterial(String id) throws SQLException, ClassNotFoundException;*/
   public  List<String> getMaterialIds() throws SQLException;


    boolean updateCost(List<MaterialDetailDTO> bcList) throws SQLException, ClassNotFoundException;


}
