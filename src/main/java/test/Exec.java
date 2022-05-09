package test;

import java.util.concurrent.*;

public class Exec {
    /*
     * Executor+CompletionService 사용
     * CompletionService는 한정된 자원을 여러개의 thread가 사용해야 할시 사용(Javadoc에 그렇게 나와있음)
     */
    void complete_wait() throws Exception{
        Call c = new Call();
//        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor);
        completionService.submit(c);

        Future<Object> f = completionService.take();
        System.out.println(f);
//        Future<Object> f = completionService.poll(5, TimeUnit.SECONDS);
        System.out.println("wait execution.....");
        Object z = f.get();
        System.out.println("exec result : "+z);
        executor.shutdown();
    }

    public static void main(String[] args) throws Exception{
        new Exec().complete_wait();
//        System.out.println("sadfasdfsda");
    }
}
