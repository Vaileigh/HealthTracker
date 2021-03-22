package HealthTracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class GroupsItemController {
    @FXML
    private ImageView group_img;

    @FXML
    private Label group_name;

    @FXML
    private Text group_description;

    GroupsModel groupsModel;

    public void setData(GroupsModel groupsModel) {
        this.groupsModel = groupsModel;
        group_name.setText(groupsModel.getName());
        group_description.setText(groupsModel.getDescription());
        Image image = new Image(getClass().getResourceAsStream(groupsModel.getImg()));
        group_img.setImage(image);
    }
}
