package hotswap.watcher;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import dto.FileWatchEvent;
import hotswap.processor.ProcessFileEvent;
import hotswap.thread.FileEventWatchThread;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class FileWatcherTest {

    private final Logger logger = LoggerFactory.getLogger(FileWatcherTest.class);

    @Test
    @DisplayName("File Watcher 초기화 테스트")
    void initWatcher() throws IOException {
        FileWatcher fileWatcher = new FileWatcher();
        WatchService watchService = fileWatcher.initWatcher(Config.getWatchRootPath());

        /*
        watchService.take() - 이벤트가 없으면 이벤트가 생길때까지 대기
        watchService.poll() - 이벤트가 없으면 즉시 null 반환
         */

        assertNotNull(watchService);
        assertNull(watchService.poll());
    }

    @Test
    @DisplayName("File Watcher Polling 테스트")
    void fileWatchEventPooling() throws IOException, InterruptedException {

        FileEventWatchThread fileEventWatchThread = new FileEventWatchThread();
        fileEventWatchThread.startWatchServiceThread();

        //FileEventWatchThread 가 작동할때 까지 대기
        Thread.sleep(1000);

        ProcessFileEvent processFileEvent = new ProcessFileEvent();
        final List<FileWatchEvent>[] fileWatchEventList = new List[]{null};

        Path testFilePath = Path.of("src", "test", "java", "testobj", "test.java");
        Files.createFile(testFilePath);

        await()
            .atLeast(Duration.of(1000, ChronoUnit.MILLIS))
            .atMost(Duration.of(30, ChronoUnit.SECONDS))
            .with()
            .pollInterval(Duration.of(1000, ChronoUnit.MILLIS))
            .until(() -> !(fileWatchEventList[0] = processFileEvent.getWatchedEventList()).isEmpty());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        logger.info("event list json str : {}",objectMapper.writeValueAsString(fileWatchEventList[0]));

        Files.deleteIfExists(testFilePath);
        assertFalse(fileWatchEventList[0].isEmpty());
    }
}