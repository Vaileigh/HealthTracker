package HealthTracker;

<<<<<<< Updated upstream
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {
    @FXML
    private Button speech;
    @FXML
    private Button font;
    @FXML
    private Button submit;
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
    private void redirect(ActionEvent event){
        Parent newRoot = null;
        try{
            if(event.getSource()==submit){
                newRoot= FXMLLoader.load(getClass().getResource("data_display.fxml"));
            }
            if (event.getSource() == btn_home) {
                newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));

            }
            if(event.getSource()==btn_user){
                newRoot = FXMLLoader.load(getClass().getResource("data_display.fxml"));
            }
            if (event.getSource() == btn_settings) {
                newRoot = FXMLLoader.load(getClass().getResource("settings.fxml"));
=======

import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class SettingsController implements Initializable {

    @FXML
    private Pane pane1;
    @FXML
    private Text tit1;
    @FXML
    private Text tit2;

    @FXML
    private Text opt1;
    @FXML
    private Text opt2;
    @FXML
    private Text opt3;
    @FXML
    private Text opt4;
    @FXML
    private Text opt5;


    @FXML
    private Button cal_btn;
    @FXML
    private Button ex_btn;
    @FXML
    private ComboBox<String> cbx1;
    @FXML
    private ComboBox<String> cbx2;
    @FXML
    private JFXToggleButton tgl1;
    @FXML
    private JFXToggleButton tgl2;
    @FXML
    private JFXToggleButton tgl3;
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

    private Locale locale;
    private ResourceBundle bundle;

    private static String unit = "km";
    private static boolean large_font_size = false;
    private static boolean text_to_speech = false;
    private static boolean goal_option = true;


    @FXML
    private void redirect(ActionEvent event) {


        if (event.getSource() == btn_user) {
            System.out.println("Already on page.");
            return;
        }
        try {
            Parent newRoot = null;
            if (event.getSource() == cal_btn) {
                newRoot = FXMLLoader.load(getClass().getResource("calories.fxml"));
            }
            if (event.getSource() == ex_btn) {
                newRoot = FXMLLoader.load(getClass().getResource("exercise.fxml"));
>>>>>>> Stashed changes
            }
            if (event.getSource() == btn_group) {
                newRoot = FXMLLoader.load(getClass().getResource("groups.fxml"));
            }
            if (event.getSource() == btn_workouts) {
<<<<<<< Updated upstream
                newRoot = FXMLLoader.load(getClass().getResource("groups.fxml"));
            }
=======
                newRoot = FXMLLoader.load(getClass().getResource("exercise.fxml"));
            }
            if (event.getSource() == btn_home) {
                newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));
            }
            if (event.getSource() == btn_settings) {
                newRoot = FXMLLoader.load(getClass().getResource("settings.fxml"));
            }


>>>>>>> Stashed changes
            Scene scene = new Scene(newRoot);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
<<<<<<< Updated upstream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
=======
        }
        catch (Exception e) {
            System.out.println("Could not redirect: " + e);
        }
    }

    private void changeSize(){
        if (large_font_size) {


            opt1.setFont(Font.font( 19));
            opt2.setFont(Font.font( 19));
            opt3.setFont(Font.font( 19));
            opt4.setFont(Font.font(19));
            opt5.setFont(Font.font(19));
        }else {
            opt1.setFont(Font.font( 15));
            opt2.setFont(Font.font( 15));
            opt3.setFont(Font.font( 15));
            opt4.setFont(Font.font( 15));
            opt5.setFont(Font.font( 15));

        }
    }


    //TRANSLATIONS METHOD //do it like this in other pages..
    //lang_du.properties has german
    //lang_en.prop has english
    private void loadLang(String lang){
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("HealthTracker.lang",locale);


        //SET ALL TEXT BELOW
        tit1.setText(bundle.getString("settingsTitle1"));
        tit2.setText(bundle.getString("settingsTitle2"));
        opt1.setText(bundle.getString("opt1"));
        opt2.setText(bundle.getString("opt2"));
        opt3.setText(bundle.getString("opt3"));
        opt4.setText(bundle.getString("opt4"));
        opt5.setText(bundle.getString("opt5"));
    }

    @FXML
    public void tglAct1(ActionEvent event){
        if(text_to_speech){
            text_to_speech = false;
        }else {
            text_to_speech = true;

            // runnable for that thread
            new Thread(() -> runSpeach()).start();
        }
    }

    private void runSpeach(){
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
            synthesizer.speak("Track Settings Panel. Text to speech. Larger font. Language. DISABLE goal option. preferred units.", null);
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);

            // Deallocate the Synthesizer.
            synthesizer.deallocate();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void tglAct2(ActionEvent event){
        if(large_font_size){
            large_font_size = false;
        }else {
            large_font_size = true;
        }
        changeSize();
    }
    @FXML
    public void tglAct3(ActionEvent event){
        if(goal_option){
            goal_option = false;
        }else {
            goal_option = true;
        }
    }


    @Override
    public void initialize(URL url,ResourceBundle rb){
        ObservableList<String> hobbies = FXCollections.observableArrayList("English","German");
        ObservableList<String> hobbies2 = FXCollections.observableArrayList("Kilometers","Miles");

        cbx1.getItems().setAll(hobbies);
        cbx1.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            if(newVal.equals("English")){
                loadLang("en");
            }else {
                loadLang("du");

            }
        });

        cbx2.getItems().setAll(hobbies2);
        cbx2.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            if(newVal.equals("Kilometers")){
                unit = "km";
            }else {
                unit = "ml";
            }
        });
    }
>>>>>>> Stashed changes
}
