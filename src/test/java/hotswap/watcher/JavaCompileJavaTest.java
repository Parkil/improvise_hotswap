package hotswap.watcher;

import config.Config;
import dto.ClassFileInfo;
import exception.exception.JavaCompileException;
import hotswap.compiler.CompileJava;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaCompileJavaTest {

    private final Logger logger = LoggerFactory.getLogger(JavaCompileJavaTest.class);

    @Test
    @DisplayName("Java File Compile 테스트")
    void compileTest() {
        Path javaFilePath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_java_data", "DummyTest.java");
        List<Path> javaFilePathList = new ArrayList<>();
        javaFilePathList.add(javaFilePath);

        Throwable error = null;
        try {
            new CompileJava().execCompile(javaFilePathList);
        } catch (JavaCompileException e) {
            error = e;
        }

        logger.info("throwable : "+ error);
        assertNull(error);

        Path javaClassPath = Path.of(Config.getTempClassPath().getAbsolutePath(), "hotswap", "watcher", "mock_java_data", "DummyTest.class");

        assertTrue(Files.exists(javaClassPath));
    }

    @Test
    @DisplayName("컴파일후 class 파일 경로 추출 테스트")
    void getClassPathTest() {
        Path javaFilePath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_java_data", "DummyTest.java");
        List<Path> orgJavaPathList = new ArrayList<>();
        orgJavaPathList.add(javaFilePath);

        List<ClassFileInfo> classPathList = new CompileJava().findClassPaths(orgJavaPathList);

        assertEquals("DummyTest.class", classPathList.get(0).getClassFilePath().getFileName().toString());
    }
}