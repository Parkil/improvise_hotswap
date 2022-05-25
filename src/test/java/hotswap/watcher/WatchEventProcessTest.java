package hotswap.watcher;

import dto.FileWatchEvent;
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

    @Test
    @DisplayName("단일 이벤트 처리 테스트")
    void singleEventProcessTest() throws IOException {
        Path mockFilePath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_json_data", "single_event.json");
        List<FileWatchEvent> mockList = TestCommonUtil.getObjectFromJson(mockFilePath);

        logger.info("mockList : {}", mockList);

        assertEquals(1, mockList.size());
    }
}