package hotswap.watcher.event_queue;

import dto.FileWatchEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.WatchEvent;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class FileEventBlockingQueue extends LinkedBlockingQueue<FileWatchEvent> {

    private final transient Logger logger = LoggerFactory.getLogger(FileEventBlockingQueue.class);

    private static FileEventBlockingQueue fileEventBlockingQueue;

    public static FileEventBlockingQueue getInstance() {
        if(fileEventBlockingQueue == null) {
            fileEventBlockingQueue = new FileEventBlockingQueue();
        }

        return fileEventBlockingQueue;
    }

    public synchronized void addWatchFilEvent(List<WatchEvent<?>> watchEventList) {
        watchEventList.forEach(row -> {
            FileWatchEvent event = new FileWatchEvent(row.context().toString(), row.kind());
            logger.info("queue offer result : {}",super.offer(event));
        });
        
        //thread 기반에서 size()와 isEmpty()는 정상적으로 작동하지 않으므로 주의
        logger.info("size test : {}", super.size());
        logger.info("is empty test : {}", isEmpty());
    }
}