package Control;

import Model.ResultsQueue;
import Model.Task;
import Model.TaskQueue;
import Utils.FileManager;
import Utils.ResultDisplayer;

import java.util.ArrayList;

public class Executor extends Thread {
    TaskQueue taskQueue;
    ResultsQueue resultsQueue;
    ArrayList<Worker> workers = new ArrayList<>();
    private volatile boolean running = true;
    FileManager fileManager;

    public Executor(int numbersOfWorkers, TaskQueue taskQueue, String filePath) {
        this.taskQueue = taskQueue;
        this.resultsQueue = new ResultsQueue();
        this.fileManager = new FileManager(filePath);
        createWorkers(numbersOfWorkers, this.resultsQueue);
    }

    private void createWorkers(int numbersOfWorkers,ResultsQueue resultsQueue) {
        for (int i = 0; i < numbersOfWorkers; i++) {
            workers.add(new Worker("Worker " + i, resultsQueue, this.fileManager));
            workers.get(i).start();
        }
    }

    @Override
    public void run() {
        long timeStart = System.currentTimeMillis();
        while (running) {
            if (!taskQueue.isEmpty()) {
                Worker worker = getFreeWorker();
                if (worker != null) {
                    Task task = taskQueue.getNextTask();
                    if (task != null) {
                        worker.setTask(task);
                    }
                }
            }else if (areAllWorkersFree()) {
                System.out.println("✨ All workers are free and there are no more tasks to execute");
                stopWorkers();
                stopExecutor();
                long timeEnd = System.currentTimeMillis();
                System.out.println();
                ResultDisplayer.displayTotalTime(timeEnd - timeStart);
            }
        }
    }

    private boolean areAllWorkersFree() {
        for (Worker worker : workers) {
            if (worker.isBusy()) {
                return false;
            }
        }
        return true;
    }

    private void stopWorkers() {
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }

    public void stopExecutor() {
        running = false;
    }

    private Worker getFreeWorker() {
        for (Worker worker : workers) {
            if (!worker.isBusy()) {
                return worker;
            }
        }
        return null;
    }

    public TaskQueue getTaskQueue() {
        return taskQueue;
    }

    public ResultsQueue getResultsQueue() {
        return resultsQueue;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public boolean isRunning() {
        return running;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
