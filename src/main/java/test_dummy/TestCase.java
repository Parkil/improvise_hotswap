package test_dummy;

import java.util.concurrent.Future;

public class TestCase {
    public static void main(String[] args) throws Exception{
        DataStorage.completionService.submit(new ChkEvent());

        Future<Object> future = DataStorage.completionService.take();
        Object result = future.get();
        System.out.println("aaabbb : "+result);
        DataStorage.executor.shutdown();
    }
}
