package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextFeild;
import lk.ijse.dao.custome.RegisterDAO;
import lk.ijse.dao.impl.RegisterDAOImpl;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private ImageView anp1;

    @FXML
    private AnchorPane anp2;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private Hyperlink linkLog;
    RegisterDAO registerDAO = new RegisterDAOImpl();

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();

        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        try {
            boolean isSaved = registerDAO.saveUser(userId, name, password);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean isValied() {
        if (!Regex.setTextColor(TextFeild.ID,txtId)) return false;
        if (!Regex.setTextColor(TextFeild.NAME,txtName)) return false;
        if (!Regex.setTextColor(TextFeild.QTY,txtPassword)) return false;
        return true;
    }

    @FXML
    void linkLogOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();

    }

    @FXML
    void txtIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.ID,txtId);
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.NAME,txtName);
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFeild.QTY,txtPassword);
    }
}
