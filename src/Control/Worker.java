package Control;

import Model.Result;
import Model.ResultsQueue;
import Model.Task;
import Utils.FileManager;

public class Worker extends Thread {
    Task task;
    boolean isBusy = false;
    ResultsQueue resultsQueue;

    final FileManager fileManager;

    public Worker(String name, ResultsQueue resultsQueue, FileManager fileManager) {
        this.setName(name);
        this.resultsQueue = resultsQueue;
        this.fileManager = fileManager;
    }

    private void doTask() {
        try {
            isBusy = true;
            long timeStart = System.currentTimeMillis();

            Thread.sleep((long) task.getCost() * 1000);
            int valueAfterTask = 0;
            if(task.getType().equals("WRITE")){
                valueAfterTask = writeTask();
            }
            else {
                valueAfterTask = readTask(false);
            }

            long timeEnd = System.currentTimeMillis();

            this.resultsQueue.add(new Result(task.getId(), valueAfterTask, timeEnd - timeStart));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            isBusy = false;
            System.out.println("🛑 " + this.getName() + " finished task " + task.getId());
        }
    }

    private int writeTask(){
        synchronized (this.fileManager) {
            int currentFileValue = readTask(true);
            fileManager.writeInLine(String.valueOf(currentFileValue + task.getValue()));
            System.out.println("✍️ " + this.getName() + " Get the value " + currentFileValue +  " wrote value " + currentFileValue + " + " + task.getValue() + " = " + (currentFileValue + task.getValue()));
            return currentFileValue + task.getValue();
        }
    }

    private int readTask(boolean isReadingToWrite){
        int value = 0;
        String fileValue = fileManager.read();
        if (!fileValue.isEmpty()) {
            value = Integer.parseInt(fileValue);
            if(!isReadingToWrite){
                System.out.println("📖 " + this.getName() + " read value " + value);
            }
        }
        return value;
    }

    public void run() {
        while (true) {
            synchronized (this) {
                while (!isBusy) {
                    try {
                        System.out.println("⏳ " + this.getName() + " is waiting for a task");
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("🚨 " + this.getName() + " interrupted");
                        return;
                    }
                }
            }

            doTask();
        }
    }

    public boolean isBusy () {
        return isBusy;
    }

    public void setTask(Task task) {
        this.isBusy = true;
        this.task = task;
        synchronized (this) {
            notify();
        }
    }
}
