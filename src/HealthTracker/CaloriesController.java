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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.ChoiceBox;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
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
    private Text p_name;

    @FXML
    private Label sub_text,heading;
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



        //COPY
        if(SettingsController.english_selected){
            loadLang("en");
        }else{
            loadLang("du");
        }

        //
        if(SettingsController.text_to_speech){
            new Thread(() -> new SpeachClass().runSpeach("Calorie Page speech")).start();
        }

        //
        changeSize();
    }

    private void loadLang(String lang){
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("HealthTracker.lang",locale);


        //SET ALL TEXT BELOW (ALL THE TEXT THAT CONTAIN IN HOME SCREEN)
        submit.setText(bundle.getString("submitBtn"));
        text_field.setPromptText(bundle.getString("hintCalories"));
        sub_text.setText(bundle.getString("sub_text"));
        heading.setText(bundle.getString("caloriesHead"));
        p_name.setText(bundle.getString("p_name"));

    }

    private void changeSize(){
        if (SettingsController.large_font_size) {
            submit.setFont(Font.font( 19));
            text_field.setFont(Font.font( 19));
            sub_text.setFont(Font.font( 19));
            heading.setFont(Font.font( 19));
            p_name.setFont(Font.font( 19));
            //ADD OTHER TEXTS HERE WHICH NEED TO CHANGE SIZE
        }else {
            submit.setFont(Font.font( 15));
            text_field.setFont(Font.font( 15));
            sub_text.setFont(Font.font( 15));
            heading.setFont(Font.font( 15));
            p_name.setFont(Font.font( 15));
            //HERE TOO WITH 15 size
        }
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