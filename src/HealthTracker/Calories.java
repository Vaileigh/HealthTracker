package HealthTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Calories extends Application {

    /*
    INITIALISE THE SCENE VIA FXML/CONTROLLER AND RUN
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("calories.fxml"));
        stage.setTitle("Calories");
        stage.setScene(new Scene(root,400,700));
        stage.getScene().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
