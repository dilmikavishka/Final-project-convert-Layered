package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextFeild;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custome.*;
import lk.ijse.bo.impl.*;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custome.CustomerDAO;
import lk.ijse.dao.impl.CustomerDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.entity.*;
import lk.ijse.Tm.PlaceOrderTm;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController {

    @FXML
    private AnchorPane anpPlaceOrder;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnNewCustomer;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXComboBox<String> comCode;

    @FXML
    private JFXComboBox<String> comBatchId;

    @FXML
    private JFXComboBox<String> comCustomerName;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<PlaceOrderTm> tblPlaceOrder;

    @FXML
    private TextField txtQty;

    @FXML
    private JFXComboBox<String> comCustomerTel;
    private ObservableList<PlaceOrderTm> obList = FXCollections.observableArrayList();
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);
    BatchBO batchBO = (BatchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BATCH);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);

    @SneakyThrows
    public void initialize() {
        generateNextOrderId();
        getCustomerTel();
        getBatchIds();
        setDate();
        setCellValueFactory();
        comCustomerTel.setEditable(true);
        clearFields();



    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("baId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private void getBatchIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> batIdList = placeOrderBO.getBatchIds();

            for (String id : batIdList) {
                obList.add(id);
            }
            comCode.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getCustomerTel() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> telList = placeOrderBO.getCustomerTel();

            for (String tel : telList) {
                obList.add(tel);
            }

            comCustomerTel.setItems(obList);

        } catch(SQLException e){
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

   private void generateNextOrderId() throws SQLException, ClassNotFoundException {
       String id = orderBO.generateNextIdOrder();
       lblOrderId.setText(id);
   }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String batId = comCode.getValue();
        String description = lblDescription.getText();
        //int qty = Integer.parseInt(txtQty.getText());

        int qty = 0;
        if (!txtQty.getText().isEmpty()) {
            qty = Integer.parseInt(txtQty.getText());
        }

        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> types = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(types.orElse(no) == yes) {
                int selectedIndex = tblPlaceOrder.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblPlaceOrder.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0;i<tblPlaceOrder.getItems().size(); i++){
            if (batId.equals(colCode.getCellData(i))) {

                PlaceOrderTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblPlaceOrder.refresh();

                calculateNetTotal();
                return;
            }
        }

        PlaceOrderTm tm = new PlaceOrderTm(batId, description, qty, unitPrice, total, btnRemove);
        obList.add(tm);

        tblPlaceOrder.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");

    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));


    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/CustomerForm.fxml"));


        anpPlaceOrder.getChildren().clear();
        anpPlaceOrder.getChildren().add(customerPane);
    }

    @SneakyThrows
    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String cusId = lblCustomerId.getText();
        Date date = Date.valueOf(LocalDate.now());

        OrderDTO order = new OrderDTO(orderId,date,cusId);

        List<OredrDetailDTO> odList = new ArrayList<>();

        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            PlaceOrderTm tm = obList.get(i);

            OredrDetailDTO od = new OredrDetailDTO(
                    tm.getBaId(),
                    orderId,
                    tm.getQty()
            );

            odList.add(od);
        }

        PlaceOrderDTO po = new PlaceOrderDTO(order, odList);


        try {
            boolean isPlaced = placeOrderBO.placeOrder(order,odList);
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                obList.clear();
                tblPlaceOrder.setItems(obList);
                calculateNetTotal();
                clearFields();
                generateNextOrderId();
                generateBill(orderId);


            }else{
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clearFields() {
        lblCustomerId.setText("");
        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblQtyOnHand.setText("");
        txtQty.setText("");
        comCustomerTel.setValue("");
        lblNetTotal.setText("");


    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.QTY,txtQty)) return false;
        return true;
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);

    }

    @FXML
    void comBatchIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String batId = comCode.getValue();

        try {
            BatchDTO batch = batchBO.searchByIdBatch(batId);
            if(batch != null) {
                lblDescription.setText(batch.getDes());
                lblUnitPrice.setText(String.valueOf(batch.getPrice()));
                lblQtyOnHand.setText(String.valueOf(batch.getQtyOnHand()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void comCustomerTelOnAction(ActionEvent event) {
        String tel = comCustomerTel.getValue();
        try {
            Customer customer = customerDAO.searchByTel(tel);
            if (customer != null){
                lblCustomerId.setText(customer.getId());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateBill(String orderId) {
        try {
            String netTotal = calculateNetTotal(orderId);

            JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\ASUS\\IdeaProjects\\My-Final-Project-Convert-LayeredArchitecture\\Final-Project\\src\\main\\resources\\Report\\PlaceOrder.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("orderId", orderId);
            parameters.put("total", netTotal);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String calculateNetTotal(String orderId) throws SQLException, ClassNotFoundException {
        return placeOrderBO.calculateNetTotalOrder(orderId);
    }


    @FXML
    void filterCustomerCon(KeyEvent event) {
        ObservableList<String > filterCon = FXCollections.observableArrayList();
        String enteredText = comCustomerTel.getEditor().getText();

        try {
            List<String> conList = placeOrderBO.getCustomerTel();

            for (String con : conList){
                if (con.contains(enteredText)){
                    filterCon.add(con);
                }
            }
            comCustomerTel.setItems(filterCon);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void comCustomerTelOnMouseClicked(MouseEvent event) {
        comCustomerTel.getSelectionModel().clearSelection();
    }


    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.QTY,txtQty);
    }
}