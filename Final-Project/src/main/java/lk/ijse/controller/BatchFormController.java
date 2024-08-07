package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextFeild;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custome.*;
import lk.ijse.bo.impl.*;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custome.*;
import lk.ijse.dao.impl.*;
import lk.ijse.dto.*;
import lk.ijse.Tm.*;
import lk.ijse.entity.*;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BatchFormController {

    @FXML
    private AnchorPane anpBatchManage;

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
    private ChoiceBox<String> choiceDescription;

    @FXML
    private TableColumn<?, ?> colAction1;

    @FXML
    private TableColumn<?, ?> colAction2;

    @FXML
    private TableColumn<?, ?> colBatchId;

    @FXML
    private TableColumn<?, ?> colBatchQty;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colHandOverQty;

    @FXML
    private TableColumn<?, ?> colMachineId;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private JFXComboBox<String> comEmployeeId;

    @FXML
    private JFXComboBox<String> comOrderId;

    @FXML
    private TableView<BatchTm> tblBatchManage;

    @FXML
    private TableView<MachineTm> tblMachineDetail;

    @FXML
    private TableView<MatirialTm> tblMaterialDetail;

    @FXML
    private TextField txtBatchColor;

    @FXML
    private TextField txtBatchId;

    @FXML
    private TextField txtBatchQty;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtHandOverQty;

    @FXML
    private TableColumn<?, ?> txtMaterialAction1;

    @FXML
    private TableColumn<?, ?> txtMaterialAction2;

    @FXML
    private TextField txtPrice;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colMatAction;
    @FXML
    private TableColumn<?, ?> colColor;
    BatchBO batchBO = (BatchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BATCH);
    BatchMachineBO batchMachineBO = (BatchMachineBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BATCHMACHINE);
    BatchMaterialBO batchMaterialBO = (BatchMaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BATCHMATERIAL);
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.EMPLOYEE);
    MachineBO machineBO = (MachineBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MACHINE);
    MaterialBO materialBO = (MaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MATERIAL);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER);

    @SneakyThrows
    public void initialize() {
        loadAllBatch();
        getEmployeeId();
        getOrderId();
        setCellValueFactory();
        loadAllMachineDetail();
        setCellValueFactoryMachine();
        loadAllMaterialDetail();
        setCellValueFactoryMaterial();
        generateNextBatchId();


        ObservableList<String> descriptionType = FXCollections.observableArrayList("POLISH","UNPOLISH");
        choiceDescription.setItems(descriptionType);

        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setCellValueFactoryMaterial() {
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMatAction.setCellValueFactory(new PropertyValueFactory<>("btnSave"));

    }

    private void loadAllMaterialDetail() {
        ObservableList<MatirialTm> obList = FXCollections.observableArrayList();
        try {
            List<MaterialDTO> materialList = materialBO.getAllMaterial();
            for (MaterialDTO material : materialList) {
                final MaterialDTO material1 = material;

                JFXButton btn = new JFXButton("Add");
                btn.setCursor(Cursor.HAND);

                MatirialTm tm = new MatirialTm(material.getId(), material.getName(), material.getDate(), material.getMatQty(), material.getSupId(), material.getPrice(), btn);
                obList.add(tm);
                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Add?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = obList.indexOf(tm);

                        String id = material1.getId();
                        String BaId = txtBatchId.getText();

                       // BatchMaterial batchMaterial = new BatchMaterial(id,BaId);

                        try {
                           // boolean isSaved = BatchMaterialRepo.save(batchMaterial);
                            if (batchMaterialBO.saveBatchMaterial(new BatchMaterialDTO(id,BaId))){
                                new Alert(Alert.AlertType.CONFIRMATION,"material is saved").show();
                            }
                        } catch (SQLException ex) {
                            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        // Update the existing EmployeeTm object in the obList
                        tm.setBtnSave(new JFXButton("Added"));
                    }
                });
            }

            tblMaterialDetail.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactoryMachine() {
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("MaId"));
        colAction1.setCellValueFactory(new PropertyValueFactory<>("btnSave"));
    }

    private void loadAllMachineDetail() {
        ObservableList<MachineTm> obList = FXCollections.observableArrayList();
        try {
            List<MachineDTO> machineList = machineBO.getAllMachine();
            for (MachineDTO machine : machineList) {
                final MachineDTO machine1 = machine;

                JFXButton btn = new JFXButton("Add");
                btn.setCursor(Cursor.HAND);

                MachineTm tm = new MachineTm(machine.getMaId(), machine.getName(), machine.getDescription(), btn);
                obList.add(tm);
                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Add?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = obList.indexOf(tm);

                        String MaId = machine1.getMaId();
                        String BaId = txtBatchId.getText();
                        Date date = Date.valueOf(txtDate.getText());


                       // BatchMachine batchMachine = new BatchMachine(MaId,BaId,date);

                        try {
                           // boolean isSaved = BatchMachineRepo.save(batchMachine);
                            if (batchMachineBO.saveBatchMachine(new BatchMachineDTO(MaId,BaId,date))){
                                new Alert(Alert.AlertType.CONFIRMATION,"machine is saved").show();
                            }
                        } catch (SQLException ex) {
                            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        // Update the existing EmployeeTm object in the obList
                        tm.setBtnSave(new JFXButton("Added"));
                    }
                });
            }

            tblMachineDetail.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("BatchId"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("BatchColor"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Des"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));


    }

    private void getOrderId() {
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

    private void getEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> employeeList = employeeDAO.getEmployeeIds();
            for (String id : employeeList){
                obList.add(id);
            }
            comEmployeeId.setItems(obList);
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
        generateNextBatchId();
        txtDate.setText(String.valueOf(LocalDate.now()));

    }

    private void clearFields() {
        txtBatchId.setText("");
        txtBatchColor.setText("");
        choiceDescription.setValue(null);
        txtBatchQty.setText("");
        txtDate.setText("");
        comEmployeeId.setValue(null);
        comOrderId.setValue(null);
        txtPrice.setText("");



    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtBatchId.getText();

        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
        }
        try {
            if (batchBO.deleteBatch(id)){
                new Alert(Alert.AlertType.CONFIRMATION,"Batch is deleted").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadAllBatch();
        clearFields();
        generateNextBatchId();
        txtDate.setText(String.valueOf(LocalDate.now()));


    }

    @SneakyThrows
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String BatchId = txtBatchId.getText();
        String BatchColor = txtBatchColor.getText();
        String Des = choiceDescription.getValue();

        int qty = Integer.parseInt(txtBatchQty.getText());

        Date date = Date.valueOf(txtDate.getText());
        String EmployeeId = comEmployeeId.getValue();
        String OrderId = comOrderId.getValue();

        double Price = 0;
        if (!txtPrice.getText().isEmpty()) {
            Price = Double.parseDouble(txtPrice.getText());
        }

        //Batch batch = new Batch(BatchId,BatchColor,Des,qty,date,EmployeeId,OrderId,Price);

        try {

            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR,"check fiels", ButtonType.OK).show();
            }
            if (batchBO.saveBatch(new BatchDTO(BatchId,BatchColor,Des,qty,date,EmployeeId,OrderId,Price))){
                new Alert(Alert.AlertType.CONFIRMATION,"Batch is saved").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllBatch();
        clearFields();
        generateNextBatchId();
        txtDate.setText(String.valueOf(LocalDate.now()));


    }

    private void loadAllBatch() {
        ObservableList<BatchTm> obList = FXCollections.observableArrayList();
        try {
            List<BatchDTO> batchList = batchBO.getAllBatch();
            for( BatchDTO batch : batchList){
                BatchTm tm = new BatchTm(batch.getBatchId(), batch.getBatchColor(),batch.getDes(), batch.getQtyOnHand(), batch.getDate(), batch.getEmployeeId(), batch.getOrderId(), batch.getPrice());
                obList.add(tm);
            }

            tblBatchManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @SneakyThrows
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String BatchId = txtBatchId.getText();
        String BatchColor = txtBatchColor.getText();
        String Des = choiceDescription.getValue();

        int qty = 0;
        if (!txtBatchQty.getText().isEmpty()) {
            qty = Integer.parseInt(txtBatchQty.getText());
        }

        Date date = Date.valueOf(txtDate.getText());
        String EmployeeId = comEmployeeId.getValue();
        String OrderId = comOrderId.getValue();

        double Price = 0;
        if (!txtPrice.getText().isEmpty()) {
            Price = Double.parseDouble(txtPrice.getText());
        }

        //Batch batch = new Batch(BatchId,BatchColor,Des,qty,date,EmployeeId,OrderId,Price);

        try {
            if (!isValied()) {
                new Alert(Alert.AlertType.ERROR, "check fiels", ButtonType.OK).show();
            }
            if (batchBO.updateBatch(new BatchDTO(BatchId,BatchColor,Des,qty,date,EmployeeId,OrderId,Price))){
                new Alert(Alert.AlertType.CONFIRMATION,"Batch is Update").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllBatch();
        clearFields();
        generateNextBatchId();
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.ID,txtBatchId)) return false;
        if (!Regex.setTextColor(TextFeild.NAME,txtBatchColor)) return false;
        if (!Regex.setTextColor(TextFeild.QTY,txtBatchQty)) return false;
        if (!Regex.setTextColor(TextFeild.SALARY,txtPrice)) return false;
        return true;
    }

    @FXML
    void comEmployeeIdOnAction(ActionEvent event) {
        String id = comEmployeeId.getValue();

        try {
            employeeDAO.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void comOrderIdOnAction(ActionEvent event) {
        String id = comOrderId.getValue();

        try {
            orderDAO.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtBatchId.getText();

        try {
            BatchDTO batch = batchBO.searchByIdBatch(id);
            if (batch != null){
                txtBatchId.setText(batch.getBatchId());
                txtBatchColor.setText(batch.getBatchColor());
                choiceDescription.setValue(batch.getDes());
                txtBatchQty.setText(String.valueOf(batch.getQtyOnHand()));
                txtDate.setText(String.valueOf(batch.getDate()));
                comEmployeeId.setValue(batch.getEmployeeId());
                comOrderId.setValue(batch.getOrderId());
                txtPrice.setText(String.valueOf(batch.getPrice()));

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Batch is not found").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


   /* private void getCurrentBatchIds() {
        try {
            String currentId = BatchRepo.getCurrentId();

            String nextBatchId = generateNextBatchId(currentId);
            txtBatchId.setText(nextBatchId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    private void generateNextBatchId() throws SQLException, ClassNotFoundException {
      String id = batchBO.generateNextIdBatch();
      txtBatchId.setText(id);

    }


    public void txtBatchIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.ID,txtBatchId);
    }

    public void txtBatchColorOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.NAME,txtBatchColor);
    }

    public void txtBatchQtyOKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.QTY,txtBatchQty);
    }

    public void txtPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFeild.SALARY,txtPrice);
    }
}

