package hotswap.watcher.mock;

import hotswap.watcher.FileWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WatchServicePollingMock {

    private final Executor executor = Executors.newFixedThreadPool(2);
    private final Path testFilePath = Path.of("src", "test", "java", "testobj", "test.java");

    /*
    volatile는 제한적인 sync를 제공한다

     */
    private volatile boolean initialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public void startPolling() {
        executor.execute(() -> {
            FileWatcher fileWatcher = new FileWatcher();
            try {
                initialized = true;
                WatchService watchService = fileWatcher.initWatcher();
                fileWatcher.fileWatchEventPooling(watchService);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void createFile() {
        executor.execute(() -> {
            try {
                Files.createFile(testFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isFileExists() {
        return Files.exists(testFilePath);
    }

    public void deleteFile() {
        if(isFileExists()) {
            try {
                Files.delete(testFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
