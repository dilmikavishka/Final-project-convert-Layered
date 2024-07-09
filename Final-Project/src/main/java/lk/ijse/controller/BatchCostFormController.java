package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.bo.custome.BatchBO;
import lk.ijse.bo.custome.BatchCostBO;
import lk.ijse.bo.custome.MaterialBO;
import lk.ijse.bo.impl.BatchBOImpl;
import lk.ijse.bo.impl.MaterialBOImpl;
import lk.ijse.dao.custome.QueryDAO;
import lk.ijse.dao.impl.QueryDAOImpl;
import lk.ijse.bo.impl.BatchCostBOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BatchCostDTO;
import lk.ijse.dto.BatchDTO;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.MaterialDetailDTO;
import lk.ijse.Tm.BatchCostTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class BatchCostFormController {

    @FXML
    private AnchorPane anpBatchCost;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnPlaceCost;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colBatchId;

    @FXML
    private TableColumn<?, ?> colColor;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colMatId;

    @FXML
    private TableColumn<?, ?> colMatName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private ComboBox<String> comBatchId;

    @FXML
    private ComboBox<String> comMaterialId;

    @FXML
    private Label lblColor;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblMatName;

    @FXML
    private Label lblMatQtyOnHand;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<BatchCostTm> tblBatchCost;

    @FXML
    private TextField txtQty;
    private ObservableList<BatchCostTm> obList = FXCollections.observableArrayList();

    MaterialBO materialBO = (MaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MATERIAL);
    BatchBO batchBO = (BatchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BATCH);
    BatchCostBO batchCostBO = (BatchCostBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BATCHCOST);

    public void initialize() {
        setDate();
        getBatchIds();
        getMaterialId();
        setCellValueFactory();
        clearField();
    }

    private void clearField() {
        comBatchId.getValue();
        comMaterialId.getValue();
        lblColor.setText("");
        lblDescription.setText("");
        lblTotal.setText("");
        lblPrice.setText("");
        lblMatQtyOnHand.setText("");
        lblMatName.setText("");
        txtQty.setText("");

    }

    private void setCellValueFactory() {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batId"));
        colMatId.setCellValueFactory(new PropertyValueFactory<>("matId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

    }

    private void getMaterialId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> matIdList = batchCostBO.getMaterialIds();

            for (String id : matIdList) {
                obList.add(id);
            }
            comMaterialId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getBatchIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> batIdList = batchCostBO.getBatchIds();

            for (String id : batIdList) {
                obList.add(id);
            }
            comBatchId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void comBatchIdOnAction(ActionEvent event) {
        String batId = comBatchId.getValue();

        try {
            BatchDTO batch = batchBO.searchByIdBatch(batId);
            if(batch != null) {
                lblColor.setText(batch.getBatchColor());
                lblDescription.setText(batch.getDes());
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    String batId = "";

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String batId = comBatchId.getValue();
        String matId = comMaterialId.getValue();
       // int qty = Integer.parseInt(txtQty.getText());
        int qty = 0;
        if (!txtQty.getText().isEmpty()) {
            qty = Integer.parseInt(txtQty.getText());
        }
        double price = Double.parseDouble(lblPrice.getText());
        double total = qty * price;

       /* if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }*/

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> types = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (types.orElse(no) == yes) {
                int selectedIndex = tblBatchCost.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblBatchCost.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblBatchCost.getItems().size(); i++) {
            if (matId.equals(colMatId.getCellData(i))) {

                BatchCostTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * price;

                tm.setQty(qty);
                tm.setTotal(total);

                tblBatchCost.refresh();

                calculateNetTotal();
                return;
            }
        }

        BatchCostTm tm = new BatchCostTm(batId,matId,qty,price,total, btnRemove);
        obList.add(tm);

        tblBatchCost.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.QTY,txtQty)) return false;
        return true;
    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblBatchCost.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceCostOnAction(ActionEvent event) {
        String materialId = comMaterialId.getValue();
        String matName = lblMatName.getText();
        double price = Double.parseDouble(lblPrice.getText());
        int qtyOnHand = Integer.parseInt(lblMatQtyOnHand.getText());

        List<MaterialDetailDTO> bcList = new ArrayList<>();

        for (int i = 0; i < tblBatchCost.getItems().size(); i++) {
            BatchCostTm tm = obList.get(i);

            MaterialDetailDTO md = new MaterialDetailDTO(
                    tm.getBatId(),
                    tm.getMatId(),
                    tm.getQty()
            );

            bcList.add(md);
        }

        BatchCostDTO bc = new BatchCostDTO(bcList);

        try {
            boolean isPlaced = batchCostBO.placeCost(bc);
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                obList.clear();
                tblBatchCost.setItems(obList);
                calculateNetTotal();
                generateBill(batId);


            }else{
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private String calculateNetTotal(String batId) throws SQLException, ClassNotFoundException {
        return batchCostBO.calculateNetTotalBatch(batId);

    }

    @FXML
    void comMaterialIdOnAction(ActionEvent event) {
        String matId = comMaterialId.getValue();

        try {
            MaterialDTO material = materialBO.searchByIdMaterial(matId);
            if(material != null) {
                lblMatName.setText(material.getName());
                lblPrice.setText(String.valueOf(material.getPrice()));
                lblMatQtyOnHand.setText(String.valueOf(material.getMatQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void generateBill(String batId) {
        try {
            String netTotal = calculateNetTotal(batId);

            JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\ASUS\\IdeaProjects\\My-Final-Project-Convert-LayeredArchitecture\\Final-Project\\src\\main\\resources\\Report\\BatchCost.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("batId", batId);
            parameters.put("total", netTotal);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }


    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.QTY,txtQty);
    }

}
