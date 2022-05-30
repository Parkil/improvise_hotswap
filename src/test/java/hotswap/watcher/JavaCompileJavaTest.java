package hotswap.watcher;

import hotswap.compiler.CompileJava;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaCompileJavaTest {

    private final Logger logger = LoggerFactory.getLogger(JavaCompileJavaTest.class);

    @Test
    @DisplayName("Java File Compile 테스트")
    void compileTest() throws IOException {
        Path javaFilePath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_java_data", "DummyTest.java");
        List<Path> javaFilePathList = new ArrayList<>();
        javaFilePathList.add(javaFilePath);

        CompileJava compileJava = new CompileJava();
        List<Diagnostic<? extends JavaFileObject>> compileErrorList = compileJava.execCompile(javaFilePathList);
        logger.info("compileResultList : {}", compileErrorList);

        assertTrue(compileErrorList.isEmpty());

        Path javaClassPath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_java_data", "DummyTest.class");
        assertTrue(Files.exists(javaClassPath));
    }
}