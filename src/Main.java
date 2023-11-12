import Control.Executor;
import Model.Task;
import Model.TaskQueue;
import Utils.TaskLoader;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LinkedList<Task> taskQueueLoaded = TaskLoader.load(5, 40);
        TaskQueue taskQueue = new TaskQueue(taskQueueLoaded);
        Executor executor = new Executor(256, taskQueue, "output.txt");
        executor.start();
    }
}
