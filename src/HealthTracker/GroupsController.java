package HealthTracker;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GroupsController implements Initializable{
    @FXML
    private Button btn_settings;
    @FXML
    private Button btn_settings_close;
    @FXML
    private Button btn_user;
    @FXML
    private Button btn_home;
    @FXML
    private Button btn_workouts;
    @FXML
    private Button btn_group;

    @FXML
    private AnchorPane paneSlide;

    @FXML
    private Text page_name;

    @FXML
    private ScrollPane group_scroll;

    @FXML
    private GridPane group_grid;

    @FXML
    private VBox content_home;
    @FXML
    private VBox content_group;

    @FXML
    private VBox content_workout;
    @FXML
    private VBox content_user;

    private Button navButton;
    private Button pre_navButton;

    private VBox layout;
    private VBox pre_layout;

    private List<GroupsModel> group = new ArrayList<>();
    private GroupsModel groups_model;

    private List<GroupsModel> getData(){
        List<GroupsModel> group = new ArrayList<>();
        GroupsModel groups_model;

        for (int i = 0; i<20; i++){
            groups_model = new GroupsModel();
            groups_model.setName("Group #1");
            groups_model.setDescription("Helps you reach your fitness goals with expertly designed workouts from our world-class Trainers");
            groups_model.setImg("../Img/donkey.jpg");

            group.add(groups_model);
        }
        return group;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneSlide.setTranslateX(-200);
        btn_settings.setVisible(true);
        btn_settings_close.setVisible(false);

        navButton = btn_home;
        pre_navButton = btn_home;
        btn_home.setStyle("-fx-background-color: orange;");
        page_name.setText(getNavName(navButton));

        layout = content_home;
        pre_layout = content_home;
        layout.setVisible(true);
        content_group.setVisible(false);

        group.addAll(getData());

        int column = 0;
        int row = 0;
        try{
            for (int i = 0; i < group.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../HealthTracker/groups_item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();



                group_grid.add(anchorPane, column, row++);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    private String getNavName(Button navButton){
        if (navButton == btn_settings){
            return "SETTINGS";
        }
        if (navButton == btn_user){
            return "USER";
        }
        if (navButton == btn_home){
            return "HOME";
        }
        if (navButton == btn_workouts){
            return "WORKOUTS";
        }
        if (navButton == btn_group){
            return "FRIENDS";
        }
        else{
            return "<404 ERROR>";
        }
    }

    public void run1(javafx.scene.input.MouseEvent mouseEvent) {
        TranslateTransition slide= new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(paneSlide);

        slide.setToX(0);
        slide.play();

        paneSlide.setTranslateX(-200);



        slide.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                btn_settings.setVisible(false);
                btn_settings_close.setVisible(true);
                if(mouseEvent.getSource() == btn_settings) {
                    System.out.println("Side Panel");
                }
            }
        });
    }

    public void run2(javafx.scene.input.MouseEvent mouseEvent) {
        TranslateTransition slide= new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(paneSlide);

        slide.setToX(-200);
        slide.play();

        paneSlide.setTranslateX(0);

        slide.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                btn_settings.setVisible(true);
                btn_settings_close.setVisible(false);
            }
        });
    }


    public void navigation(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == btn_user) {
            btn_user.setStyle("-fx-background-color: orange;");
            pre_navButton.setStyle("-fx-background-color: white;");
            page_name.setText(getNavName(btn_user));
            pre_navButton = btn_user;
        }
        else if(mouseEvent.getSource() == btn_home) {
            btn_home.setStyle("-fx-background-color: orange;");
            pre_navButton.setStyle("-fx-background-color: white;");
            page_name.setText(getNavName(btn_home));
            pre_layout.setVisible(false);
            content_home.setVisible(true);
            pre_layout = content_home;
            pre_navButton = btn_home;
        }
        else if(mouseEvent.getSource() == btn_workouts) {
            btn_workouts.setStyle("-fx-background-color: orange;");
            pre_navButton.setStyle("-fx-background-color: white;");
            page_name.setText(getNavName(btn_workouts));
            pre_navButton = btn_workouts;
        }
        else if(mouseEvent.getSource() == btn_group) {
            btn_group.setStyle("-fx-background-color: orange;");
            pre_navButton.setStyle("-fx-background-color: white;");
            page_name.setText(getNavName(btn_group));
            pre_layout.setVisible(false);
            content_group.setVisible(true);
            pre_layout = content_group;
            pre_navButton = btn_group;
        }

    }
}
