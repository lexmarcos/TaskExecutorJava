import Control.Executor;
import Model.Task;
import Model.TaskQueue;
import Utils.ResultDisplayer;
import Utils.TaskLoader;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LinkedList<Task> taskQueueLoaded = TaskLoader.load(2, 40);
        int expectedValueOfWriteTasks = TaskLoader.getExpectedValue(taskQueueLoaded);
        TaskQueue taskQueue = new TaskQueue(taskQueueLoaded);

        Executor executor = new Executor(4, taskQueue, "results.txt");
        executor.start();
        executor.join();

        long totalTime = executor.getResultsQueue().getTotalTime();
        int valueOfTheFile = Integer.parseInt(executor.getFileManager().read());
        int valueOfTheResultQueue = executor.getResultsQueue().getLastResult().getResult();

        System.out.println();
        ResultDisplayer.displayTotalTime(totalTime);
        ResultDisplayer.displayExpectedValue(expectedValueOfWriteTasks);
        ResultDisplayer.displayResultOfTheFileAfterProcess(valueOfTheFile);
        ResultDisplayer.displayResultOfResultQueue(valueOfTheResultQueue);
        ResultDisplayer.displayIfTheValuesIsTheSame(expectedValueOfWriteTasks, valueOfTheResultQueue, valueOfTheFile);
    }
}
