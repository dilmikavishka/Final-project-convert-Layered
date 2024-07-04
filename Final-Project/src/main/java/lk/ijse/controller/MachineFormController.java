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
import lk.ijse.bo.custome.MachineBO;
import lk.ijse.bo.impl.MachineBOImpl;
import lk.ijse.dao.custome.MachineDAO;
import lk.ijse.dao.impl.MachineDAOImpl;
import lk.ijse.dto.MachineDTO;
import lk.ijse.Tm.MachineTm;
import lk.ijse.entity.Machine;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.List;

public class MachineFormController {

    @FXML
    private AnchorPane apnMachineManage;

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
    private TableColumn<?, ?> colMachineId;

    @FXML
    private TableColumn<?, ?> colMachineName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<MachineTm> tblMachineManage;

    @FXML
    private TextField txtMachineId;

    @FXML
    private TextField txtMachineName;

    @FXML
    private TextField txtStatus;
//    MachineDAO machineDAO = new MachineDAOImpl();
    MachineBO  machineBO = new MachineBOImpl();

    @SneakyThrows
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextMachineId();
    }


    private void clearFields() {
        txtMachineId.setText("");
        txtMachineName.setText("");
        txtStatus.setText("");
    }

    @SneakyThrows
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String MaId = txtMachineId.getText();

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(machineBO.deleteMachine(MaId)) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMachine();
        clearFields();
        generateNextMachineId();
    }

    @SneakyThrows
    public void initialize() {
       setCellValueFactory();
       loadAllMachine();
       generateNextMachineId();
    }

    private void generateNextMachineId() throws SQLException, ClassNotFoundException {
        String id = machineBO.generateNextIdMachine();
        txtMachineId.setText(id);
    }

    private void setCellValueFactory() {
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("MaId"));
        colMachineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @SneakyThrows
    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String MaId = txtMachineId.getText();
        String name = txtMachineName.getText();
        String description = txtStatus.getText();

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(machineBO.saveMachine(new MachineDTO(MaId,name,description))) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadAllMachine();
        clearFields();
        generateNextMachineId();
    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.ID,txtMachineId)) return false;
        if (!Regex.setTextColor(TextFeild.NAME,txtMachineName)) return false;
        if (!Regex.setTextColor(TextFeild.NAME,txtStatus)) return false;
        return true;
    }

    private void loadAllMachine() {
        ObservableList<MachineTm> obList = FXCollections.observableArrayList();
        try {
            List<MachineDTO> machineList = machineBO.getAllMachine();
            for(MachineDTO machine : machineList){
                MachineTm tm = new MachineTm(machine.getMaId(), machine.getName(), machine.getDescription(), new JFXButton());
                obList.add(tm);
            }
            tblMachineManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String MaId = txtMachineId.getText();
        String name = txtMachineName.getText();
        String description = txtStatus.getText();

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"please check the fields",ButtonType.OK).show();
            }
            if(machineBO.updateMachine(new MachineDTO(MaId,name,description))) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMachine();
        clearFields();
        generateNextMachineId();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String MaId = txtMachineId.getText();

        MachineDTO machine = machineBO.searchByIdMachine(MaId);

        if (machine != null) {
            txtMachineId.setText(machine.getMaId());
            txtMachineName.setText(machine.getName());
            txtStatus.setText(machine.getDescription());

        }else {
            new Alert(Alert.AlertType.INFORMATION,"Machine is not found !").show();
        }
    }

   /* private void getCurrentMachineIds() {
        try {
            String currentId = MachineRepo.getCurrentId();

            String nextMachineId = generateNextMachineId(currentId);
            txtMachineId.setText(nextMachineId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/


    @FXML
    void txtMachineIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtMachineId);
    }

    @FXML
    void txtStatusOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.NAME,txtStatus);
    }

    public void txtMachineNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.NAME,txtMachineName);
    }
}
