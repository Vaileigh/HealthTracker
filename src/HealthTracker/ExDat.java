package HealthTracker;

import java.time.LocalDate;

class ExDat {
    LocalDate date;
    String exerciseType;
    double duration;
    int steps;
    ExDat(LocalDate ld, String et, double d, int s) {
        this.date = ld;
        this.exerciseType = et;
        this.duration = d;
        this.steps=s;
    }

    ExDat(String et, double d) {
        this.date = LocalDate.now();
        this.exerciseType = et;
        this.duration = d;
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

    double getDuration() {
        return this.duration;
    }

    void setDuration(double d) {
        this.duration = d;
    }

    @Override
    public String toString() {
        return "DATE: " + date + " EXTYPE: " + exerciseType + " DURATION: " + duration;
    }
}