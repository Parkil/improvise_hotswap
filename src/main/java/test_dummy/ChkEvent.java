package test_dummy;

import java.util.concurrent.Callable;

public class ChkEvent implements Callable<Object> {
    @Override
    public Object call() throws InterruptedException {
        System.out.println("callable called");
        return DataStorage.taskQueue.take();
    }
}