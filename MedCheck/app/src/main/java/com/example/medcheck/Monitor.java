package com.example.medcheck;

//This class is used as a semaphore for db interactions
//Each Monitor represents a seperate semaphore instance.
public class Monitor {
    private int Monitor1;
    private int Monitor2;

    public Monitor(){}
    public void setMonitor1(int monitor1) {
        Monitor1 = monitor1;
    }

    public int getMonitor1() {
        return Monitor1;
    }

    public int getMonitor2() {
        return Monitor2;
    }

    public void setMonitor2(int monitor2) {
        Monitor2 = monitor2;
    }

}
