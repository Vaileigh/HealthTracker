package HealthTracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GoalController extends User{
    @FXML
    private ProgressBar pb1, pb2,pb3;
    @FXML
    private Text gl1,gl2,gl3;
    @FXML
    private Button btn_home, btn_user, btn_group, btn_settings, btn_workouts;
    @FXML
    public void initialize(){
        createWeightProgress();
        createStepsProgress();
        createCalProg();
    }
    @FXML
    public void createWeightProgress() {
        double weight = getStartWeight();
        pb1.setStyle("-fx-accent: green");
        if(weight/weightGoal>1.0){
            double over=weight-weightGoal;
            String m = "You are over your weight goal by: "+over+getPreferredWeightMetric().name();
            JOptionPane.showMessageDialog(new JFrame(), m, "Error",JOptionPane.ERROR_MESSAGE );
            pb1.setProgress(100.0);
            pb1.setStyle("-fx-accent: red");
            gl1.setText((weight-weightGoal)+" over "+weightGoal+getPreferredWeightMetric());

        }
        else{
            gl1.setText((weightGoal-weight)+" from "+weightGoal+getPreferredWeightMetric());
            pb1.setProgress((weight/weightGoal)*100);
        }

    }
    public void createStepsProgress()
    {
        HashMap<LocalDate, ArrayList<ExDat>> stepsCount = new HashMap<>();
        LocalDate.now().getDayOfWeek();

        stepsCount=getExData(LocalDate.now(),LocalDate.now().minusDays(7));
        int step=0;
        for(int i=0; i<stepsCount.size();i++){
            step+=stepsCount.get(i).get(i).steps;
        }
        double prog = (stepGoal/stepGoal)*100;
        if(step/stepGoal>1.0){
            double over=step-stepGoal;
            String m = "You are over your step goal by: "+over;
            JOptionPane.showMessageDialog(new JFrame(), m, "Well done",JOptionPane.ERROR_MESSAGE );
            pb2.setProgress(100.0);
            pb2.setStyle("-fx-accent: red");
            gl2.setText((step-stepGoal)+" over "+stepGoal+" steps");

        }
        else{
            pb2.setStyle("-fx-accent: green");

            gl2.setText((stepGoal-step)+" from "+stepGoal+" steps");
            pb2.setProgress((step/stepGoal)*100);
        }
        pb2.setProgress((step/stepGoal)*100);
    }

    public void createCalProg()
    {
        HashMap<LocalDate, ArrayList<CalDat>> calCount = new HashMap<>();
        double calsTaken = 0;


        for (ArrayList<CalDat> cals : calCount.values())
            for (CalDat cal : cals)
                calsTaken += cal.getCalories();

        double progress = calsTaken / calGoal;
        pb3.setProgress(progress); //create progress bar with data

        gl3.setText((progress * 100) + "%");
        if (calsTaken / calGoal > 1.0) { //handle values over 100%
            double over = (progress - 1.0) * 100;
            JOptionPane.showMessageDialog(new JFrame(), "You are over by "+over+" calories", "Well done",JOptionPane.ERROR_MESSAGE );
            pb3.setStyle("-fx-accent: red");
        }
        else {
            JOptionPane.showMessageDialog(new JFrame(), "You are "+calsTaken +" from "+calGoal +" calories", "Well done",JOptionPane.ERROR_MESSAGE );
            pb3.setStyle("-fx-accent: green");
        }
        pb3.setProgress(progress);
    }
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
}
