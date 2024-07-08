package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custome.MaterialDetailBO;
import lk.ijse.bo.impl.MaterialDetailBOImpl;
import lk.ijse.dao.custome.BatchDAO;
import lk.ijse.dao.custome.MaterialDetailDAO;
import lk.ijse.dao.impl.BatchDAOImpl;
import lk.ijse.dao.impl.MaterialDetailDAOImpl;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.Tm.MaterialDetailTm;
import lk.ijse.entity.MaterialDetail;


import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MaterialDetailFormController {

    @FXML
    private AnchorPane anpMaterialsDetails;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colBatchId;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private JFXComboBox<String> comBatchId;

    @FXML
    private JFXComboBox<String> comMaterialId;

    @FXML
    private TextField txtQty;

    @FXML
    private TableView<MaterialDetailTm> tblMaterialsDetails;
    BatchDAO batchDAO = new BatchDAOImpl();
   MaterialDetailBO materialDetailBO = new MaterialDetailBOImpl();

    public void initialize() {
        setCellValueFactory();
        loadAllMaterialDetail();

    }

    private void setCellValueFactory() {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batId"));
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("matId"));
    }

    private void loadAllMaterialDetail() {
        ObservableList<MaterialDetailTm> obList = FXCollections.observableArrayList();
        try {
            List<MaterialDetailDTO> mdList = materialDetailBO.getAllMaterialDetail();
            for( MaterialDetailDTO md : mdList){
                MaterialDetailTm tm = new MaterialDetailTm(md.getBatId(), md.getMatId(), md.getQty());
                obList.add(tm);
            }

            tblMaterialsDetails.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void comBatchIdOnAction(ActionEvent event) {
        String id = comBatchId.getValue();

        try {
            batchDAO.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void comMaterialIdOnAction(ActionEvent event) {
        String id = comMaterialId.getValue();

        try {
            materialDetailBO.searchByIdMaterialDetail(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
