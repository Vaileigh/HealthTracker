package HealthTracker;

import java.time.LocalDate;

public class CalDat {
    LocalDate date;
    String mealType;
    double calories;

    CalDat(LocalDate d, String mt, double c) {
        this.date = d; //input with LocalDate.of(year, month, day);
        this.mealType = mt;
        this.calories = c;
    }

    CalDat(String mt, double c) {
        this.date = LocalDate.now();
        this.mealType = mt;
        this.calories = c;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate d) {
        this.date = d;
    }

    public String getMealType() {
        return this.mealType;
    }

    public void setMealType(String mt) {
        this.mealType = mt;
    }

    public double getCalories() {
        return this.calories;
    }

    public void setCalories(double c) {
        this.calories = c;
    }

    @Override
    public String toString() {
        return "DATE: " + date + " MEALTYPE: " + mealType + " CALORIES: " + calories;
    }
}
