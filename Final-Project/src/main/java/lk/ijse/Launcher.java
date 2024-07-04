package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.sun.javafx.css.StyleClassSet.getStyleClass;

public class Launcher extends Application {
            public static void main(String[] args) {
                launch(args);
            }

    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader
                .load(this.getClass().getResource("/view/MainForm.fxml"))));
        stage.setTitle("Main Form");
        stage.centerOnScreen();
        stage.show();
    }
}
