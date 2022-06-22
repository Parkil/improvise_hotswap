package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {
    @Test
    @DisplayName("파일 검색 테스트")
    void findFileTest() throws IOException{
        Path basicPath = Path.of("src", "main", "java");
        List<Path> findList = FileUtil.findByFileName(basicPath, "FileEventWatchThread.java");

        assertFalse(findList.isEmpty());
        assertEquals("FileEventWatchThread.java", findList.get(0).getFileName().toString());
    }

    @Test
    @DisplayName("하위 경로 검색 테스트")
    void findSubPathTest() throws IOException{
        Path basicPath = Path.of("src", "main", "java");
        List<Path> subPathList = FileUtil.findAllSubPath(basicPath);

        assertFalse(subPathList.isEmpty());
        assertEquals("src\\main\\java", subPathList.get(0).toString());
    }
}