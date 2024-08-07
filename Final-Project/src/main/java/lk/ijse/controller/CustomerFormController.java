package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.bo.impl.CustomerBOImpl;
import lk.ijse.dao.custome.CustomerDAO;
import lk.ijse.dao.impl.CustomerDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.Tm.CustomerTm;
import lk.ijse.entity.Customer;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomerFormController {

    @FXML
    private AnchorPane anpCustomerManage;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colCustomerAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colCustomerTel;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private Label lblCustomer;


    @FXML
    private JFXButton btnCusList;
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @SneakyThrows
    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
        generateNextCustomerId();

    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDTO> customerList = customerBO.getAllCustomer();
            for(CustomerDTO customer : customerList){
                CustomerTm tm = new CustomerTm(customer.getId(), customer.getName(), customer.getTel(), customer.getAddress());
                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    @SneakyThrows
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextCustomerId();
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerTel.setText("");
        txtCustomerAddress.setText("");
    }

    @SneakyThrows
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(customerBO.deleteCustomer(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllCustomers();
        clearFields();
        generateNextCustomerId();
    }

    @SneakyThrows
    @FXML
    void btnSaveOnAction(ActionEvent event)  {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();


        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields").show();
                return;
            }
            if (customerBO.saveCustomer(new CustomerDTO(id,name,tel,address))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                clearFields();
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadAllCustomers();
        clearFields();
        generateNextCustomerId();
    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.NAME,txtCustomerName)) return false;
        if (!Regex.setTextColor(TextFeild.ID,txtCustomerId)) return false;
        if (!Regex.setTextColor(TextFeild.ADDRESS,txtCustomerAddress)) return false;
        if (!Regex.setTextColor(TextFeild.CONTACT,txtCustomerTel)) return false;
        return true;
    }

    @SneakyThrows
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();

        try {

            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"check fiels",ButtonType.OK).show();
            }
            if (customerBO.updateCustomer(new CustomerDTO(id,name,tel,address))){
                new Alert(Alert.AlertType.CONFIRMATION,"customer is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllCustomers();
        clearFields();
        generateNextCustomerId();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException{
        String id = txtCustomerId.getText();

        CustomerDTO customer = null;
        try {
            customer = customerBO.searchByIdCustomer(id);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (customer != null) {
            txtCustomerId.setText(customer.getId());
            txtCustomerName.setText(customer.getName());
            txtCustomerTel.setText(customer.getTel());
            txtCustomerAddress.setText(customer.getAddress());

        }else {
            new Alert(Alert.AlertType.INFORMATION,"customer is not found !").show();
        }
    }


  /*  private void getCurrentCusIds() {
        try {
            String currentId = CustomerRepo.getCurrentId();

            String nextCusId = generateNexrCusId(currentId);
            txtCustomerId.setText(nextCusId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    private void generateNextCustomerId() throws SQLException, ClassNotFoundException {
        String id = customerBO.generateNextIdCustomer();
        txtCustomerId.setText(id);

    }

   /* private String generateNexrCusId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("C");  //" ", "2"6
            int idNum = Integer.parseInt(split[1]);
            return "C" + String.format("%03d", ++idNum);
        }
        return"C001";
    }*/


    @FXML
    void btnCusListOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\ASUS\\IdeaProjects\\My-Final-Project-Convert-LayeredArchitecture\\Final-Project\\src\\main\\resources\\Report\\CustomerList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("cusId",txtCustomerId.getText());
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport,data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }


    @FXML
    void txtCustomerIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtCustomerId);
    }

    public void txtCustomerTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.CONTACT,txtCustomerTel);
    }

    public void txtCustomerAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.ADDRESS,txtCustomerAddress);
    }

    public void txtCustomerNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.NAME,txtCustomerName);
    }
}
