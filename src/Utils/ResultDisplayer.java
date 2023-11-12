package Utils;

public class ResultDisplayer {
    public static void displayTotalTime(long totalTime) {
        System.out.println("⏱ Total Time: " + totalTime + "ms");
    }

    public static void displayExpectedValue(int expectedValue) {
        System.out.println("🎯 Expected value of write tasks: " + expectedValue);
    }

    public static void displayResultOfTheFileAfterProcess(int lastResult) {
        System.out.println("📄 Value of the file after process: " + lastResult);
    }

    public static void displayResultOfResultQueue(int result) {
        System.out.println("📊 Value of result queue after process: " + result);
    }

    public static void displayIfTheValuesIsTheSame(int expectedValue, int resultQueueValue, int fileValue) {
        if(expectedValue == resultQueueValue && resultQueueValue == fileValue){
            System.out.println("🟢 Same value in all? " + true + " ✅");
        }else{
            System.out.println("🔴 Same value in all? " + false + " ❌");
        }
    }
}
