package HealthTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Settings extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root2 = FXMLLoader.load(getClass().getResource("settings.fxml"));
        primaryStage.setTitle("Settings");
        Scene scene = new Scene(root2, 400, 700);
        //scene.getStylesheets().add(getClass().getResource("global.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}