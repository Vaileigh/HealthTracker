package HealthTracker;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DataDisplayController {
    @FXML
    private AnchorPane banner;
    @FXML
    private Text title;
    @FXML
    private Text page_name;
    @FXML
    private Text custom;
    @FXML
    private ComboBox<String> rng_cbx;
    @FXML
    private Text cal_txt;
    @FXML
    private ProgressBar pb;
    @FXML
    private Text cal_prog;
    @FXML
    private Text warning;
    @FXML
    private Button cal_btn;
    @FXML
    private Separator sep;
    @FXML
    private Text ex_txt;
    @FXML
    private Text noexdata;
    @FXML
    private Button swap_btn;
    @FXML
    private Button ex_btn;
    @FXML
    private Image ex_chart;
    @FXML
    private ImageView ex_chart_display;

    int ex_mode = 2;
    User me = new User();
    double calGoal = 100;

    public void initialize() {
        DummyData();
        createCalProg();
        createBar(rng_cbx.getValue());
    }

    public void timeChange() {
        String string = rng_cbx.getValue();
        cal_txt.setText("Your calorie summary for " + string);
        ex_txt.setText("Your exercise summary for " + string);
        createCalProg();
        if (ex_mode == 2) {
            createBar(rng_cbx.getValue());
        } else {
            createPie(rng_cbx.getValue());
        }
    }

    public void createCalProg() {
        HashMap<LocalDate, ArrayList<CalDat>> calCount = new HashMap<>();
        double calsTaken = 0;
        if (rng_cbx.getValue().equals("today"))
            calCount = me.getCalData(LocalDate.now(), LocalDate.now());
        if (rng_cbx.getValue().equals("this week"))
            calCount = me.getCalData(LocalDate.now().minusDays(7),LocalDate.now());

        for (ArrayList<CalDat> cals : calCount.values())
            for (CalDat cal : cals)
                calsTaken += cal.getCalories();

        double progress = calsTaken / calGoal;
        pb.setProgress(progress); //create progress bar with data

        cal_prog.setText((progress * 100) + "%");
        if (calsTaken / calGoal > 1.0) { //handle values over 100%
            double over = (progress - 1.0) * 100;
            warning.setText("You are over your goal by " + over + "%"); //displays a warning message
            warning.setFill(Color.rgb(236, 128, 47));
            pb.setStyle("-fx-accent: red");
        }
        else {
            warning.setText(null);
            pb.setStyle("-fx-accent: green");
        }
        pb.setProgress(progress);
    }

    public void redirectCal() {
        System.out.println("Redirect to calories page");
    }

    public void redirectEx() {
        System.out.println("Redirect to exercise page");
    }

    public void createPie(String timeframe) {
        final int WIDTH = 400;
        final int HEIGHT = 200;
        DefaultPieDataset dataset = new DefaultPieDataset();

        if (me.getExData().isEmpty()) {
            noexdata.setVisible(true);
            ex_chart_display.setImage(null);
            return;
        }
        if (timeframe.equals("this week")) {
            dataset = formPieData(LocalDate.now().minusDays(7), LocalDate.now());
        }
        if (timeframe.equals("today")) {
            dataset = formPieData(LocalDate.now(), LocalDate.now());
        }
        JFreeChart chart = ChartFactory.createPieChart(
                null,   // chart title
                dataset,          // data
                false,             // include legend
                true,
                false);

        ex_chart = SwingFXUtils.toFXImage(chart.createBufferedImage(WIDTH, HEIGHT), null);
        ex_chart_display.setImage(ex_chart);
        noexdata.setVisible(false);
    }

    //TODO bar chart needs goal data, not sure how its handled yet
    public void createBar(String timeframe) {
        final int WIDTH = 400;
        final int HEIGHT = 200;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (me.getExData().isEmpty()) {
            noexdata.setVisible(true);
            ex_chart_display.setImage(null);
            return;
        }
        if (timeframe.equals("this week")) {
            dataset = formBarData(LocalDate.now().minusDays(7), LocalDate.now());
        }
        if (timeframe.equals("today")) {
            dataset = formBarData(LocalDate.now(), LocalDate.now());
        }

        //produce the bar chart
        // chart title
        JFreeChart chart = ChartFactory.createBarChart( //produce the bar chart
                null,   // chart title
                "Day",
                "Duration (minutes)",
                dataset);

        //buffer image and create image view for display
        ex_chart = SwingFXUtils.toFXImage(chart.createBufferedImage(WIDTH, HEIGHT), null);
        ex_chart_display.setImage(ex_chart);
        noexdata.setVisible(false);
    }

    public DefaultPieDataset formPieData(LocalDate start, LocalDate end) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        HashMap<LocalDate, ArrayList<ExDat>> hash = me.getExData(start, end);
        for (LocalDate date : hash.keySet()) {
            for (ExDat dat : hash.get(date)) {
                try {
                    if (dataset.getKeys().contains(dat.getExerciseType())) {
                        dataset.setValue(dat.getExerciseType(), (Double) dataset.getValue(dat.getExerciseType()) +dat.getDuration());
                    } else {
                        dataset.setValue(dat.getExerciseType(), dat.getDuration());
                    }
                } catch (Exception e) {
                    System.out.println(e + ": No data for date (" + date + ")");
                }
            }
        }
        return dataset;
    }

    public DefaultCategoryDataset formBarData(LocalDate start, LocalDate end) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        HashMap<LocalDate, ArrayList<ExDat>> hash = me.getExData(start, end);
        for (LocalDate date : hash.keySet()) {
            for (ExDat dat : hash.get(date)) {
                try {
                    if (dataset.getColumnKeys().contains(date)) {
                        dataset.incrementValue(dat.getDuration(), "DURATION", date);
                    } else {
                        dataset.setValue(dat.getDuration(), "DURATION", date);
                    }
                } catch (Exception e) {
                    System.out.println(e + ": No data for date (" + date + ")");
                }
            }
        }
        return dataset;
    }

    public void swapCharts() {
        if (ex_mode == 1) {
            ex_mode = 2;
            swap_btn.setText("View Progress");
            createBar(rng_cbx.getValue());
        } else {
            ex_mode = 1;
            swap_btn.setText("View Distribution");
            createPie(rng_cbx.getValue());
        }
    }

    public void DummyData() {
        CalDat cal = new CalDat("Breakfast", 20);
        me.recordCal(cal);
        cal = new CalDat("Breakfast", 30);
        me.recordCal(cal);
        cal = new CalDat(LocalDate.now().minusDays(1), "Lunch", 40);
        me.recordCal(cal);
        cal = new CalDat(LocalDate.now().minusDays(2), "Lunch", 60);
        me.recordCal(cal);
        cal = new CalDat(LocalDate.now().minusDays(3), "Lunch", 20);
        me.recordCal(cal);

        ExDat ex = new ExDat("Cardio", 30);
        me.recordEx(ex);
        ex = new ExDat("Balance", 40);
        me.recordEx(ex);
        ex = new ExDat(LocalDate.now().minusDays(1), "Cardio", 20);
        me.recordEx(ex);
        ex = new ExDat(LocalDate.now().minusDays(2), "Cardio", 30);
        me.recordEx(ex);
        ex = new ExDat(LocalDate.now().minusDays(3), "Cardio", 5);
        me.recordEx(ex);
        ex = new ExDat(LocalDate.now().minusDays(4), "Cardio", 80);
        me.recordEx(ex);
        ex = new ExDat(LocalDate.now().minusDays(5), "Abs", 20);
        me.recordEx(ex);
        ex = new ExDat(LocalDate.now().minusDays(6), "Cardio", 60);
        me.recordEx(ex);
    }
}
