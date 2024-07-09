package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextFeild;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custome.PaymentBO;
import lk.ijse.bo.custome.SupplierBO;
import lk.ijse.bo.impl.PaymentBOImpl;
import lk.ijse.bo.impl.SupplierBOImpl;
import lk.ijse.dao.custome.PaymentDAO;
import lk.ijse.dao.custome.SupplierDAO;
import lk.ijse.dao.impl.PaymentDAOImpl;
import lk.ijse.dao.impl.SupplierDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.Tm.SupplierTm;
import lk.ijse.entity.Payment;
import lk.ijse.entity.Supplier;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierFormController {

    @FXML
    private AnchorPane anpSupplierManage;

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
    private TableColumn<?, ?> colPayId;

    @FXML
    private TableColumn<?, ?> colSupDate;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableColumn<?, ?> colSupTel;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private JFXComboBox<String> comPayId;

    @FXML
    private TextField txtSupDate;

    @FXML
    private TextField txtSupId;

    @FXML
    private TextField txtSupName;

    @FXML
    private TextField txtSupTel;

    @FXML
    private JFXButton btnSupList;
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);


    @SneakyThrows
    public void initialize() {
        setCellValueFactory();
        loadAllSupplier();
        getPayIds();
        generateNextSupplierId();
        txtSupDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextSupplierId() throws SQLException, ClassNotFoundException {
        String id = supplierBO.generateNextIdSupplier();
        txtSupId.setText(id);
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("SupId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colPayId.setCellValueFactory(new PropertyValueFactory<>("payId"));
    }

    private void getPayIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> payList = paymentBO.getPaymentId();
            for (String id : payList){
                obList.add(id);
            }
            comPayId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextSupplierId();
        txtSupDate.setText(String.valueOf(LocalDate.now()));

    }

    private void clearFields() {
        txtSupId.setText("");
        txtSupName.setText("");
        txtSupDate.setText("");
        txtSupTel.setText("");
        comPayId.setValue(null);
    }

    @SneakyThrows
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String SupId = txtSupId.getText();

        try {

            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
            }
            if(supplierBO.deleteSupplier(SupId)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSupplier();
        clearFields();
        generateNextSupplierId();
        txtSupDate.setText(String.valueOf(LocalDate.now()));
    }

    @SneakyThrows
    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String SupId = txtSupId.getText();
        String name = txtSupName.getText();
        Date date = Date.valueOf(txtSupDate.getText());
        String tel = txtSupTel.getText();
        String payId = comPayId.getValue();

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
                return;
            }

            if (supplierBO.saveSupplier(new SupplierDTO(SupId,name,date,tel,payId))){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier is Save").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadAllSupplier();
        clearFields();
        generateNextSupplierId();
        txtSupDate.setText(String.valueOf(LocalDate.now()));

    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.ID,txtSupId)) return false;
        if (!Regex.setTextColor(TextFeild.NAME,txtSupName)) return false;
        if (!Regex.setTextColor(TextFeild.CONTACT,txtSupTel)) return false;
        return true;
    }


    @FXML
    void comPayIdOnAction(ActionEvent event) {
        String payId = comPayId.getValue();

        try {
            PaymentDTO payment = paymentBO.searchByIdPayment(payId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void loadAllSupplier() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDTO> supplierList = supplierBO.getAllSupplier();
            for( SupplierDTO supplier : supplierList){
                SupplierTm tm = new SupplierTm(supplier.getSupId(), supplier.getName(), supplier.getDate(), supplier.getTel(), supplier.getPayId());
                obList.add(tm);
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @SneakyThrows
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String SupId = txtSupId.getText();
        String name = txtSupName.getText();
        Date date = Date.valueOf(txtSupDate.getText());
        String tel = txtSupTel.getText();
        String payId = comPayId.getValue();

        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        try {
            if (supplierBO.updateSupplier(new SupplierDTO(SupId,name,date,tel,payId))){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier is update").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSupplier();
        clearFields();
        generateNextSupplierId();
        txtSupDate.setText(String.valueOf(LocalDate.now()));

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtSupId.getText();

        SupplierDTO supplier = supplierBO.searchByIdSupplier(id);

        if (supplier != null) {
            txtSupId.setText(supplier.getSupId());
            txtSupName.setText(supplier.getName());
            txtSupDate.setText(String.valueOf(supplier.getDate()));
            txtSupTel.setText(supplier.getTel());
            comPayId.setValue(supplier.getPayId());

        }else {
            new Alert(Alert.AlertType.INFORMATION,"Supplier is not found !").show();
        }

    }

   /* private void getCurrentSupplierIds() {
        try {
            String currentId = SupplierRepo.getCurrentId();

            String nextSupplierId = generateNextSupplierId(currentId);
            txtSupId.setText(nextSupplierId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextSupplierId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("S");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "S" + String.format("%03d", ++idNum);
        }
        return"S001";
    }*/


    @FXML
    void btnSupListOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\ASUS\\IdeaProjects\\My-Final-Project-Convert-LayeredArchitecture\\Final-Project\\src\\main\\resources\\Report\\SupplierList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("supId",txtSupId.getText());
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport,data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }


    @FXML
    void txtSupIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtSupId);
    }

    @FXML
    void txtSupNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.NAME,txtSupName);
    }

    @FXML
    void txtSupTelOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.CONTACT,txtSupTel);
    }
}
