package HealthTracker;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.lang.model.element.ElementVisitor;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class HomeController {
    @FXML
    private Text title;
    @FXML
    private AnchorPane banner;
    @FXML
    private Text page_name;
    @FXML
    private Text custom;
    @FXML
    private Text name;
    @FXML
    private Text about;
    @FXML
    private Button support;
    @FXML
    private Button terms;
    @FXML
    private Button closeApp;
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
    private Button goalbtn;
    @FXML
    private void initialize(){
        User user = new User("James Wright","pass");
        name.setText(user.getUsername());
        about.setTextAlignment(TextAlignment.CENTER);
        about.setText(user.getAbout());
    }
    @FXML
    private void redirect(ActionEvent event){
        if(event.getSource()==closeApp){
            System.out.println("Closing app");
            System.exit(0);
        }
        try{
            Parent newRoot=null;
            //icons
            if(event.getSource()==terms) {
                newRoot=FXMLLoader.load(getClass().getResource("terms.fxml"));
            }
            if(event.getSource()==support){
                newRoot=FXMLLoader.load(getClass().getResource("support.fxml"));
            }
            if(event.getSource()==goalbtn){
                newRoot=FXMLLoader.load(getClass().getResource("goals.fxml"));

            }
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

            Scene scene = new Scene(newRoot);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Could not redirect:" +e);
        }
    }
}
