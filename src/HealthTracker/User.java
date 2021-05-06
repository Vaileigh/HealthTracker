package HealthTracker;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class User implements Serializable {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private double height;
    private double startWeight;
    private int verificationCode;
    private boolean verified;
    private HashMap<LocalDate, ArrayList<CalDat>> calData = new HashMap<>();
    private HashMap<LocalDate, ArrayList<ExDat>> exData = new HashMap<>();

    User(){
        username = "example";
        password = "1234";
        verified = false;
    }

    User(String user, String pass){
        username = user;
        password = pass;
        verified = false;

        //generates a random 5 digit verification code
        Random rand = new Random(System.nanoTime());
        verificationCode = rand.nextInt(90000) + 10000;
    }

    User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        verified = false;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String pass){
        password= pass;
    }

    public void setUsername(String user){
        username = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(double startWeight) {
        this.startWeight = startWeight;
    }

    public int getVerificationCode() {
        return verificationCode;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public static void main(String[] args) {
        User user = new User("Jimmy", "Bob");
        System.out.println(user.getVerificationCode());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void recordCal(CalDat cal) {
        ArrayList<CalDat> items = calData.get(cal.getDate());
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(cal);
        calData.put(cal.getDate(), items);
    }

    HashMap<LocalDate, ArrayList<CalDat>> getCalData() {
        return calData;
    }

    HashMap<LocalDate, ArrayList<CalDat>> getCalData(LocalDate start, LocalDate end) { //allow selected range of days from current day
        HashMap<LocalDate, ArrayList<CalDat>> returnData = new HashMap<>();
        LocalDate today = start;
        int range = start.until(end).getDays()+1;
        for (int i = 0; i < range; i++) {
            try {
                if (calData.get(today) != null) {
                    returnData.put(today, calData.get(today));
                }
            }
            catch (Exception e){
                System.out.println(e + ": Data for date (" + today + ")");
            }
            today = today.plusDays(1);
        }
        return returnData;
    }

    public void recordEx(ExDat ex) {
        ArrayList<ExDat> items = exData.get(ex.getDate());
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(ex);
        exData.put(ex.getDate(), items);
    }

    HashMap<LocalDate, ArrayList<ExDat>> getExData() {
        return exData;
    }

    HashMap<LocalDate, ArrayList<ExDat>> getExData(LocalDate start, LocalDate end) { //allow selected range of days from current day
        HashMap<LocalDate, ArrayList<ExDat>> returnData = new HashMap<>();
        LocalDate today = start;
        int range = start.until(end).getDays()+1;
        for (int i = 0; i < range; i++) {
            try {
                if (exData.get(today) != null) {
                    returnData.put(today, exData.get(today));
                }
            }
            catch (Exception e){
                System.out.println(e + ": Data for date (" + today + ")");
            }
            today = today.plusDays(1);
        }
        return returnData;
    }
}
