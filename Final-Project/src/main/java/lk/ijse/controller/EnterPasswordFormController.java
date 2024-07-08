package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dao.custome.SecurityDAO;
import lk.ijse.dao.impl.SecurityDAOImpl;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnterPasswordFormController {

    @FXML
    private AnchorPane anpPasswor;

    @FXML
    private TextField txtConformPassword;

    @FXML
    private PasswordField txtConformPasswordB;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private PasswordField txtNewPasswordA;
    private boolean isPasswordVisible = false;

    SecurityDAO securityDAO = new SecurityDAOImpl();
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane loginPane = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(loginPane);

        Stage stage = (Stage) anpPasswor.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String newPassword = txtNewPassword.getText();
        String conPassword = txtConformPassword.getText();

        if (newPassword.equalsIgnoreCase(conPassword)){
                if (securityDAO.updatePassword(newPassword)){
                    new Alert(Alert.AlertType.CONFIRMATION, "Password Updated!!").show();

                }
                txtConformPassword.setStyle("-fx-border-color: green;");
                txtNewPassword.setStyle("-fx-border-color: green;");
            } else {
            txtConformPassword.setStyle("-fx-border-color: #e74c3c;");
            txtNewPassword.setStyle("-fx-border-color: #e74c3c;");
        }
    }

    @FXML
    void hidePasswordOnMouseReleased(MouseEvent event) {
        txtNewPasswordA.setVisible(true);
        txtNewPassword.setVisible(false);
    }

    @FXML
    void seePasswordOnMousePressed(MouseEvent event) {
        txtNewPasswordA.setVisible(false);
        txtNewPassword.setText(txtNewPasswordA.getText());
        txtNewPassword.setVisible(true);
    }

    @FXML
    void txtConformPasswordBOnAction(ActionEvent event) {

    }

    @FXML
    void txtConformPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtNewPasswordAOnAction(ActionEvent event) {

    }

    @FXML
    void txtNewPasswordOnAction(ActionEvent event) {

    }

}
