package com.company;

import java.util.ArrayList;

public class CPU {

    ArrayList<Process> processes;
    ArrayList<Process> ended;

    public CPU() {
        processes = new ArrayList<>();
        ended = new ArrayList<>();
    }

    public int loadSum() {
        int sum = 0;
        for(Process p : processes)
            sum+=p.load;
        return sum;
    }

    public void updatePhaseL() {
        for(int i = 0; i < processes.size(); i++) {
            --processes.get(i).phaseL;
            if(processes.get(i).phaseL <= 0) {
                ended.add(processes.get(i));
                processes.remove(processes.get(i));
            }
        }
    }

}
