package com.company;

public class Process {

    int id;
    int arrivalTime;
    int phaseL;
    int load;

    public Process(int id, int arrivalTime, int phaseL, int load) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.phaseL = phaseL;
        this.load = load;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
}
