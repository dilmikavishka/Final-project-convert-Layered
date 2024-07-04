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
import lk.ijse.dao.custome.OrderDAO;
import lk.ijse.dao.custome.PaymentDAO;
import lk.ijse.dao.impl.OrderDAOImpl;
import lk.ijse.dao.impl.PaymentDAOImpl;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.Tm.PaymentTm;
import lk.ijse.entity.Order;
import lk.ijse.entity.Payment;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaymentFormController {

    public TextField txtPaymentSarch;
    @FXML
    private AnchorPane apnPayment;

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
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtPaymentDate;

    @FXML
    private ChoiceBox<String> choiseType;

    @FXML
    private TextField txtPaymentID;
    @FXML
    private TextField txtStatus;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private JFXComboBox<String> comOrderId;
    OrderDAO orderDAO = new OrderDAOImpl();
    PaymentDAO paymentDAO = new PaymentDAOImpl();

    @FXML
    void comOrderIdOnAction(ActionEvent event) {
        String oId = comOrderId.getValue();

        try {
            OrderDTO order = orderDAO.searchById(oId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @SneakyThrows
    public void initialize() {
        setCellValueFactory();
        loadAllPayments();
        getOId();
        generateNextPaymentId();

        ObservableList<String> paymentTypes = FXCollections.observableArrayList("CASH","CARD");
        choiseType.setItems(paymentTypes);

        txtPaymentDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextPaymentId() throws SQLException, ClassNotFoundException {
        String id = paymentDAO.generateNextId();
        txtPaymentID.setText(id);
    }

    private void getOId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> orderList = orderDAO.getOrderIds();
            for (String id : orderList){
                obList.add(id);
            }
            comOrderId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("oId"));


    }

    private void loadAllPayments() {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
        try {
            List<PaymentDTO> payList = paymentDAO.getAll();
            for (PaymentDTO payment : payList){
                PaymentTm tm = new PaymentTm(payment.getPaymentId(), payment.getPaymentDate(), payment.getAmount(), payment.getType(), payment.getOId());
                obList.add(tm);
            }
            tblPayment.setItems(obList);
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
        generateNextPaymentId();
        txtPaymentDate.setText(String.valueOf(LocalDate.now()));
    }

    private void clearFields() {
        txtPaymentID.setText("");
        txtPaymentDate.setText("");
        txtAmount.setText("");
        choiseType.setValue(null);
        comOrderId.setValue(null);
    }


    @SneakyThrows
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtPaymentID.getText();

        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }
        try {
            if (paymentDAO.delete(id)){
                new Alert(Alert.AlertType.CONFIRMATION,"payment is deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        loadAllPayments();
        clearFields();
        generateNextPaymentId();
        txtPaymentDate.setText(String.valueOf(LocalDate.now()));
    }

    @SneakyThrows
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtPaymentID.getText();
        Date date = Date.valueOf(txtPaymentDate.getText());
        
        double amount = 0;
        if (!txtAmount.getText().isEmpty()) {
            amount = Double.parseDouble(txtAmount.getText());
        }

        String type =choiseType.getValue();
        String oId = comOrderId.getValue();


        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }
        try {

            if (paymentDAO.save(new PaymentDTO(id,date,amount,type,oId))){
                new Alert(Alert.AlertType.CONFIRMATION,"payment is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }
        loadAllPayments();
        clearFields();
        generateNextPaymentId();
        txtPaymentDate.setText(String.valueOf(LocalDate.now()));
    }

    @SneakyThrows
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtPaymentID.getText();
        Date date = Date.valueOf(txtPaymentDate.getText());

        double amount = 0;
        if (!txtAmount.getText().isEmpty()) {
            amount = Double.parseDouble(txtAmount.getText());
        }


        String type = choiseType.getValue();
        String oId = comOrderId.getValue();

        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }
        try {
            if (paymentDAO.update(new PaymentDTO(id,date,amount,type,oId))){
                new Alert(Alert.AlertType.CONFIRMATION,"payment is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllPayments();
        clearFields();
        generateNextPaymentId();
        txtPaymentDate.setText(String.valueOf(LocalDate.now()));

    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.ID,txtPaymentID)) return false;
        if (!Regex.setTextColor(TextFeild.SALARY,txtAmount)) return false;
        return true;

    }

    @FXML
    public void txtPaymentSarchOnAction(ActionEvent actionEvent) {
        String id = txtPaymentID.getText();

        try {
            PaymentDTO payment = paymentDAO.searchById(id);
            if (payment != null){
                txtPaymentID.setText(payment.getPaymentId());
                txtPaymentDate.setText(String.valueOf(payment.getPaymentDate()));
                txtAmount.setText(String.valueOf(payment.getAmount()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"payment is not found").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

 /*   private void getCurrentPaymentIds() {
        try {
            String currentId = PaymentRepo.getCurrentId();

            String nextPaymentId = generateNextPaymentId(currentId);
            txtPaymentID.setText(nextPaymentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPaymentId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("P");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "P" + String.format("%03d", ++idNum);
        }
        return"P001";
    }*/


    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.SALARY,txtAmount);
    }

    @FXML
    void txtPaymentIDOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtPaymentID);
    }
}
