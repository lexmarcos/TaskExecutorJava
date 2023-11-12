package Utils;

import Model.Task;

import java.util.LinkedList;
import java.util.Random;

public class TaskLoader {
    public static LinkedList<Task> load(int N, int E) {
        LinkedList<Task> queue = new LinkedList<>();
        Random random = new Random();
        double totalTasks = Math.pow(10, N);
        double writeTasksCount = (E * totalTasks) / 100;
        int writeTasksAdded = 0;
        System.out.println("Generating " + totalTasks + " tasks, with " + writeTasksCount + " write tasks and " + (totalTasks - writeTasksCount) + " read tasks");

        for (int i = 0; i < totalTasks; i++) {
            int id = i + 1;
            double cost = random.nextDouble() * 0.01;
            int value = 0;

            String type;
            boolean isPossibleToAddWriteTask = writeTasksAdded < writeTasksCount;
            double numberOfWriteTasksLeft = writeTasksCount - writeTasksAdded;
            double tasksLeft = totalTasks - i;

            boolean shouldAddWriteTask = isPossibleToAddWriteTask && random.nextDouble() < numberOfWriteTasksLeft / tasksLeft;

            if (shouldAddWriteTask) {
                type = "WRITE";
                value = random.nextInt(10);
                writeTasksAdded++;
            } else {
                type = "READ";
            }

            Task task = new Task(id, cost, type, value);
            queue.add(task);
        }
        return queue;
    }

    public static int getExpectedValue(LinkedList<Task> queue) {
        int expectedValue = 0;
        for (Task task : queue) {
            if (task.getType().equals("WRITE")) {
                expectedValue += task.getValue();
            }
        }
        return expectedValue;
    }
}
