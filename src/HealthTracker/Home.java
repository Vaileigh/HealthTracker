package HealthTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Home extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        primaryStage.setTitle("Health Tracker");
        primaryStage.setScene(new Scene(root, 400, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}