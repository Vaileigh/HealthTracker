package HealthTracker;

public class Exercise {
    private int steps;
    private double distance;    
    private boolean isKm;
    Exercise(){
        steps=0;
        distance=0;
    }
    Exercise(double d, int s, boolean k){
        this.steps=s;
        this.distance=d;
        if(k==false){
            this.isKm=false;
        }
        else{
            this.isKm=true;
        }
    }
}
