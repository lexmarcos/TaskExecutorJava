package Model;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    Queue<Task> queue;

    public TaskQueue(LinkedList<Task> queue){
        this.queue = queue;
    }

    public Queue<Task> getQueue() {
        return queue;
    }

    public synchronized Task getNextTask(){
        return queue.poll();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }
}
