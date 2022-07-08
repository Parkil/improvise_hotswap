package hotswap.event_queue;

import dto.FileWatchEvent;

import java.nio.file.WatchEvent;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class FileEventBlockingQueue extends LinkedBlockingQueue<FileWatchEvent> {

    private static FileEventBlockingQueue fileEventBlockingQueue;

    public static FileEventBlockingQueue getInstance() {
        if(fileEventBlockingQueue == null) {
            fileEventBlockingQueue = new FileEventBlockingQueue();
        }

        return fileEventBlockingQueue;
    }

    public boolean addWatchFilEvent(List<WatchEvent<?>> watchEventList) {
        return super.offer(new FileWatchEvent(watchEventList));
    }
}