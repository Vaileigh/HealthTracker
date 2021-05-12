package HealthTracker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import HealthTracker.Account.*;
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
    @FXML
    private ComboBox rng_cbx;
    private double d;
    private int s;
    private boolean iskm, selectkm,selectmiles;
    public User user;
    @FXML
    public void initialize(){
        user=Account.getLoggedIn();
        selectkm=false;
        selectmiles=false;
    }
    @FXML
    public void km(){
        kmB.setStyle("-fx-background-color: orange;");
        iskm=true;
        if(selectkm)         {
            kmB.setStyle("-fx-background-color: grey;");
            selectkm=false;
            return;
        }

        selectkm=true;
    }
    public void miles(){
        milesB.setStyle("-fx-background-color: orange;");
        iskm=false;
        if(selectmiles)   {
            milesB.setStyle("-fx-background-color: grey;");
            selectmiles=false;
            return;
        }

        selectmiles=true;
    }
    @FXML
    public void cals(ActionEvent event){
        if(rng_cbx.getValue().equals("Calories")){
            try{
                Parent newRoot = null;
                newRoot=FXMLLoader.load(getClass().getResource("calories.fxml"));
                Scene scene = new Scene(newRoot);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void redirect(ActionEvent event){
        try{
            Parent newRoot=null;
            //nav bar
            if (event.getSource() == btn_home) {
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
                System.out.println("Already on page");
                newRoot = FXMLLoader.load(getClass().getResource("exercise.fxml"));
            }
                newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));

            Scene scene = new Scene(newRoot);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
                } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        catch (NumberFormatException n){
            n.printStackTrace();
        }


    }
    @FXML
    public void handleSubmit(ActionEvent event){
        if(kmB.getStyle().equals("-fx-background-color: orange;")&&milesB.getStyle().equals("-fx-background-color: orange;")){
            JOptionPane.showMessageDialog(new JFrame(), "Please select ONLY miles or kilometres for your measurement", "Error",JOptionPane.ERROR_MESSAGE );
        }
        if(kmB.getStyle().equals("-fx-background-color: white;")&&milesB.getStyle().equals("-fx-background-color: white;")){
            JOptionPane.showMessageDialog(new JFrame(), "Please select miles or kilometres for your measurement", "Error",JOptionPane.ERROR_MESSAGE );
        }
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene scene=new Scene(new Group(),400,700);
                final Exercise ex;
                String measurement=null;
                String tempD = null, tempS = null;
                tempD=distance.getText();
                tempS=steps.getText();
                if(tempS.isEmpty()||tempD.isEmpty()||tempD==null||tempS==null){
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid integer", "Dialog",JOptionPane.ERROR_MESSAGE );
                }
                else if(iskm!=true||iskm!=false){
                    JOptionPane.showMessageDialog(new JFrame(), "Please select miles or kilometres for your measurement", "Dialog",JOptionPane.ERROR_MESSAGE );

                }
                else{
                    if(iskm==true){
                        measurement="km";
                    }
                    else{
                        measurement="miles";
                    }


                    d=Double.parseDouble(tempD);
                    s=Integer.parseInt(tempS);
                    try {
                        Parent newRoot=null;

                        newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));
                        scene = new Scene(newRoot);
                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        appStage.setScene(scene);
                        appStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                LocalDate date = LocalDate.now();
                ExDat exDat = new ExDat(date,measurement,d,s);
                System.out.println(exDat.toString());
                user.recordEx(exDat);
                System.out.println("Exercise logged");
                try {
                    Parent newRoot=null;

                    newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));
                    scene = new Scene(newRoot);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.setScene(scene);
                    appStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
