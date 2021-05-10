package HealthTracker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.IOException;

public class ExerciseController {
    @FXML
    private Button milesB;
    @FXML
    private Button kmB;
    @FXML
    private Button btn_home;
    @FXML
    private Button btn_settings;
    @FXML
    private Button btn_group;
    @FXML
    private Button btn_user;
    @FXML
    private Button btn_workouts;
    @FXML
    private Button submit;
    @FXML
    private TextField steps;
    @FXML
    private TextField distance;
    private double d;
    private int s;
    private boolean iskm;
    public void km(){
        kmB.setStyle("-fx-background-color: orange;");
        iskm=true;
    }
    public void miles(){
        milesB.setStyle("-fx-background-color: orange;");
        iskm=false;
    }
    @FXML
    private void redirect(ActionEvent event){
        try{
            Parent newRoot=null;
            //nav bar
            if (event.getSource() == btn_home) {
                System.out.println("Already on page");
                newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));

            }
            if(event.getSource()==btn_user){
                newRoot = FXMLLoader.load(getClass().getResource("data_display.fxml"));
            }
            if (event.getSource() == btn_settings) {
                newRoot = FXMLLoader.load(getClass().getResource("settings.fxml"));
            }
            if (event.getSource() == btn_group) {
                newRoot = FXMLLoader.load(getClass().getResource("groups.fxml"));
            }
            if (event.getSource() == btn_workouts) {
                newRoot = FXMLLoader.load(getClass().getResource("exercise.fxml"));
            }
            if(event.getSource()==submit){
                final Exercise ex;
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String tempD=distance.getText();
                        String tempS=steps.getText();
                        d=Double.parseDouble(tempD);
                        s=Integer.parseInt(tempS);
                        Exercise ex = new Exercise(d, s, iskm);
                    }

                });
                System.out.println("Distance is: "+d+s);
                newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));

            }

            Scene scene = new Scene(newRoot);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Could not redirect:" +e);
        }
    }
}
