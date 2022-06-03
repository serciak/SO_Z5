package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {

    public static ArrayList<Process> processesGen(int amount, int cpus, int maxPhase, int maxLoad) {
        ArrayList<Process> processes = new ArrayList<>();
        Random rand = new Random();

        for(int i = 0; i < amount; i++) {
            int cid = rand.nextInt(0, cpus-1);
            int arrivalTime = rand.nextInt(0, amount);
            int load = rand.nextInt(0, maxLoad);
            int pl = rand.nextInt(0, maxPhase);
            processes.add(new Process(cid, arrivalTime, pl, load));
        }

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        return processes;
    }

    public static ArrayList<CPU> cpusGen(int c) {
        ArrayList<CPU> cpus = new ArrayList<>();
        for(int i = 0; i < c; i++) {
            cpus.add(new CPU());
        }
        return cpus;
    }

    public static ArrayList<Process> copy(ArrayList<Process> src) {
        ArrayList<Process> res = new ArrayList<>();
        for(Process p : src)
            res.add(new Process(p.id, p.arrivalTime, p.phaseL, p.load));

        return res;
    }

    public static void main(String[] args) {
        int p = 70;
        int z = 5;
        int r = 30;

        int pAmount = 10000;
        int cAmount = 50;
        int maxPhase = 300;
        int maxLoad = 80;

        ArrayList<CPU> CPUs = cpusGen(cAmount);
        ArrayList<Process> processes = processesGen(pAmount, cAmount, maxPhase, maxLoad);

        ArrayList<CPU> CPUs2 = cpusGen(cAmount);
        ArrayList<CPU> CPUs3 = cpusGen(cAmount);

        ArrayList<Process> processes2 = copy(processes);
        ArrayList<Process> processes3 = copy(processes);

        Algorithms algorithms = new Algorithms();
        algorithms.alg1(CPUs, processes, p, z);
        algorithms.alg2(CPUs2, processes2, p);
        algorithms.alg3(CPUs3, processes3, p, r);
    }
}
