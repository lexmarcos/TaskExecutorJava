import Control.Executor;
import Model.Task;
import Model.TaskQueue;
import Utils.ResultDisplayer;
import Utils.TaskLoader;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter N (power for total tasks): ");
        int N = scanner.nextInt();

        System.out.print("Enter E (percentage for write tasks): ");
        int E = scanner.nextInt();

        System.out.print("Enter T (number of workers): ");
        int T = scanner.nextInt();

        LinkedList<Task> taskQueueLoaded = TaskLoader.load(N, E);
        int expectedValueOfWriteTasks = TaskLoader.getExpectedValue(taskQueueLoaded);
        TaskQueue taskQueue = new TaskQueue(taskQueueLoaded);

        Executor executor = new Executor(T, taskQueue, "results.txt");
        executor.start();
        executor.join();

        int valueOfTheFile = Integer.parseInt(executor.getFileManager().read());
        int valueOfTheResultQueue = executor.getResultsQueue().getLastResult().getResult();

        ResultDisplayer.displayExpectedValue(expectedValueOfWriteTasks);
        ResultDisplayer.displayResultOfTheFileAfterProcess(valueOfTheFile);
        ResultDisplayer.displayResultOfResultQueue(valueOfTheResultQueue);
        ResultDisplayer.displayIfTheValuesIsTheSame(expectedValueOfWriteTasks, valueOfTheResultQueue, valueOfTheFile);
    }
}
