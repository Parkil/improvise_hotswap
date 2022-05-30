package hotswap.watcher;

import dto.FileWatchEvent;
import hotswap.processor.ProcessFileEvent;
import hotswap.watcher.util.TestCommonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchEventProcessTest {

    private final Logger logger = LoggerFactory.getLogger(WatchEventProcessTest.class);

    private List<String> refineEvent(Path targetPath) throws IOException {
        List<FileWatchEvent> mockList = TestCommonUtil.getObjectFromJson(targetPath);

        logger.info("mockList : {}", mockList);

        ProcessFileEvent processFileEvent = new ProcessFileEvent();
        return processFileEvent.getTargetFileNameList(mockList);
    }

    @Test
    @DisplayName("이벤트 처리 리스트 - 단일 이벤트")
    void singleEventProcessTest() throws IOException {
        Path mockFilePath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_json_data", "single_event.json");
        List<String> targetNameList = refineEvent(mockFilePath);

        assertEquals(1, targetNameList.size());
    }

    @Test
    @DisplayName("이벤트 처리 리스트 - 다중 이벤트 - Delete 대상 필터링")
    void multiEventProcessTest() throws IOException {
        Path mockFilePath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_json_data", "multi_event.json");
        List<String> targetNameList = refineEvent(mockFilePath);

        assertEquals("fdsfds.java", targetNameList.get(0));
        assertEquals(1, targetNameList.size());
    }
}