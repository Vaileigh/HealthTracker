package HealthTracker;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.io. * ;
import java.util.stream.Collectors;

import java.io.FileWriter;

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
    private VBox content_vbox;
    @FXML
    private VBox myGroups_vbox;
    @FXML
    private VBox allGroups_vbox;

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

//    private List<GroupsModel> group = new ArrayList<>();
//    private GroupsModel groups_model;
//
//    private List<GroupsModel> getData(){
//        List<GroupsModel> group = new ArrayList<>();
//        GroupsModel groups_model;
////        try {
////          File file = new File(System.getProperty("user.dir")+"\\src\\GroupData\\groupData.csv");
////          FileReader fr = new FileReader(file);
////          BufferedReader br = new BufferedReader(fr);
////          String line = " ";
////          String[] tempArr;
////          while ((line = br.readLine()) != null) {
////            tempArr = line.split(",");
////            System.out.println(tempArr[0]);
////            System.out.println(tempArr[1]);
////            System.out.println(tempArr[2]);
////            groups_model = new GroupsModel();
////            groups_model.setName(tempArr[0]);
////            groups_model.setDescription(tempArr[1]);
////            groups_model.setImg(tempArr[2]);
////
////            group.add(groups_model);
////
////          }
////          br.close();
////        }
////        catch(IOException ioe) {
////          ioe.printStackTrace();
////        }
//
//        for (int i = 0; i<20; i++){
//            groups_model = new GroupsModel();
//            groups_model.setName("Group #");
//            groups_model.setDescription("Help you reach your fitness goals with expertly designed workouts from our world-class Trainers");
//            groups_model.setImg("../Img/donkey.jpg");
//
//            group.add(groups_model);
//        }
//
//        return group;
//    }

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

        // group.addAll(getData());
        // System.out.println(group.get(0).getName());
        String allGroups = System.getProperty("user.dir")+"\\src\\GroupData\\groupData.csv";
        String userGroups = System.getProperty("user.dir")+"\\src\\GroupData\\userGroupData.csv";
        // read(csvFile);

        File userFile = new File(userGroups);
        String[] user_groups;
        String line = " ";
        try {
            FileReader userFileReader = new FileReader(userFile);
            BufferedReader userBufferedReader = new BufferedReader(userFileReader);
            line = userBufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        user_groups = line.split("\n");





        try {
            File file = new File(allGroups);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = " ";
            String[] tempArr;
            int id = 0;
            String groupName, groupInfo, groupImg, groupStatus;
            int groupMember = 0, groupMax = 0;
            groupName = "null";
            groupInfo = "null";
            groupImg = "src/Img/profile.png";
            groupStatus = "src/Img/profile.png";


            while ((line = bufferedReader.readLine()) != null) {
                tempArr = line.split(",");

                for(int i = 0; i < tempArr.length; i++){
                    switch (i){
                        case 0:
                            groupName = tempArr[i];
                            break;
                        case 1:
                            groupInfo = tempArr[i];
                            break;
                        case 2:
                            groupImg = tempArr[i];
                            break;
                        case 3:
                            groupMember = Integer.parseInt(tempArr[i]);
                            break;
                        case 4:
                            groupMax = Integer.parseInt(tempArr[i]);
                            break;
                        default:
                            groupStatus = tempArr[i];
                            break;
                    }
                }
                id++;

//                System.out.println("He: "+groupName+" "+groupInfo+" "+groupImg);
                // for (int i = 0; i<10; i++)
                setAllGroup(groupName, groupImg, id, groupInfo, user_groups, groupMember, groupMax, groupStatus, userGroups, allGroups);


            }

            bufferedReader.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

        // Create a HBox
//        for (int i=0; i<10; i++){
//            HBox hBox = new HBox();
//            hBox.setId("hBox1");
//            ImageView imageView = new ImageView();
//            imageView.setFitHeight(60);
//            imageView.setFitWidth(60);
//            File file = new File("src/Img/profile.png");
//            Image image = new Image(file.toURI().toString());
//            imageView.setImage(image);
//            HBox.setMargin(imageView, new Insets(5, 5, 5, 5));
//            imageView.setId("hBox1_imageView");
//            hBox.getChildren().add(imageView);
//            VBox vBox = new VBox();
//            vBox.setId("hBox1_vBox");
//            Text name = new Text("Group 2");
//            name.setStyle("-fx-font-size: 14;");
//            vBox.getChildren().add(name);
//            Text detail = new Text("Happy Meal Happy Meal Happy Meal Happy Meal Happy Meal ");
//            detail.setWrappingWidth(317);
//            vBox.getChildren().add(detail);
//            hBox.getChildren().add(vBox);
//            content_vbox.getChildren().add(hBox);
//        }





        ///String currentDirectory = System.getProperty("user.dir");
        //System.out.println("The current working directory is " + currentDirectory);

//        int column = 0;
//        int row = 0;
//        try{
//            for (int i = 0; i < group.size(); i++){
//                FXMLLoader fxmlLoader = new FXMLLoader();
//
//                fxmlLoader.setLocation(getClass().getResource("../HealthTracker/groups_item.fxml"));
//
//
//                AnchorPane anchorPane = fxmlLoader.load();
//
//                group_grid.add(anchorPane, column, row++);
//                GridPane.setMargin(anchorPane, new Insets(10));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    private void setAllGroup(String groupName, String groupImg, int id, String groupInfo, String[] user_groups, int groupMember, int groupMax, String groupStatus, String userGroups, String allGroups){
        HBox hBox = new HBox();
        hBox.setId(groupName);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        File imageFile = new File(groupImg);
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
        HBox.setMargin(imageView, new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-border-color: orange;");
        hBox.setStyle("-fx-background-color: white;");
        imageView.setId(id+"_imageView");
        hBox.getChildren().add(imageView);
        VBox vBox = new VBox();
        vBox.setId(id+"_vBox");
        Text name = new Text(groupName);
        name.setStyle("-fx-font-size: 14;");
        vBox.getChildren().add(name);
        Text detail = new Text(groupInfo);
        detail.setWrappingWidth(270);
        vBox.getChildren().add(detail);
        Text no = new Text(groupMember+"/"+groupMax+" | "+groupStatus);
        //Text status = new Text(groupStatus);
        vBox.getChildren().add(no);
        //vBox.getChildren().add(status);
        hBox.getChildren().add(vBox);


        Boolean con = false;
        if (user_groups.length > 0) {
            for (String userGroup : user_groups) {
//                System.out.println("hello : " + groupName + " " + userGroup);
                if ((groupName.strip()).equals(userGroup.strip())) {
                    con = true;
                    break;
                }
            }
        }

        if (con){
            myGroups_vbox.getChildren().add(hBox);
        }else{
            String labels = "Join";
            Button joinButton = new Button(labels);
            if (groupMember == groupMax){
                joinButton.setDisable(true);
            }
            hBox.getChildren().add(joinButton);
            HBox.setMargin(joinButton, new Insets(20, 5, 5, 5));
            joinButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(groupStatus.equals("private")){
                        System.out.println(groupMember);
                        if (joinButton.getText().equals("Join"))
                            joinButton.setText("Pending");
                        else
                            joinButton.setText("Join");
                    }else{

                        updateUserGroups(userGroups, hBox);
                        updateAllGroups(allGroups, hBox);
                        try {
                            Thread.sleep(1000);
                            hBox.getChildren().remove(joinButton);
                            myGroups_vbox.getChildren().add(hBox);
                            no.setText(groupMember+1 +"/"+groupMax+" | "+groupStatus);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            allGroups_vbox.getChildren().add(hBox);
        }

        hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(hBox.getId());
            }
        });


    }

    private void updateAllGroups(String allGroups, HBox hBox) {
        // Clone csv
        String[] rows, cols;
        String line = " ";
        try {
            FileReader userFileReader = new FileReader(allGroups);
            BufferedReader userBufferedReader = new BufferedReader(userFileReader);
            line = userBufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        rows = line.split("\n");
        String content = null;
        for (String row: rows){
//            System.out.println(row);
            cols = row.split(",");
            if (cols[0].equals(hBox.getId())){
                cols[3] = String.valueOf(Integer.parseInt(cols[3]) + 1);
                for (int j = 0; j < cols.length; j++){
                    if (content == null){
                        content = cols[j];
                    }else{
                        if (j == 0){
                            content = content + cols[j];
                        }else{
                            content = content + "," + cols[j];
                        }

                    }
                }
            }else{
                if (content == null){
                    content = row;
                }else{
                    content = content + "\n" + row;
                }
            }


        }
        System.out.println(content);

        File file = new File(allGroups);
        try (FileOutputStream fos = new FileOutputStream(new File(allGroups))) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUserGroups(String userGroups, HBox hBox) {
        File file = new File(userGroups);
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            String content = "\n"+hBox.getId();
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void read(String csvFile) {
//        try {
//          File file = new File(csvFile);
//          FileReader fr = new FileReader(file);
//          BufferedReader br = new BufferedReader(fr);
//          String line = " ";
//          String[] tempArr;
//          // String[][] groups = new String[tempArr.length][];
//          int i = 0;
//          while ((line = br.readLine()) != null) {
//            tempArr = line.split(",");
//            // groups[i][0] = tempArr[0];
//            for (String tempStr: tempArr) {
//
//                System.out.print(tempStr + " ");
//            }
//            System.out.println();
//          }
//          br.close();
//        }
//        catch(IOException ioe) {
//          ioe.printStackTrace();
//        }
//      }

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
