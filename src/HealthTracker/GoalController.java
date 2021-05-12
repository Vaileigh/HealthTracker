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
    public User user;
    @FXML
    public void initialize(){
        //normally this
        //user=Account.getLoggedIn();
        //dummy user
        user=new User();
        CalDat calDat = new CalDat("Breakfast", 4000);
        ExDat exDat = new ExDat(LocalDate.now(), "Cardio", 4000, 10);
        user.recordCal(calDat);
        user.recordEx(exDat);
        user.setStartWeight(54.0);
        user.setWeightGoal(90.0);
        user.setPreferredHeightMetric(heightMetric.CM);
        user.setPreferredWeightMetric(weightMetric.KG);
        user.setCalGoal(5000);
        //end of dummy data
        createWeightProgress();
        createStepsProgress();
        createCalProg();
    }
    @FXML
    public void createWeightProgress() {
        double weight = user.getStartWeight();
        pb1.setStyle("-fx-accent: green");
        if(weight/weightGoal>1.0){
            double over=weight-user.weightGoal;
            String m = "You are over your weight goal by: "+over+getPreferredWeightMetric().name();
            JOptionPane.showMessageDialog(new JFrame(), m, "Error",JOptionPane.ERROR_MESSAGE );
            pb1.setProgress(100.0);
            pb1.setStyle("-fx-accent: red");
            gl1.setText((weight-user.weightGoal)+" over "+user.weightGoal+user.getPreferredWeightMetric());

        }
        else{
            gl1.setText((user.weightGoal-weight)+" from "+user.weightGoal+" "+user.getPreferredWeightMetric());
            pb1.setProgress((weight/user.weightGoal));
        }

    }
    public void createStepsProgress()
    {
        HashMap<LocalDate, ArrayList<ExDat>> stepsCount = new HashMap<>();
        stepsCount=user.getExData(LocalDate.now().minusDays(7),LocalDate.now());
        int step=0;
        for(ArrayList<ExDat> c: stepsCount.values()){
            for(ExDat e:c){
                step+=e.steps;
            }
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
        //between now and 7 days before
        calCount=user.getCalData(LocalDate.now().minusDays(7), LocalDate.now());
        for (ArrayList<CalDat> cals : calCount.values())
            for (CalDat cal : cals)
                calsTaken += cal.getCalories();

        double progress = calsTaken / calGoal;
        pb3.setProgress(progress); //create progress bar with data

        gl3.setText((progress * 100) + "% from you goal of "+user.getCalGoal()+" calories");
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
