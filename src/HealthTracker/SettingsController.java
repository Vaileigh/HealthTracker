package HealthTracker;


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



    public static String unit = "km";
    public static boolean large_font_size = false;
    public static boolean text_to_speech = false;
    public static boolean goal_option = true;
    public static boolean english_selected = true;


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
            }
            if (event.getSource() == btn_group) {
                newRoot = FXMLLoader.load(getClass().getResource("groups.fxml"));
            }
            if (event.getSource() == btn_workouts) {
                newRoot = FXMLLoader.load(getClass().getResource("exercise.fxml"));
            }
            if (event.getSource() == btn_home) {
                newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));
            }
            if (event.getSource() == btn_settings) {
                newRoot = FXMLLoader.load(getClass().getResource("settings.fxml"));
            }


            Scene scene = new Scene(newRoot);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
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
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("HealthTracker.lang",locale);


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

            //for speech call like this in all controler

            if(SettingsController.text_to_speech){
                new Thread(() -> new SpeachClass().runSpeach("Track Settings Panel. Text to speech. Larger font. Language. DISABLE goal option. preferred units.")).start();
            }
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
                english_selected = true;
            }else {
                loadLang("du");
                english_selected = false;
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

        //SETTING VALUES FOR PANEL
        cbx2.getSelectionModel().select(SettingsController.unit.equals("km")? "Kilometers" : "Miles");
        cbx1.getSelectionModel().select(SettingsController.english_selected? "English" : "German");
        tgl1.setSelected(SettingsController.text_to_speech ? true : false);
        tgl2.setSelected(SettingsController.large_font_size ? true : false);
        tgl3.setSelected(SettingsController.goal_option ? true : false);
        //END


        //COPY
        if(SettingsController.english_selected){
            loadLang("en");
        }else{
            loadLang("du");
        }

        //
        if(SettingsController.text_to_speech){
            new Thread(() -> new SpeachClass().runSpeach("Track Settings Panel. Text to speech. Larger font. Language. DISABLE goal option. preferred units.")).start();
        }

        //
        changeSize();
    }
}
