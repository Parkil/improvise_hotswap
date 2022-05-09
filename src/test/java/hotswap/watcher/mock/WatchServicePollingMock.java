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

    public void startPolling() {
        executor.execute(() -> {
            System.out.println("startPolling thread start");
            FileWatcher fileWatcher = new FileWatcher();
            try {
                WatchService watchService = fileWatcher.initWatcher();
                fileWatcher.fileWatchEventPooling(watchService);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void createFile() {
        executor.execute(() -> {
            System.out.println("createFile thread start");
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

    public void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }
}
