package hotswap.event_queue;

import dto.FileWatchEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.WatchEvent;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class FileEventBlockingQueue extends LinkedBlockingQueue<FileWatchEvent> {

    private final transient Logger logger = LoggerFactory.getLogger(FileEventBlockingQueue.class);

    private static FileEventBlockingQueue fileEventBlockingQueue;

    public static FileEventBlockingQueue getInstance() {
        if(fileEventBlockingQueue == null) {
            fileEventBlockingQueue = new FileEventBlockingQueue();
        }

        return fileEventBlockingQueue;
    }

    public void addWatchFilEvent(List<WatchEvent<?>> watchEventList) {
        FileWatchEvent event = new FileWatchEvent(watchEventList);
        logger.info("queue offer result : {}", super.offer(event));
    }
}