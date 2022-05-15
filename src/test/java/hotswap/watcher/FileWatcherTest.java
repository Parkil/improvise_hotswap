package hotswap.watcher;

import dto.FileWatchEvent;
import hotswap.watcher.event_queue.FileEventBlockingQueue;
import hotswap.watcher.mock.WatchServicePollingMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.WatchService;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class FileWatcherTest {
    @Test
    @DisplayName("File Watcher 초기화 테스트")
    void initWatcher() throws IOException {
        FileWatcher fileWatcher = new FileWatcher();
        WatchService watchService = fileWatcher.initWatcher();

        /*
        watchService.take() - 이벤트가 없으면 이벤트가 생길때까지 대기
        watchService.poll() - 이멘트가 없으면 즉시 null 반환
         */

        assertNotNull(watchService);
        assertNull(watchService.poll());
    }

    @Test
    @DisplayName("File Watcher Polling 테스트")
    void fileWatchEventPooling() throws InterruptedException {
        WatchServicePollingMock watchServicePollingMock = new WatchServicePollingMock();
        watchServicePollingMock.startPolling();

        await()
            .atLeast(Duration.of(50, ChronoUnit.MILLIS))
            .atMost(Duration.of(5, ChronoUnit.SECONDS))
            .with()
            .pollInterval(Duration.of(100, ChronoUnit.MILLIS))
            .until(watchServicePollingMock::isInitialized);

        watchServicePollingMock.createFile();

        FileEventBlockingQueue fileEventBlockingQueue = FileEventBlockingQueue.getInstance();
        FileWatchEvent fileWatchEvent = fileEventBlockingQueue.take();

        assertEquals("test.java", fileWatchEvent.getContext());
        watchServicePollingMock.deleteFile();
        
        //나중에 실 구현시에는 재귀호출을 이용하면 될듯
        fileWatchEvent = fileEventBlockingQueue.take();
        System.out.println(fileWatchEvent.getContext());
        System.out.println(fileWatchEvent.getEventKind().name());
    }
}