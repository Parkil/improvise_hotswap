package hotswap.thread;

import config.Config;
import exception.runtime.ThreadException;
import hotswap.watcher.DirectoryFileWatcher;
import io.methvin.watcher.DirectoryWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.*;

public class FileEventWatchThread {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private final Logger logger = LoggerFactory.getLogger(FileEventWatchThread.class);

    // producer
    public void startWatchServiceThread() {
        executorService.execute(() -> {
            DirectoryFileWatcher directoryFileWatcher = new DirectoryFileWatcher();
            try {
                logger.info("watch service thread started");
                DirectoryWatcher directoryWatcher = directoryFileWatcher.initWatcher(Config.getWatchRootPath());
                directoryWatcher.watch();
            } catch (IOException ioe) {
                throw new ThreadException(ioe);
            }
        });
    }
}
