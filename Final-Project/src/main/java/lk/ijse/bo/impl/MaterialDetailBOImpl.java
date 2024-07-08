package lk.ijse.bo.impl;

import lk.ijse.bo.custome.MaterialDetailBO;
import lk.ijse.dao.custome.MaterialDetailDAO;
import lk.ijse.dao.impl.MaterialDetailDAOImpl;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.entity.MaterialDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDetailBOImpl implements MaterialDetailBO {
    MaterialDetailDAO materialDetailDAO = new MaterialDetailDAOImpl();
    @Override
    public List<MaterialDetailDTO> getAllMaterialDetail() throws SQLException, ClassNotFoundException {
        List<MaterialDetailDTO> materialDetailDTOS = new ArrayList<>();
        List<MaterialDetail> materialDetails =  materialDetailDAO.getAll();
        for (MaterialDetail materialDetail : materialDetails) {
            materialDetailDTOS.add(new MaterialDetailDTO(materialDetail.getBatId(),materialDetail.getMatId(),materialDetail.getQty()));
        }
        return materialDetailDTOS;
    }

    @Override
    public boolean saveMaterialDetail(MaterialDetailDTO md) throws SQLException, ClassNotFoundException {
        return materialDetailDAO.save(new MaterialDetail(md.getBatId(),md.getMatId(),md.getQty()));
    }

    @Override
    public boolean deleteMaterialDetail(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateMaterialDetail(MaterialDetailDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public MaterialDetailDTO searchByIdMaterialDetail(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextIdMaterialDetail() throws SQLException, ClassNotFoundException {
        return null;
    }

}
