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
import lk.ijse.bo.custome.EmployeeBO;
import lk.ijse.bo.impl.EmployeeBOImpl;
import lk.ijse.dao.custome.EmployeeDAO;
import lk.ijse.dao.impl.EmployeeDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.Tm.EmployeeTm;
import lk.ijse.entity.Employee;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeFormController {

    @FXML
    private AnchorPane anpEmplloyeeManage;

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
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableView<EmployeeTm> tblEmployeeManage;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtTel;

    @FXML
    private JFXButton btnEmpList;
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    @SneakyThrows
    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
        generateNextEmployeeId();

    }

    private void generateNextEmployeeId() throws SQLException, ClassNotFoundException {
        String id = employeeBO.generateNextIdEmployee();
        txtEmployeeId.setText(id);
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    @SneakyThrows
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextEmployeeId();
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtEmpName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
        txtSalary.setText("");
    }

    @SneakyThrows
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(employeeBO.deleteEmployee(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
        clearFields();
        generateNextEmployeeId();
    }

    @SneakyThrows
    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String id = txtEmployeeId.getText();
        String name = txtEmpName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        double salary = 0;
        if (!txtSalary.getText().isEmpty()) {
            salary = Double.parseDouble(txtSalary.getText());
        }

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(employeeBO.saveEmployee(new EmployeeDTO(id,name,address,tel,salary))) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
        clearFields();
        generateNextEmployeeId();


    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.NAME,txtEmpName)) return false;
        if (!Regex.setTextColor(TextFeild.ID,txtEmployeeId)) return false;
        if (!Regex.setTextColor(TextFeild.ADDRESS,txtAddress)) return false;
        if (!Regex.setTextColor(TextFeild.CONTACT,txtTel)) return false;
        return true;
    }

    private void loadAllEmployee() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDTO> employeeList = employeeBO.getAllEmployee();
            for (EmployeeDTO employee : employeeList){
                EmployeeTm tm = new EmployeeTm(employee.getId(), employee.getName(), employee.getAddress(), employee.getTel(), employee.getSalary());
                obList.add(tm);
            }
            tblEmployeeManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtEmpName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        double salary = 0;
        if (!txtSalary.getText().isEmpty()) {
            salary = Double.parseDouble(txtSalary.getText());
        }

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(employeeBO.updateEmployee(new EmployeeDTO(id,name,address,tel,salary))) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
        clearFields();
        generateNextEmployeeId();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();

        try {
            EmployeeDTO employee = employeeBO.searchByIdEmployee(id);
            if (employee != null){
                txtEmployeeId.setText(employee.getId());
                txtEmpName.setText(employee.getName());
                txtAddress.setText(employee.getAddress());
                txtTel.setText(employee.getTel());
                txtSalary.setText(String.valueOf(employee.getSalary()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"payment is not found").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

   /* private void getCurrentEmployeeIds() {
        try {
            String currentId = EmployeeRepo.getCurrentId();

            String nextEmpId = generateNextEmployeeId(currentId);
            txtEmployeeId.setText(nextEmpId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/



    @FXML
    void btnEmpListOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\ASUS\\IdeaProjects\\My-Final-Project-Convert-LayeredArchitecture\\Final-Project\\src\\main\\resources\\Report\\EmployeeList .jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("cusId",txtEmployeeId.getText());
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport,data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void txtEmployeeIdOnActionReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtEmployeeId);
    }

    public void txtEmpNameOnKeyReleased(KeyEvent keyEvent) {
      Regex.setTextColor(TextFeild.NAME,txtEmpName);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.ADDRESS,txtAddress);
    }

    public void txtTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.CONTACT,txtTel);
    }

    public void txtSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.SALARY,txtSalary);
    }
}
