package hotswap.watcher.event_queue;

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

    public void putWatchFilEvent(List<WatchEvent<?>> watchEventList) {
        watchEventList.stream()
                .map(row -> new FileWatchEvent(row.context().toString(), row.kind()))
                .forEach(super::offer);
    }

    @Override
    public boolean isEmpty() {
        return super.size() == 0;
    }
}
