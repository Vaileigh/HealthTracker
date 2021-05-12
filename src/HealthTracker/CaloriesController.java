package HealthTracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.ChoiceBox;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CaloriesController implements Initializable {

    @FXML
    private Button btn_group;

    @FXML
    private Button btn_workouts;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_user;

    @FXML
    private Button btn_settings;

    @FXML
    private Button submit;

    @FXML
    private TextField text_field;

    @FXML
    private ChoiceBox meals;

    LocalDate date = LocalDate.now();
    String Meals[] = {"Breakfast","Lunch","Dinner","Other"};

    //TEMPORARY USER FOR THE PURPOSE OF THE DEMO
    User user = new User();

    /*
    INITIALISE THE TEXT FIELD TO BE NUMBER FORMAT AND COMBO BOX TO CONTAIN MEAL TYPE
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text_field.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        ObservableList<String> a = FXCollections.observableArrayList(Meals);
        meals.setItems(a);
        meals.setValue(a.get(0));
    }

    /*
    NAVIGATION OPTIONS FOR THE NAV BAR SEEN BELOW
     */
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

    /*
    ACCESSOR FOR RETRIEVING THE CALORIES ENTERED
     */
    public int getCalories(){
        int calories = 0;
        try {
            calories = Integer.parseInt(text_field.getText());
            return calories;
        } catch (NumberFormatException e){
            System.out.println("Expected a number as measurement for calories!");
        }
        return calories;
    }

    /*
    ACCESSOR FOR RETRIEVING THE MEAL TYPE SELECTED
     */
    public String getMealType(){
        return (String) meals.getValue();
    }

    /*
    HANDLE SUBMIT METHOD, CHECKS INFORMATION IS VALID ON SUBMIT BEING CLICKED
     */
    public void handleSubmit(){
        if (text_field.getText().isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a number in the text field", "Dialog",JOptionPane.ERROR_MESSAGE );
        }
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String tempMealType = getMealType();
                int tempCal = getCalories();
                if (tempCal == 0){
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a kcal count above 0", "Dialog",JOptionPane.ERROR_MESSAGE );
                } else if(tempMealType.isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(), "Please select a meal type", "Dialog",JOptionPane.ERROR_MESSAGE );
                } else {
                    CalDat calorieRecord = new CalDat(date, tempMealType, tempCal);
                    System.out.println(calorieRecord.toString());
                    System.out.println("Data Recorded Successfully");
                    user.recordCal(calorieRecord);
                    System.out.println(user.getCalData());
                }
            }
        });
    }

}