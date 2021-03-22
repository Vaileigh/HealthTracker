package HealthTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Groups extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("groups.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("Tracker");
//        primaryStage.setScene(scene);
//        // primaryStage.setScene(new Scene(root, 400, 700));
//        primaryStage.show();
        Parent root = FXMLLoader.load(getClass().getResource("groups.fxml"));
        primaryStage.setTitle("TRACK");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}