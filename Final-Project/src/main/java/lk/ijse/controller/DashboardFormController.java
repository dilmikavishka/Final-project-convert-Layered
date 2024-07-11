package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.DashboardDAO;
import lk.ijse.dao.impl.DashboardDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.Material;
import lk.ijse.repository.MaterialRepo;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.PaymentRepo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane anpDash;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private Label lblCusCount;

    @FXML
    private Label lblEmpCount;

    @FXML
    private Label lblOrderCount;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private PieChart pieChart;

    private int customerCount;

    private int orderCount;

    private int employeeCount;

    @FXML
    private BarChart<?, ?> barChart;
    DashboardDAO dashboardDAO = new DashboardDAOImpl();
    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNote = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(rootNote);
        Stage stage = (Stage) this.anpDash.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login form");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int customerCount = 0;
        int orderCount = 0;
        int employeeCount = 0;

        try {
            iniPieChart();
            iniLineChart();
            iniBarChart();
            customerCount = dashboardDAO.getActiveCustomerCount();
            orderCount = dashboardDAO.getOrderCount();
            employeeCount = dashboardDAO.getEmployeeCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(customerCount);
        setOrderCount(orderCount);
        setEmployeeCount(employeeCount);

    }
    private void setOrderCount(int orderCount) {
        lblOrderCount.setText(String.valueOf(orderCount));
    }

    private void setEmployeeCount(int employeeCount) {
        lblEmpCount.setText(String.valueOf(employeeCount));
    }
    private void setCustomerCount(int customerCount) {lblCusCount.setText(String.valueOf(customerCount));}

    private void iniBarChart() {
        XYChart.Series series = new XYChart.Series();
        try {
            Map<String, Double> ordersByDay = dashboardDAO.getOrdersByDay();

            for (Map.Entry<String, Double> entry : ordersByDay.entrySet()) {
                series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
            }

            barChart.getData().add(series);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void iniLineChart() throws SQLException, ClassNotFoundException {
        XYChart.Series series = new XYChart.Series<>();
        Map<String, Double> paymentsByDay = dashboardDAO.getPaymentsByDay();

        for (Map.Entry<String, Double> entry : paymentsByDay.entrySet()) {
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }

        lineChart.getData().add(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }
    private void iniPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try {
            List<Material> Material = dashboardDAO.getAll();
            for (Material material : Material) {
                pieChartData.add(new PieChart.Data(material.getName(), material.getPrice()));
            }
            pieChart.setData(pieChartData);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
