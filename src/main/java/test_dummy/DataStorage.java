package test_dummy;

import java.util.concurrent.*;

public class DataStorage {
    public static final LinkedBlockingQueue<Object> taskQueue = new LinkedBlockingQueue();
    public static final ExecutorService executor = Executors.newCachedThreadPool();
    public static final CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor);
}
