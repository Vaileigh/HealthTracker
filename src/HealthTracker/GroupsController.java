package HealthTracker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
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
    private Text page_name;

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
    private ScrollPane groupDetails_scrollPane;

    @FXML
    private Text groupDetails_Name;

    @FXML
    private Text groupDetails_current;

    @FXML
    private Text groupDetails_max;

    @FXML
    private Text groupDetails_descriptions;

    @FXML
    private Text groupDetails_admins;

    @FXML
    private Text groupDetails_members;
    @FXML
    private Text groupDetails_status;
    @FXML
    private VBox groupDetails_requests;
    @FXML
    private Button btnJoin;

    private Button navButton;
    private Button pre_navButton;

    private VBox layout;
    private VBox pre_layout;
    public String username = "User5";

    private void loadLang(String lang){
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("HealthTracker.lang",locale);


        //SET ALL TEXT BELOW (ALL THE TEXT THAT CONTAIN IN HOME SCREEN)
        //page_name.setText(bundle.getString("homeTitle"));

    }

    private void changeSize(){
        if (SettingsController.large_font_size) {
            //name.setFont(Font.font( 19));
            //ADD OTHER TEXTS HERE WHICH NEED TO CHANGE SIZE
        }else {
            //name.setFont(Font.font( 15));
            //HERE TOO WITH 15 size
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //COPY
        if(SettingsController.english_selected){
            loadLang("en");
        }else{
            loadLang("du");
        }

        //
        if(SettingsController.text_to_speech){
            new Thread(() -> new SpeachClass().runSpeach("GROUPS Page speech")).start();
        }

        //
        changeSize();

        navButton = btn_home;
        pre_navButton = btn_home;
        btn_home.setStyle("-fx-background-color: orange;");
        page_name.setText(getNavName(navButton));

        layout = content_home;
        pre_layout = content_home;
        layout.setVisible(true);
        content_group.setVisible(false);
        content_setting.setVisible(false);
        groupDetails_scrollPane.setVisible(false);

        // group.addAll(getData());
        // System.out.println(group.get(0).getName());
        String allGroups = System.getProperty("user.dir") + "\\src\\GroupData\\groupData.csv";
        //String userGroups = System.getProperty("user.dir")+"\\src\\GroupData\\userGroupData.csv";
        String userGroups = System.getProperty("user.dir") + "\\src\\GroupData\\userGroups.csv";
        // read(csvFile);

        File userFile = new File(userGroups);
        String[] user_groups = null;
        String line = " ";

        try {
            FileReader userFileReader = new FileReader(userFile);
            BufferedReader userBufferedReader = new BufferedReader(userFileReader);
            while ((line = userBufferedReader.readLine()) != null) {
                user_groups = line.split(",");
                if ((user_groups[0]/*.strip()*/).equals(username)) {
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


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

                for (int i = 0; i < tempArr.length; i++) {
                    switch (i) {
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

                setAllGroup(groupName, groupImg, id, groupInfo, user_groups, groupMember, groupMax, groupStatus, userGroups, allGroups);


            }

            bufferedReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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
        if (user_groups != null) {
            for (int i = 1; i < user_groups.length; i++) {
//                System.out.println("hello : " + groupName + " " + userGroup);
                if ((groupName.strip()).equals(user_groups[i].strip())) {
                    con = true;
                    break;
                }
            }
        }

        if (con){
            myGroups_vbox.getChildren().add(hBox);
        }else{
            String labels = "Join";
            Button joinButton = new Button();
            if(seekGroupPending(hBox, username)){
                joinButton.setText("Pending");
            }else{
                joinButton.setText(labels);
            }
            if (groupMember == groupMax){
                joinButton.setDisable(true);
            }
            hBox.getChildren().add(joinButton);
            HBox.setMargin(joinButton, new Insets(20, 5, 5, 5));
            joinButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(groupStatus.equals("private")){
//                        System.out.println(groupMember);
                        if (joinButton.getText().equals(labels)){
                            joinButton.setText("Pending");
                            updateGroupPending(hBox, username, true);
                        }
                        else{
                            joinButton.setText(labels);
                            updateGroupPending(hBox, username, false);
                        }
                    }else{
                        updateUserGroups(userGroups, hBox, true);
                        System.out.println("User Group updated");
                        updateAllGroups(allGroups, hBox, true);
                        System.out.println("All Group updated");
                        try {
                            Thread.sleep(1000);
                            hBox.getChildren().remove(joinButton);
                            myGroups_vbox.getChildren().add(hBox);
                            no.setText(groupMember+1 +"/"+groupMax+" | "+groupStatus);
                            groupDetails_current.setText(String.valueOf(groupMember+1));
                            updateGroupMembers(hBox, username, true);
                            System.out.println("Group members updated");
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
                // System.out.println(hBox.getId());
                String groupID = hBox.getId();
                String groupAdmins = System.getProperty("user.dir")+"\\src\\GroupData\\groupAdmins.csv";

                File adminsFile = new File(groupAdmins);
                String[] group_admins = null;
                String line = " ";
                String admins = null;
                try{
                    FileReader adminsFileReader = new FileReader(adminsFile);
                    BufferedReader adminsBufferedReader = new BufferedReader(adminsFileReader);
                    while ((line = adminsBufferedReader.readLine()) != null){
                        group_admins = line.split(",");
                        if ((group_admins[0].strip()).equals(groupID)){
                            break;
                        }
                    }
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                }

                boolean groupName = true;
                for(String admin : group_admins){
                    if (groupName){
                        groupName = false;
                    }else{
                        if(admins == null){
                            admins = admin;
                        }else{
                            admins = admins+"\n"+admin;
                        }
                    }

                }

                String groupMem = System.getProperty("user.dir")+"\\src\\GroupData\\groupMembers.csv";

                File memFile = new File(groupMem);
                String[] group_members = null;
                line = " ";
                String members = null;
                try{
                    FileReader memFileReader = new FileReader(memFile);
                    BufferedReader memBufferedReader = new BufferedReader(memFileReader);
                    while ((line = memBufferedReader.readLine()) != null){
                        group_members = line.split(",");
                        if ((group_members[0].strip()).equals(groupID)){
                            break;
                        }
                    }
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                }

                groupName = true;
                boolean isMember = false;
                for(String member : group_members){
                    if (member.strip().equals(username))
                        isMember = true;
                    if (groupName){
                        groupName = false;
                    }else{
                        if (!(admins.contains(member))){
                            if(members == null){
                                members = member;
                            }else{
                                members = members+"\n"+member;
                            }
                        }

                    }

                }

                groupDetails_scrollPane.setVisible(true);
                groupDetails_Name.setText(hBox.getId());
                groupDetails_current.setText(String.valueOf(groupMember));
                groupDetails_max.setText(String.valueOf(groupMax));
                groupDetails_status.setText(groupStatus);
                if (isMember){
                    btnJoin.setText("Leave Group >");
                }else{
                    if(seekGroupPending(hBox, username)){
                        btnJoin.setText("Pending");
                    }else{
                        btnJoin.setText("Join");
                    }

                }
                btnJoin.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(btnJoin.getText().equals("Join")){
                            if (groupStatus.equals("public")){
                                updateGroupMembers(hBox, username, true);
                                System.out.println("Group Member updated");
                                updateUserGroups(userGroups, hBox, true);
                                System.out.println("User Group updated");
                                updateAllGroups(allGroups, hBox, true);
                                System.out.println("All Group updated");
                                no.setText(groupMember+1 +"/"+groupMax+" | "+groupStatus);
                                groupDetails_current.setText(String.valueOf(groupMember+1));
                                btnJoin.setText("Leave Group >");
                            }else{
                                updateGroupPending(hBox,username,true);
                                btnJoin.setText("Pending");
                            }

                        }else {
                            if (btnJoin.getText().equals("Pending")){
                                updateGroupPending(hBox,username,false);
                                btnJoin.setText("Join");
                            }else{
                                updateGroupMembers(hBox, username, false);
                                System.out.println("Group Member updated");
                                updateUserGroups(userGroups, hBox, false);
                                System.out.println("User Group updated");
                                updateAllGroups(allGroups, hBox, false);
                                System.out.println("All Group updated");
                                btnJoin.setText("Join");
                                no.setText(groupMember-1 +"/"+groupMax+" | "+groupStatus);
                                groupDetails_current.setText(String.valueOf(groupMember-1));
                                // joinButton.setVisible(false);
                                Button joinButton = new Button();
                                if(seekGroupPending(hBox, username)){
                                    joinButton.setText("Pending");
                                }else{
                                    joinButton.setText("Join");
                                }
                                if (groupMember == groupMax){
                                    joinButton.setDisable(true);
                                }
                                hBox.getChildren().add(joinButton);
                                HBox.setMargin(joinButton, new Insets(20, 5, 5, 5));
                                allGroups_vbox.getChildren().remove(hBox);
                                allGroups_vbox.getChildren().add(hBox);
                            }


                        }

                    }
                });

                groupDetails_descriptions.setText(groupInfo);
                groupDetails_admins.setText(admins);
                groupDetails_members.setText(members);
                if (groupStatus.equals("private")){
                    groupDetails_requests.setVisible(true);
                    String groupPen = System.getProperty("user.dir")+"\\src\\GroupData\\groupPending.csv";

                    File penFile = new File(groupPen);
                    String[] group_pending = null;
                    line = " ";
                    String pendings = null;
                    try{
                        FileReader penFileReader = new FileReader(penFile);
                        BufferedReader penBufferedReader = new BufferedReader(penFileReader);
                        while ((line = penBufferedReader.readLine()) != null){
                            group_pending = line.split(",");
                            if ((group_pending[0].strip()).equals(groupID)){
                                break;
                            }
                        }
                    } catch(IOException ioe) {
                        ioe.printStackTrace();
                    }

                    groupName = true;
                    groupDetails_requests.getChildren().clear();
                    if (group_pending.length > 1 && admins.contains(username)){
                        groupDetails_requests.getChildren().add(new Text("Requests"));
                        for(String pending : group_pending){
                            HBox hBox1 = new HBox();
                            if (groupName){
                                groupName = false;
                            }else{
                                Text text = new Text(pending);
                                Text text1 = new Text("\t");
                                Button btnAccept = new Button("Y");
                                Button btnReject = new Button("N");
                                hBox1.getChildren().add(text);
                                hBox1.getChildren().add(text1);
                                hBox1.getChildren().add(btnAccept);
                                hBox1.getChildren().add(btnReject);
                                groupDetails_requests.getChildren().add(hBox1);
                                btnAccept.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        String newMem = text.getText();
                                        groupDetails_requests.getChildren().remove(hBox1);
                                        updateGroupMembers(hBox, newMem, true);
                                        System.out.println("Group members updated");
                                        updateGroupPending(hBox, newMem, false);
                                        System.out.println("Group pending updated");
                                        updateAllGroups(allGroups, hBox, true);
                                        no.setText(groupMember+1 +"/"+groupMax+" | "+groupStatus);
                                        groupDetails_current.setText(String.valueOf(groupMember+1));
                                        System.out.println("All Group updated");
                                    }
                                });
                                btnReject.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        String newMem = text.getText();
                                        groupDetails_requests.getChildren().remove(hBox1);
                                        updateGroupPending(hBox, newMem, false);
                                    }
                                });
                            }

                        }
                    }
                }else{
                    groupDetails_requests.setVisible(false);
                }

            }
        });


    }

    private boolean seekGroupPending(HBox hBox, String username) {
        String groupID = hBox.getId();
        String groupPen = System.getProperty("user.dir")+"\\src\\GroupData\\groupPending.csv";
        File penFile = new File(groupPen);
        String[] group_pending = null;
        String[] rows, cols;
        String line = " ";
        String pendings = null;
        try{
            FileReader penFileReader = new FileReader(penFile);
            BufferedReader penBufferedReader = new BufferedReader(penFileReader);
            line = penBufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        rows = line.split("\n");
        StringBuilder content = null;
        for (int i = 0; i< rows.length; i++){
            cols = rows[i].split(",");
            if (cols[0].equals(groupID)){
               for (int j = 1; j < cols.length; j++){
                   if (cols[j].strip().equals(username)){
                       return true;
                   }
               }
            }
        }
        return false;
    }

    private void updateGroupMembers(HBox hBox, String newMem, boolean condition){
        String groupID = hBox.getId();
        String groupMem = System.getProperty("user.dir")+"\\src\\GroupData\\groupMembers.csv";
        File memFile = new File(groupMem);
        StringBuilder content = null;
        String curr = groupDetails_members.getText();
        String ad = groupDetails_admins.getText();
        if (condition){
            groupDetails_members.setText(curr+"\n"+newMem);
        }else{
            //String[] temp = curr.split("\n");
            String newCurr = null, newAd = null;
            for (String temp : curr.split("\n")){
                if (!temp.strip().equals(username)){
                    if (newCurr == null){
                        newCurr = temp;
                    }else{
                        newCurr = "\n"+newCurr;
                    }
                }
            }
            for (String a : ad.split("\n")){
                if (!a.strip().equals(username)){
                    if (newAd == null){
                        newAd = a;
                    }else{
                        newAd = "\n"+newAd;
                    }
                }
            }
            groupDetails_members.setText(newCurr);
            groupDetails_admins.setText(newAd);
        }

        String[] group_members = null;
        String[] rows, cols;
        String line = " ";
        String members = null;
        try{
            FileReader memFileReader = new FileReader(memFile);
            BufferedReader memBufferedReader = new BufferedReader(memFileReader);
            line = memBufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        rows = line.split("\n");
        for (int i = 0; i< rows.length; i++){
            cols = rows[i].split(",");
            if (cols[0].equals(groupID)){
                String[] temp = rows[i].split(",");
                if (condition){
                    if (content == null){
                        content = new StringBuilder();
                        for (int j = 0; j< temp.length; j++){
                            if (j == 0){
                                content.append(temp[j]);
                                content.append(","+newMem);
                            }else{
                                content.append(","+temp[j]);
                            }
                        }
                    }else{
                        for (int j = 0; j< temp.length; j++) {
                            if (j == 0) {
                                content.append(temp[j]);
                                content.append("," + newMem);
                            } else {
                                content.append("," + temp[j]);
                            }
                        }
                    }
                }else{
                    if (content == null){
                        content = new StringBuilder();
                    }
                    for (int j = 0; j< temp.length; j++) {
                        if (j == 0) {
                            content.append(temp[j]);
                        } else {
                            if (!temp[j].strip().equals(username)){
                                content.append("," + temp[j]);
                            }

                        }
                    }
                }

            }else{
                if (content == null){
                    content = new StringBuilder(rows[i]);
                }else{
                    content.append("\n").append(rows[i]);
                }
            }
        }

        String strContent = String.valueOf(content);
//        System.out.println(strContent);
        String updateMember = System.getProperty("user.dir")+"\\src\\GroupData\\groupMembers.csv";
        File file = new File(updateMember);
        try (FileOutputStream fos = new FileOutputStream(new File(updateMember))) {
            fos.write(strContent.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateGroupPending(HBox hBox, String username, boolean condition){
        String groupID = hBox.getId();
        String groupPen = System.getProperty("user.dir")+"\\src\\GroupData\\groupPending.csv";
//        System.out.println(condition);
        File penFile = new File(groupPen);
        String[] group_pending = null;
        String[] rows, cols;
        String line = " ";
        String pendings = null;
        try{
            FileReader penFileReader = new FileReader(penFile);
            BufferedReader penBufferedReader = new BufferedReader(penFileReader);
            line = penBufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        rows = line.split("\n");
        StringBuilder content = null;
        boolean found = false;
        for (int i = 0; i< rows.length; i++){
            cols = rows[i].split(",");
            if (cols[0].equals(groupID)){
                found = true;
                if (condition){
                    if (content == null){
                        String[] temp = rows[i].split(",");
                        content = new StringBuilder();
                        for (int j = 0; j< temp.length; j++){
                            if (j == 0){
                                content.append(temp[j]);
                                content.append(","+username);
                            }else{
                                content.append(","+temp[j]);
                            }
                        }
                    }else{
                        content.append("\n").append(rows[i]);
                    }
                }else {
                    found = true;
                    String[] temp = rows[i].split(",");
                    for (int j = 0; j < temp.length; j++){
                        if (!temp[j].strip().equals(username)){
//                            System.out.println(temp[j]);
                            if (content == null){
                                content = new StringBuilder(temp[j]);
                            }else{
                                if (j == 0){
                                    content.append(temp[j]);
                                }else
                                    content.append(","+ temp[j]);

                            }
                        }
                    }
                }

            }else{
                if (content == null){
                    content = new StringBuilder(rows[i]);
                }else{
                    content.append("\n").append(rows[i]);
                }
            }
        }
        if (!found){
            content.append("\n"+groupID+","+username);
        }

        String strContent = String.valueOf(content);
//        System.out.println(strContent);
        String updatePending = System.getProperty("user.dir")+"\\src\\GroupData\\groupPending.csv";
        File file = new File(updatePending);
        try (FileOutputStream fos = new FileOutputStream(new File(updatePending))) {
            fos.write(strContent.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateAllGroups(String allGroups, HBox hBox, boolean condition) {
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
                if (condition)
                    cols[3] = String.valueOf(Integer.parseInt(cols[3]) + 1);
                else
                    cols[3] = String.valueOf(Integer.parseInt(cols[3]) - 1);
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
        // System.out.println(content);

        File file = new File(allGroups);
        try (FileOutputStream fos = new FileOutputStream(new File(allGroups))) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUserGroups(String userGroups, HBox hBox, boolean condition) {
        File userFile = new File(userGroups);
        String groupID = hBox.getId();
        StringBuilder content = null;
        String[] group_users = null;
        String[] rows, cols;
        String line = " ";
        boolean found = false;
        try{
            FileReader userFileReader = new FileReader(userFile);
            BufferedReader userBufferedReader = new BufferedReader(userFileReader);
            line = userBufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        rows = line.split("\n");
        for (int i = 0; i< rows.length; i++){
            cols = rows[i].split(",");
            if (cols[0].equals(username)){
                found = true;
                String[] temp = rows[i].split(",");
                if (condition){
                    if (content == null){
                        content = new StringBuilder();
                        for (int j = 0; j< temp.length; j++){
                            if (j == 0){
                                content.append(temp[j]);
                                content.append(","+groupID);
                            }else{
                                content.append(","+temp[j]);
                            }
                        }
                    }else{
                        for (int j = 0; j< temp.length; j++) {
                            if (j == 0) {
                                content.append(temp[j]);
                                content.append("," + groupID);
                            } else {
                                content.append("," + temp[j]);
                            }
                        }
                    }
                }else{
                    if (content == null){
                        content = new StringBuilder();
                    }
                    for (int j = 0; j< temp.length; j++) {
                        if (j == 0) {
                            content.append(temp[j]);
                        } else {
                            if (!temp[j].strip().equals(groupID)){
                                content.append("," + temp[j]);
                            }

                        }
                    }
                }
            }else{
                if (content == null){
                    content = new StringBuilder(rows[i]);
                }else{
                    content.append("\n").append(rows[i]);
                }
            }
        }
        if (!found){
            content.append("\n"+username+","+groupID);
        }
        String strContent = String.valueOf(content);
//        System.out.println(strContent);
        String updateMember = System.getProperty("user.dir")+"\\src\\GroupData\\userGroups.csv";
        File file = new File(updateMember);
        try (FileOutputStream fos = new FileOutputStream(new File(updateMember))) {
            fos.write(strContent.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public void back(){
        groupDetails_scrollPane.setVisible(false);
    }



    @FXML
    public void navigation(ActionEvent mouseEvent) {
        try {
            Parent newRoot=null;

            if (mouseEvent.getSource() == btn_user) {
                btn_user.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                page_name.setText(getNavName(btn_user));
                pre_navButton = btn_user;
                newRoot = FXMLLoader.load(getClass().getResource("data_display.fxml"));
            } else if (mouseEvent.getSource() == btn_home) {
                btn_home.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                page_name.setText(getNavName(btn_home));
                pre_layout.setVisible(false);
                content_home.setVisible(true);
                pre_layout = content_home;
                pre_navButton = btn_home;
                newRoot = FXMLLoader.load(getClass().getResource("home.fxml"));

            } else if (mouseEvent.getSource() == btn_settings) {
                btn_settings.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                page_name.setText(getNavName(btn_settings));
                pre_layout.setVisible(false);
                //pre_layout = content_setting;
                pre_navButton = btn_settings;
                newRoot = FXMLLoader.load(getClass().getResource("settings.fxml"));

            } else if (mouseEvent.getSource() == btn_workouts) {
                btn_workouts.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                page_name.setText(getNavName(btn_workouts));
                pre_navButton = btn_workouts;
                newRoot = FXMLLoader.load(getClass().getResource("exercise.fxml"));

            } else if (mouseEvent.getSource() == btn_group) {
                btn_group.setStyle("-fx-background-color: orange;");
                pre_navButton.setStyle("-fx-background-color: white;");
                page_name.setText(getNavName(btn_group));
                pre_layout.setVisible(false);
                content_group.setVisible(true);
                pre_layout = content_group;
                pre_navButton = btn_group;
                newRoot = FXMLLoader.load(getClass().getResource("groups.fxml"));

            }
            Scene scene = new Scene(newRoot);
            Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
