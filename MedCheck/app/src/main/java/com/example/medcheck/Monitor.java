package com.example.medcheck;

//This class is used as a semaphore for db interactions
//Each Monitor represents a seperate semaphore instance.
public class Monitor {
    public static int Monitor1;
    public static int Monitor2;

    public static void setMonitor1(int monitor1) {
        Monitor1 = monitor1;
    }
    public static void setMonitor2(int monitor2) {
        Monitor2 = monitor2;
    }

}
