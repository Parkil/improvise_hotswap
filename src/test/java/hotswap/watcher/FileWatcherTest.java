package hotswap.watcher;

import hotswap.watcher.event_queue.FileEventBlockingQueue;
import hotswap.watcher.mock.WatchServicePollingMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.WatchService;
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
    void fileWatchEventPooling(){
        WatchServicePollingMock watchServicePollingMock = new WatchServicePollingMock();
        watchServicePollingMock.startPolling();
        watchServicePollingMock.sleep(1000);
        watchServicePollingMock.createFile();

        System.out.println("is Empty : " + FileEventBlockingQueue.getInstance().isEmpty());
        System.out.println("after size : " + FileEventBlockingQueue.getInstance().size());

        watchServicePollingMock.deleteFile();
        
        //todo Thread.sleep으로 하니까 시차 차이가 나느듯 하이 이를 Awaitility 로 변경할 필요가 있음
        
        assertNotEquals(0, FileEventBlockingQueue.getInstance().size());
    }
}