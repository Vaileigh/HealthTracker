package HealthTracker;

import java.time.LocalDate;

class ExDat {
    LocalDate date;
    String exerciseType;
    double distance;
    int steps;
    ExDat(LocalDate ld, String et, double d, int s) {
        this.date = ld;
        this.distance = d;
        this.steps=s;
    }

    ExDat(String et, double d) {
        this.date = LocalDate.now();
        this.exerciseType = et;
        this.distance = d;
    }

    LocalDate getDate() {
        return this.date;
    }

    void setDate(LocalDate d) {
        this.date = d;
    }

    String getExerciseType() {
        return this.exerciseType;
    }

    void setExerciseType(String et) {
        this.exerciseType = et;
    }

    double getDistance() {
        return this.distance;
    }

    void setDistance(double d) {
        this.distance = d;
    }

    @Override
    public String toString() {
        return "DATE: " + date + " EXTYPE: " + exerciseType + " DURATION: " + distance;
    }
}