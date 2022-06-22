package hotswap.thread;

import config.Config;
import hotswap.watcher.FileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.WatchService;
import java.util.concurrent.*;

public class FileEventWatchThread {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private final Logger logger = LoggerFactory.getLogger(FileEventWatchThread.class);

    // producer
    public void startWatchServiceThread() {
        executorService.execute(() -> {
            FileWatcher fileWatcher = new FileWatcher();
            try {
                logger.info("watch service thread started");
                WatchService watchService = fileWatcher.initWatcher(Config.WATCH_ROOT_PATH);
                fileWatcher.fileWatchEventPooling(watchService);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                ie.printStackTrace();
            }
        });
    }
}
