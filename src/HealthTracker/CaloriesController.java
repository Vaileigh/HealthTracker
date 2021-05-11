package HealthTracker;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.ChoiceBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CaloriesController implements Initializable {

    @FXML
    private AnchorPane a_pane;

    @FXML
    private AnchorPane banner;

    @FXML
    private Text track;

    @FXML
    private Text p_name;

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
    private Pane content;

    @FXML
    private Line line;

    @FXML
    private Button submit;

    @FXML
    private TextField text_field;

    @FXML
    private Label heading;

    @FXML
    private Label sub_text;

    @FXML
    private ImageView img;

    @FXML
    private ChoiceBox meals;

    private Button navButton;
    private Button pre_navButton;
    String Meals[] = {"Breakfast","Lunch","Dinner"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text_field.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        navButton = btn_home;
        pre_navButton = btn_home;
        btn_home.setStyle("-fx-background-color: orange;");
        p_name.setText(getNavName(navButton));
        ObservableList<String> a = FXCollections.observableArrayList(Meals);
        meals.setItems(a);
    }

    public void getCalories(){
        int calories = 0;
        try {
            calories = Integer.parseInt(text_field.getText());
        } catch (NumberFormatException e){
            System.out.println("Expected a number as measurement for calories!");
        }
    }

    public void getMealType(){
        meals.getValue();
    }


    public void submit() {

    }

    private String getNavName(Button navButton) {
        if (navButton == btn_settings) {
            return "SETTINGS";
        }
        if (navButton == btn_user) {
            return "USER";
        }
        if (navButton == btn_home) {
            return "HOME";
        }
        if (navButton == btn_workouts) {
            return "WORKOUTS";
        }
        if (navButton == btn_group) {
            return "FRIENDS";
        } else {
            return "<404 ERROR>";
        }
    }

    @FXML
    public void navigation(ActionEvent event) {
        try {
            Parent newRoot = null;
            if (event.getSource() == btn_user) {
                btn_user.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                p_name.setText(getNavName(btn_user));
                pre_navButton = btn_user;
                newRoot = FXMLLoader.load(getClass().getResource("data_display.fxml"));
            } else if (event.getSource() == btn_home) {
                btn_home.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                p_name.setText(getNavName(btn_home));
                pre_navButton = btn_home;
                newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));
            } else if (event.getSource() == btn_settings) {
                btn_settings.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                p_name.setText(getNavName(btn_settings));
                pre_navButton = btn_settings;
                newRoot = FXMLLoader.load(getClass().getResource("settings.fxml"));
            } else if (event.getSource() == btn_workouts) {
                btn_workouts.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                p_name.setText(getNavName(btn_workouts));
                pre_navButton = btn_workouts;
                newRoot = FXMLLoader.load(getClass().getResource("groups.fxml"));

            } else if (event.getSource() == btn_group) {
                btn_group.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                p_name.setText(getNavName(btn_group));
                pre_navButton = btn_group;
                newRoot = FXMLLoader.load(getClass().getResource("groups.fxml"));

            }
            Scene scene = new Scene(newRoot);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();

        } catch (IOException e) {
            System.out.println("Could not redirect:" + e);
        }


    }
}
