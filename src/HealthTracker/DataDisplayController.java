package HealthTracker;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

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
    @FXML
    private Button prev_btn;
    @FXML
    private Button next_btn;
    @FXML
    private Button reset_btn;

    int ex_mode = 2; //determine graph view
    User me = new User(); //TEMPORARY USER to be replaced with logged in user
    double calGoal = 100; //TEMPORARY GOAL to be replaced with user goal

    TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek(); //for getting week of date
    LocalDate chartView = LocalDate.now(); //the current date at time of use
    int current = 0; //date modifier for graph manipulation

    public void initialize() {
        DummyData();
        createCalProg(); //creating progress bar from user data
        createBar(rng_cbx.getValue()); //create graph from user data, default is bar chart
        reset_btn.setDisable(true);
    }

    /***************************************************
     * METHODS FOR CREATING DATA DISPLAY DATA, GRAPHS **
     **************************************************/
    public void createCalProg() {
        HashMap<LocalDate, ArrayList<CalDat>> calCount = new HashMap<>();
        double calsTaken = 0;
        if (rng_cbx.getValue().equals("today"))
            calCount = me.getCalData(chartView, chartView);
        if (rng_cbx.getValue().equals("this week"))
            calCount = me.getCalData(chartView.with(fieldISO, 1),chartView.with(fieldISO, 7));

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

    public void createPie(String timeframe) {
        final int WIDTH = 400;
        final int HEIGHT = 200; //for graph image output
        DefaultPieDataset dataset = new DefaultPieDataset();

        if (me.getExData().isEmpty()) { //user has never logged data for exercise
            noData();
            return;
        }

        if (timeframe.equals("this week")) {
            LocalDate start = (chartView.with(fieldISO, 1));
            LocalDate end = (chartView.with(fieldISO, 7));
            dataset = formPieData(start, end);
        }
        if (timeframe.equals("today")) {
            dataset = formPieData(chartView, chartView);
        }

        if (dataset == null) {
            noData();
            return;
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
        final int HEIGHT = 200; //for graph image output
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (me.getExData().isEmpty()) { //user has never logged exercise data
            noData();
            return;
        }

        if (timeframe.equals("this week")) {
            LocalDate start = (chartView.with(fieldISO, 1));
            LocalDate end = (chartView.with(fieldISO, 7));
            dataset = formBarData(start, end);
        }
        if (timeframe.equals("today")) {
            dataset = formBarData(chartView, chartView);
        }

        if (dataset == null) {
            noData();
            return;
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

        if (hash.isEmpty()) {
            return null;
        }

        int range = (int)(start.datesUntil(end).count())+1;
        ArrayList<LocalDate> daysOfWeek = new ArrayList<>();

        if (range == 1)
            daysOfWeek.add(start);
        else {
            for (int i = 0; i < range; i++) {
                daysOfWeek.add(start.with(fieldISO, i + 1));
            }
        }

        for (LocalDate date : daysOfWeek) {
            try {
                for (ExDat dat : hash.get(date)) {
                    if (dataset.getKeys().contains(dat.getExerciseType())) {
                        dataset.setValue(dat.getExerciseType(), (Double) dataset.getValue(dat.getExerciseType()) + dat.getDuration());
                    } else {
                        dataset.setValue(dat.getExerciseType(), dat.getDuration());
                    }
                }
            } catch (Exception e) {
                System.out.println(e + ": No data for date (" + date + ")");
            }
        }
        return dataset;
    }

    public DefaultCategoryDataset formBarData(LocalDate start, LocalDate end) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        HashMap<LocalDate, ArrayList<ExDat>> hash = me.getExData(start, end);

        if (hash.isEmpty()) {
            return null;
        }

        int range = (int)(start.datesUntil(end).count())+1;
        ArrayList<LocalDate> daysOfWeek = new ArrayList<>();

        if (range == 1)
            daysOfWeek.add(start);
        else {
            for (int i = 0; i < range; i++) {
                daysOfWeek.add(start.with(fieldISO, i + 1));
            }
        }

        for (LocalDate date : daysOfWeek) {
            try {
                for (ExDat dat : hash.get(date)) {
                    if (dataset.getColumnKeys().contains(date)) {
                        dataset.incrementValue(dat.getDuration(), "DURATION", date);
                    } else {
                        dataset.setValue(dat.getDuration(), "DURATION", date);
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e + ": No data for date (" + date + ")");
                dataset.setValue(0, "DURATION", date);
                }
            }
        return dataset;
    }

    public void noData() { //TODO implement for calories
        noexdata.setVisible(true);
        ex_chart_display.setImage(null);
        if (rng_cbx.getValue().equals("today")) {
            noexdata.setX(0);
            noexdata.setText("No data for " + chartView);
        }
        if (rng_cbx.getValue().equals("this week")) {
            noexdata.setX(-40);
            noexdata.setText("No data for " + chartView.with(fieldISO, 1) + "~" + chartView.with(fieldISO, 7));
        }
        ex_chart_display.setImage(null);
    }

    /**************************
     * BUTTON HANDLING EVENTS *
     **************************/
    public void timeChange() {
        current = 0;
        chartView = LocalDate.now();
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

    public void swapCharts() {
        if (ex_mode == 1) {
            ex_mode = 2;
            swap_btn.setText("View Distribution");
            createBar(rng_cbx.getValue());
        } else {
            ex_mode = 1;
            swap_btn.setText("View Progress");
            createPie(rng_cbx.getValue());
        }
    }

    public void redirectCal() {
        System.out.println("Redirect to calories page");
    }

    public void redirectEx() {
        System.out.println("Redirect to exercise page");
    }

    public void nav(ActionEvent actionEvent) { //this is horrible but i dont care right now
        if (actionEvent.getSource() == prev_btn) {
            current--;
            if (rng_cbx.getValue().equals("today")) {
                ex_txt.setText("Your exercise summary for " + LocalDate.now().minusDays(Math.abs(current)));
                ex_txt.setX(-30);
            }
        }
        if (actionEvent.getSource() == next_btn) {
            current++;
            if (rng_cbx.getValue().equals("today")) {
                ex_txt.setText("Your exercise summary for " + LocalDate.now().plusDays(current));
                ex_txt.setX(0);
            }
        }
        if (actionEvent.getSource() == reset_btn) {
            current = 0;
            ex_txt.setX(0);
        }

        if (rng_cbx.getValue().equals("this week")) {
            if (current < 0) {
                chartView = LocalDate.now().minusDays(Math.abs(current)*7);
            }
            if (current > 0) {
                chartView = LocalDate.now().plusDays(current*7);
            }
            if (current == 0) {
                chartView = LocalDate.now();
                ex_txt.setText("Your exercise summary for this week");
            }
            LocalDate s = chartView.with(fieldISO, 1);
            LocalDate e = chartView.with(fieldISO, 7);
            ex_txt.setText("Your exercise summary for " +
                    s + "~" + e);
        }
        if (rng_cbx.getValue().equals("today")) {
            if (current < 0) {
                chartView = LocalDate.now().minusDays(Math.abs(current));
            }
            if (current > 0) {
                chartView = LocalDate.now().plusDays(current);
            }
            if (current == 0) {
                chartView = LocalDate.now();
                ex_txt.setText("Your exercise summary for today");
            }
        }
        if (swap_btn.getText().equals("View Distribution"))
            createBar(rng_cbx.getValue());
        if (swap_btn.getText().equals("View Progress"))
            createPie(rng_cbx.getValue());
        createCalProg();

        reset_btn.setDisable(current == 0);
    }

    /*******************************
     * TEMPORARY DATA DELETE LATER *
     *******************************/
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
