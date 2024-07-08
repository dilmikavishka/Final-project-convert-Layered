package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextFeild;
import lk.ijse.bo.custome.MaterialBO;
import lk.ijse.bo.impl.MaterialBOImpl;
import lk.ijse.dao.custome.MaterialDAO;
import lk.ijse.dao.custome.SupplierDAO;
import lk.ijse.dao.impl.MaterialDAOImpl;
import lk.ijse.dao.impl.SupplierDAOImpl;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.Tm.MatirialTm;
import lk.ijse.entity.Material;
import lk.ijse.repository.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MaterialFormController {

    @FXML
    private AnchorPane anpMaterialManage;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> comSupId;
    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colMatid;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableView<MatirialTm> tblMaterials;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtMatId;

    @FXML
    private TextField txtMatQty;

    @FXML
    private TextField txtName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSupId;
    @FXML
    private JFXButton btnMaterialsDetail;
   // MaterialDAO materialDAO = new MaterialDAOImpl();
    MaterialBO materialBO = new MaterialBOImpl();
    SupplierDAO supplierDAO = new SupplierDAOImpl();

    @SneakyThrows
    public void initialize() {
        setCellValueFactory();
        loadAllMaterial();
        getSupId();
        generateNextMaterialId();
        txtDate.setText(String.valueOf(LocalDate.now()));

    }

    private void generateNextMaterialId() throws SQLException, ClassNotFoundException {
        String id = materialBO.generateNextIdMaterial();
        txtMatId.setText(id);
    }

    private void getSupId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> payList = supplierDAO.getSupplierId();
            for (String id : payList){
                obList.add(id);
            }
            comSupId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colMatid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("matQty"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    @SneakyThrows
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextMaterialId();
        txtDate.setText(String.valueOf(LocalDate.now()));

    }

    private void clearFields() {
        txtMatId.setText("");
        txtName.setText("");
        txtDate.setText("");
        txtMatQty.setText("");
        comSupId.setValue(null);
        txtPrice.setText("");

    }

    @SneakyThrows
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtMatId.getText();

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(materialBO.deleteMaterial(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMaterial();
        clearFields();
        generateNextMaterialId();
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    @SneakyThrows
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtMatId.getText();
        String name = txtName.getText();
        Date date = Date.valueOf(txtDate.getText());
        int qty = Integer.parseInt(txtMatQty.getText());
        String supId = comSupId.getValue();
        if (supId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields.").show();
            return;
        }

        double price = Double.parseDouble(txtPrice.getText());

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(materialBO.saveMaterial(new MaterialDTO(id,name,date,qty,supId,price))) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMaterial();
        clearFields();
        generateNextMaterialId();
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.ID,txtMatId)) return false;
        if (!Regex.setTextColor(TextFeild.NAME,txtName)) return false;
        if (!Regex.setTextColor(TextFeild.QTY,txtMatQty)) return false;
        if (!Regex.setTextColor(TextFeild.SALARY,txtPrice)) return false;
        return true;
    }

    private void loadAllMaterial() {
        ObservableList<MatirialTm> obList = FXCollections.observableArrayList();
        try {
            List<MaterialDTO> materialList = materialBO.getAllMaterial();
            for( MaterialDTO material : materialList){
                MatirialTm tm = new MatirialTm(material.getId(), material.getName(), material.getDate(), material.getMatQty(), material.getSupId(), material.getPrice(), new JFXButton());
                obList.add(tm);
            }

            tblMaterials.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtMatId.getText();
        String name = txtName.getText();
        Date date = Date.valueOf(txtDate.getText());
        int qty = Integer.parseInt(txtMatQty.getText());
        String supId = comSupId.getValue();
        double price = Double.parseDouble(txtPrice.getText());

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(materialBO.updateMaterial(new MaterialDTO(id,name,date,qty,supId,price))) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMaterial();
        clearFields();
        generateNextMaterialId();
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void comSupIdOnAction(ActionEvent event) {
        String supId = comSupId.getValue();
        try {
            supplierDAO.searchById(supId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtMatId.getText();

        MaterialDTO material = materialBO.searchByIdMaterial(id);

        if (material != null) {
            txtMatId.setText(material.getId());
            txtName.setText(material.getName());
            txtDate.setText(String.valueOf(material.getDate()));
            txtMatQty.setText(String.valueOf(material.getMatQty()));
            comSupId.setValue(material.getSupId());
            txtPrice.setText(String.valueOf(material.getPrice()));

        }else {
            new Alert(Alert.AlertType.INFORMATION,"Machine is not found !").show();
        }
    }

   /* private void getCurrentMaterialIds() {
        try {
            String currentId = MaterialRepo.getCurrentId();

            String nextMaterialId = generateNextMaterialId(currentId);
            txtMatId.setText(nextMaterialId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

   /* private String generateNextMaterialId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("M");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "M" + String.format("%03d", ++idNum);
        }
        return"M001";
    }*/


    @FXML
    void btnMaterialsDetailOnAction(ActionEvent event) throws IOException {
        AnchorPane matPane = FXMLLoader.load(this.getClass().getResource("/view/MaterialDetailForm.fxml"));


        anpMaterialManage.getChildren().clear();
        anpMaterialManage.getChildren().add(matPane);

    }

    @FXML
    void txtMatIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtMatId);
    }

    @FXML
    void txtMatQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.QTY,txtMatQty);
    }
    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.NAME,txtName);
    }

    @FXML
    void txtPriceOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.SALARY,txtPrice);
    }
}
