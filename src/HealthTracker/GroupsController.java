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
import java.util.Scanner;
import java.io. * ;

public class GroupsController implements Initializable{
    @FXML
    private Button btn_settings;

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
    private VBox content_setting;

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
//        try {
//          File file = new File(System.getProperty("user.dir")+"\\src\\GroupData\\groupData.csv");
//          FileReader fr = new FileReader(file);
//          BufferedReader br = new BufferedReader(fr);
//          String line = " ";
//          String[] tempArr;
//          while ((line = br.readLine()) != null) {
//            tempArr = line.split(",");
//            System.out.println(tempArr[0]);
//            System.out.println(tempArr[1]);
//            System.out.println(tempArr[2]);
//            groups_model = new GroupsModel();
//            groups_model.setName(tempArr[0]);
//            groups_model.setDescription(tempArr[1]);
//            groups_model.setImg(tempArr[2]);
//
//            group.add(groups_model);
//
//          }
//          br.close();
//        }
//        catch(IOException ioe) {
//          ioe.printStackTrace();
//        }

        for (int i = 0; i<20; i++){
            groups_model = new GroupsModel();
            groups_model.setName("Group #");
            groups_model.setDescription("Help you reach your fitness goals with expertly designed workouts from our world-class Trainers");
            groups_model.setImg("../Img/donkey.jpg");

            group.add(groups_model);
        }

        return group;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        navButton = btn_home;
        pre_navButton = btn_home;
        btn_home.setStyle("-fx-background-color: orange;");
        page_name.setText(getNavName(navButton));

        layout = content_home;
        pre_layout = content_home;
        layout.setVisible(true);
        content_group.setVisible(false);
        content_setting.setVisible(false);

        group.addAll(getData());
        // System.out.println(group.get(0).getName());
        //String csvFile = System.getProperty("user.dir")+"\\src\\GroupData\\groupData.csv";
        //read(csvFile);


        ///String currentDirectory = System.getProperty("user.dir");
        //System.out.println("The current working directory is " + currentDirectory);

        int column = 0;
        int row = 0;
        try{
            for (int i = 0; i < group.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("../HealthTracker/groups_item.fxml"));
                groupsItemController.setData(group.get(i));
                fxmlLoader.setController(groupsItemController);

                AnchorPane anchorPane = fxmlLoader.load();

                group_grid.add(anchorPane, column, row++);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void read(String csvFile) {
        try {
          File file = new File(csvFile);
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);
          String line = " ";
          String[] tempArr;
          while ((line = br.readLine()) != null) {
            tempArr = line.split(",");
            for (String tempStr: tempArr) {
              System.out.print(tempStr + " ");
            }
            System.out.println();
          }
          br.close();
        }
        catch(IOException ioe) {
          ioe.printStackTrace();
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
        else if(mouseEvent.getSource() == btn_settings) {
            btn_settings.setStyle("-fx-background-color: orange;");
            pre_navButton.setStyle("-fx-background-color: white;");
            page_name.setText(getNavName(btn_settings));
            pre_layout.setVisible(false);
            //pre_layout = content_setting;
            pre_navButton = btn_settings;
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
