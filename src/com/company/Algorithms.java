package com.company;

import java.util.*;

public class Algorithms {

    private double mean(ArrayList<Integer> loads) {
        int sum = 0;
        for(int l : loads)
            sum += l;
        return (double) sum/loads.size();
    }

    private double meanD(ArrayList<Double> loads) {
        double sum = 0;
        for(double l : loads)
            sum += l;
        return sum/loads.size();
    }

    public void alg1(ArrayList<CPU> CPUs, ArrayList<Process> processes, int p, int z) {
        System.out.println("Algorithm 1:");
        ArrayList<Double> averageLoad = new ArrayList<>();
        int asksForLoad = 0;
        int time = 0;
        int migrations = 0;
        Random rand = new Random();

        while(!processes.isEmpty()) {
            if(processes.get(0).arrivalTime <= time) {
                Process currP = processes.get(0);
                CPU currCPU = CPUs.get(currP.id);

                for(int i = 0; i < z; i++) {
                    int random = rand.nextInt(0, CPUs.size()-1);
                    asksForLoad++;
                    if(CPUs.get(random).loadSum() < p) {
                        currCPU = CPUs.get(random);
                        migrations++;
                        break;
                    }
                }

                currCPU.processes.add(currP);
                processes.remove(currP);
            }
            time++;
            if(time % 10 == 0) {
                ArrayList<Integer> loads = new ArrayList<>();
                for(CPU cpu : CPUs) {
                    if(cpu.loadSum() > 100)
                        loads.add(100);
                    else
                        loads.add(cpu.loadSum());
                    averageLoad.add(mean(loads));
                }
            }
            for(CPU cpu : CPUs) {
                cpu.updatePhaseL();
            }
        }
        double avg = meanD(averageLoad);
        ArrayList<Double> dev = new ArrayList<>();
        double aboveMax = 100;
        for(double l : averageLoad) {
            if(l > 100)
                dev.add(aboveMax);
            else
                dev.add(Math.abs(avg - l));
        }

        System.out.println("Srednie obciazenie: " + avg);
        System.out.println("Odchylenie: " + meanD(dev));
        System.out.println("Zapytania o obciazenie: " + asksForLoad);
        System.out.println("Migracje: " + migrations + "\n");

    }

    public void alg2(ArrayList<CPU> CPUs, ArrayList<Process> processes, int p) {
        System.out.println("Algorithm 2:");
        int noFreeCPU = 0;
        ArrayList<Double> averageLoad = new ArrayList<>();
        int asksForLoad = 0;
        int time = 0;
        int migrations = 0;
        Random rand = new Random();

        while(!processes.isEmpty()) {
            if(processes.get(0).arrivalTime <= time) {
                Process currP = processes.get(0);
                CPU currCPU = CPUs.get(currP.id);
                asksForLoad++;

                if(currCPU.loadSum() > p) {
                    ArrayList<CPU> freeCPU = new ArrayList<>();
                    for(CPU cpu : CPUs) {
                        asksForLoad++;
                        if(cpu.loadSum() < p) {
                            freeCPU.add(cpu);
                        }
                    }
                    if(!freeCPU.isEmpty()) {
                        int random = 0;
                        if(freeCPU.size() > 1)
                            random = rand.nextInt(0, freeCPU.size()-1);
                        currCPU = freeCPU.get(random);
                        migrations++;
                    } else {
                        noFreeCPU++;
                    }
                }
                currCPU.processes.add(currP);
                processes.remove(currP);
            }
            time++;
            if(time % 10 == 0) {
                ArrayList<Integer> loads = new ArrayList<>();
                for(CPU cpu : CPUs) {
                    if(cpu.loadSum() > 100)
                        loads.add(100);
                    else
                        loads.add(cpu.loadSum());
                    averageLoad.add(mean(loads));
                }
            }
            for(CPU cpu : CPUs) {
                cpu.updatePhaseL();
            }
        }
        double avg = meanD(averageLoad);
        ArrayList<Double> dev = new ArrayList<>();
        double aboveMax = 100;
        for(double l : averageLoad) {
            if(l > 100)
                dev.add(aboveMax);
            else
                dev.add(Math.abs(avg - l));
        }

        System.out.println("Srednie obciazenie: " + avg);
        System.out.println("Odchylenie: " + meanD(dev));
        System.out.println("Zapytania o obciazenie: " + asksForLoad);
        System.out.println("Migracje: " + migrations + "\n");
    }

    public void alg3(ArrayList<CPU> CPUs, ArrayList<Process> processes, int p, int r) {
        System.out.println("Algorithm 3:");
        int noFreeCPU = 0;
        ArrayList<Double> averageLoad = new ArrayList<>();
        int asksForLoad = 0;
        int time = 0;
        int migrations = 0;
        Random rand = new Random();

        while(!processes.isEmpty()) {
            ArrayList<CPU> belowR = new ArrayList<>();
            ArrayList<CPU> aboveP = new ArrayList<>();

            for(CPU cpu : CPUs) {
                asksForLoad++;
                if(cpu.loadSum() < r)
                    belowR.add(cpu);
                if(cpu.loadSum() > r)
                    aboveP.add(cpu);
            }

            if(!aboveP.isEmpty() && !belowR.isEmpty()) {
                for(CPU cpu : belowR) {
                    int randomCPU = 0;
                    if(aboveP.size() > 1)
                        randomCPU = rand.nextInt(0, aboveP.size()-1);
                    CPU temp = aboveP.get(randomCPU);
                    if(!temp.processes.isEmpty()) {
                        int random = 0;
                        if(temp.processes.size() > 1)
                            random = rand.nextInt(0, temp.processes.size()-1);
                        Process process = temp.processes.get(random);
                        temp.processes.remove(process);
                        cpu.processes.add(process);
                        migrations++;
                    }
                }
            }

            if(processes.get(0).arrivalTime <= time) {
                Process currP = processes.get(0);
                CPU currCPU = CPUs.get(currP.id);
                asksForLoad++;

                if(currCPU.loadSum() > p) {
                    ArrayList<CPU> freeCPU = new ArrayList<>();
                    for(CPU cpu : CPUs) {
                        if(cpu.loadSum() < p)
                            freeCPU.add(cpu);

                        if(!freeCPU.isEmpty()) {
                            int random = 0;
                            if(freeCPU.size() > 1)
                                random = rand.nextInt(0, freeCPU.size()-1);
                            currCPU = freeCPU.get(random);
                            migrations++;
                        } else
                            noFreeCPU++;
                    }
                }
                currCPU.processes.add(currP);
                processes.remove(currP);
            }

            time++;
            if(time % 10 == 0) {
                ArrayList<Integer> loads = new ArrayList<>();
                for(CPU cpu : CPUs) {
                    if(cpu.loadSum() > 100)
                        loads.add(100);
                    else
                        loads.add(cpu.loadSum());
                    averageLoad.add(mean(loads));
                }
            }
            for(CPU cpu : CPUs) {
                cpu.updatePhaseL();
            }
        }
        double avg = meanD(averageLoad);
        ArrayList<Double> dev = new ArrayList<>();
        for(double l : averageLoad) {
            dev.add(Math.abs(avg - l));
        }

        System.out.println("Srednie obciazenie: " + avg);
        System.out.println("Odchylenie: " + meanD(dev));
        System.out.println("Zapytania o obciazenie: " + asksForLoad);
        System.out.println("Migracje: " + migrations + "\n");
    }
}
