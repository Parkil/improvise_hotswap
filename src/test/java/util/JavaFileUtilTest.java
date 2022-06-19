package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JavaFileUtilTest {
    @Test
    @DisplayName("package 명 가져오는 로직 테스트")
    void getPackage() throws IOException {
        Path targetPath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_java_data", "DummyTest.java");
        String packageStr = JavaFileUtil.getPackage(targetPath);

        assertEquals("hotswap.watcher.mock_java_data", packageStr);
    }
}