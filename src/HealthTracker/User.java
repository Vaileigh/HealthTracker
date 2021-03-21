package HealthTracker;

import java.io.Serializable;
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
}
