package test;

import java.util.List;
import java.util.concurrent.*;

public class DummySubmitTest {
    static ExecutorService main_thread = Executors.newSingleThreadExecutor(); //main thread 1개만 존재
    static ExecutorService back_thread = Executors.newCachedThreadPool(); //background thread 여러개 존재함

    public static void runRunnable() throws Exception{
        /* Runnable 실행 */
        /*
         * Runnable은 값을 반환하는 기능이 존재하지 않으므로 Future.get()은 항상 null을 반환한다.
         * 사실상 Runnable에서는 쓸데가 없음
         *
         * cancel에서 true를 인자로 입력하면 현재 실행중인 thread의 경우 interrupt를 건다.
         */
        Future<?> f = DummySubmitTest.back_thread.submit(new DummyThread());
        Thread.sleep(1000);
//        f.cancel(true);
//        DummySubmitTest.back_thread.shutdown();
        //f.get(5, TimeUnit.SECONDS);
        System.out.println("완료");
    }

    public static void runCallable() throws Exception{
        /*Callable 실행 */

//        ExecutorService executor = Executors.newCachedThreadPool();

//        CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor);
//        completionService.submit(c);

//        Future<Object> f = completionService.take();
//        Future<Object> f = completionService.poll(5, TimeUnit.SECONDS);
        /*
         * Callable에서는 Future.get으로 결과값을 반환받는것이 가능
         * 만약 해당 Thread에서 hang이 걸릴경우 Future.get에서 무한정 대기할 가능성이 있으므로 timeout을 설정할것
         * 해당 Callable에서 while(true)같이 처음부터 무한정 작동하는 코드를 작성했을경우 timeout으로 코드를 빠져나와도 shutdown이 걸리지가 않음 주의
         */
        Future<Object> f = DummySubmitTest.back_thread.submit(new DummyCall());
        try {
            System.out.println(f.get(1, TimeUnit.SECONDS)); //timeout 설정 설정한 시간을 넘어가서 실행되면 TimeoutException 발생
        }catch(TimeoutException e) {
            System.out.println("timeout");
            f.cancel(true);
        }finally {
//            List<Runnable> r = DummySubmitTest.back_thread.shutdownNow();
//            System.out.println(r.size());
//            for(Runnable e : r) {
//                System.out.println(e.toString());
//            }
        }

        System.out.println("완료");
    }
    public static void main(String[] args) throws Exception{
        //ExecutorService 자체가 shutdown을 시키지 않는 이상 계속 돌아간다
        //ExecutorCompletionService 를 이용하면 take()를 호출하여 완료된 작업을 가져오는 형태로 구성할수 있다
        //watchService의 take() 자체가 Excecutor - take를 이용했을 가능성이 높다
        DummySubmitTest.runCallable();
    }
}

