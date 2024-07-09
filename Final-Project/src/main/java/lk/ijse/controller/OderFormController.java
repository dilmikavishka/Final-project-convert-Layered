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
import lk.ijse.bo.custome.CustomerBO;
import lk.ijse.bo.custome.OrderBO;
import lk.ijse.bo.impl.CustomerBOImpl;
import lk.ijse.bo.impl.OrderBOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custome.CustomerDAO;
import lk.ijse.dao.custome.OrderDAO;
import lk.ijse.dao.impl.CustomerDAOImpl;
import lk.ijse.dao.impl.OrderDAOImpl;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.Tm.OrderTm;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Order;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OderFormController {

    @FXML
    private AnchorPane anpOrderManage;

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
    private JFXComboBox<String> comCustId;

    @FXML
    private JFXComboBox<String> comPayId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableView<OrderTm> tblOrderManage;

    @FXML
    private TextField txtOrderDate;

    @FXML
    private TextField txtOrderId;
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    @SneakyThrows
    public void initialize() {
        setCellValueFactory();
        loadAllOrders();
        getCustmoreIds();
        generateNextOrderId();
        txtOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextOrderId() throws SQLException, ClassNotFoundException {
        String id = orderBO.generateNextIdOrder();
        txtOrderId.setText(id);
    }

    private void getCustmoreIds() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> cusList = customerBO.getCustomerIds();
            obList.addAll(cusList);
            comCustId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("oId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CusId"));
    }

    @SneakyThrows
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextOrderId();
        txtOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void clearFields() {
        txtOrderId.clear();
        txtOrderDate.clear();
        comCustId.setValue(null);
    }

    @SneakyThrows
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String oId = txtOrderId.getText();
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR,"Check fields", ButtonType.OK).show();
            return;
        }
        try {
            if (orderBO.deleteOrder(oId)){
                new Alert(Alert.AlertType.CONFIRMATION,"Order is deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllOrders();
        clearFields();
        generateNextOrderId();
        txtOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    @SneakyThrows
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String oId = txtOrderId.getText();
        Date date = Date.valueOf(txtOrderDate.getText());
        String CusId = comCustId.getValue();
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR,"Check fields", ButtonType.OK).show();
            return;
        }
        try {
            if (orderBO.saveOrder(new OrderDTO(oId, date, CusId))){
                new Alert(Alert.AlertType.CONFIRMATION,"Order is saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllOrders();
        clearFields();
        generateNextOrderId();
        txtOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private boolean isValied() {
        return Regex.setTextColor(TextFeild.ID, txtOrderId);
    }

    private void loadAllOrders() {
        ObservableList<OrderTm> obList = FXCollections.observableArrayList();
        try {
            List<OrderDTO> orderList = orderBO.getAllOrder();
            for (OrderDTO order : orderList){
                OrderTm tm = new OrderTm(order.getOId(), order.getDate(), order.getCusId());
                obList.add(tm);
            }
            tblOrderManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
           e.printStackTrace();
        }
    }

    @SneakyThrows
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String oId = txtOrderId.getText();
        Date date = Date.valueOf(txtOrderDate.getText());
        String CusId = comCustId.getValue();
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR,"Check fields", ButtonType.OK).show();
            return;
        }
        try {
            if (orderBO.updateOrder(new OrderDTO(oId, date, CusId))){
                new Alert(Alert.AlertType.CONFIRMATION,"Order is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllOrders();
        clearFields();
        generateNextOrderId();
        txtOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void comCustIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = comCustId.getValue();
        try {
            Customer customer = customerDAO.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtOrderId.getText();
        OrderDTO order = orderBO.searchByIdOrder(id);
        if (order != null) {
            txtOrderId.setText(order.getOId());
            txtOrderDate.setText(String.valueOf(order.getDate()));
            comCustId.setValue(order.getCusId());
        } else {
            new Alert(Alert.AlertType.INFORMATION,"Order not found!").show();
        }
    }

    @FXML
    void txtOrderIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID, txtOrderId);
    }
}
