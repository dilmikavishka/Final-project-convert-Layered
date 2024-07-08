package lk.ijse.bo.impl;

import lk.ijse.bo.custome.MaterialBO;
import lk.ijse.dao.custome.MaterialDAO;
import lk.ijse.dao.impl.MaterialDAOImpl;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.entity.Material;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBOImpl implements MaterialBO {

    MaterialDAO materialDAO = new MaterialDAOImpl();

    @Override
    public List<String> getMaterialIds() throws SQLException {
        return materialDAO.getMaterialIds();
    }

    @Override
    public boolean updateCost(List<MaterialDetailDTO> bcList) throws SQLException, ClassNotFoundException {
        return materialDAO.updateCost(bcList);
    }

    @Override
    public List<MaterialDTO> getAllMaterial() throws SQLException, ClassNotFoundException {
        List<MaterialDTO> materialDTOS = new ArrayList<>();
        List<Material> materials = materialDAO.getAll();
        for (Material m : materials) {
           materialDTOS.add(new MaterialDTO(m.getId(),m.getName(),m.getDate(),m.getMatQty(),m.getSupId(),m.getPrice()));
        }
        return materialDTOS;
    }

    @Override
    public boolean saveMaterial(MaterialDTO material) throws SQLException, ClassNotFoundException {
        return materialDAO.save(new Material(material.getId(),material.getName(),material.getDate(),material.getMatQty(),material.getSupId(),material.getPrice()));
    }

    @Override
    public boolean updateMaterial(MaterialDTO material) throws SQLException, ClassNotFoundException {
        return materialDAO.update(new Material(material.getId(),material.getName(),material.getDate(),material.getMatQty(),material.getSupId(),material.getPrice()));

    }

    @Override
    public MaterialDTO searchByIdMaterial(String id) throws SQLException, ClassNotFoundException {
        Material material = materialDAO.searchById(id);
        return new MaterialDTO(material.getId(),material.getName(),material.getDate(),material.getMatQty(),material.getSupId(),material.getPrice());
    }

    @Override
    public String generateNextIdMaterial() throws SQLException, ClassNotFoundException {
        return materialDAO.generateNextId();
    }

    @Override
    public boolean deleteMaterial(String id) throws SQLException, ClassNotFoundException {
        return materialDAO.delete(id);
    }
}
