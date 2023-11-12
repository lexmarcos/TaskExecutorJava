package Model;

import java.util.LinkedList;
import java.util.Queue;

public class ResultsQueue {
    Queue<Result> queue;

    public ResultsQueue() {
        this.queue = new LinkedList<>();
    }
    public synchronized void add(Result result) {
        queue.add(result);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Queue<Result> getQueue() {
        return queue;
    }

    public long getTotalTime(){
        long totalTime = 0;
        for(Result result : queue){
            totalTime += result.getTime();
        }
        return totalTime;
    }

    public Result getLastResult(){
        Result lastResult = null;
        for(Result result : queue){
            lastResult = result;
        }
        return lastResult;
    }

    public Result getFirstResult(){
        return queue.peek();
    }
    public synchronized Result remove() {
        return queue.remove();
    }
}
