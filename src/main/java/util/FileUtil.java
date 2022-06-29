package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil(){}

    public static boolean createTempClassPath(File file) {
        if(!file.exists()) {
            boolean result = file.mkdirs();
            if(!result) {
                logger.info("temp class directory not created. path[{}]", file.getPath());
                return false;
            }
        }

        return true;
    }

    public static List<Path> findByFileName(Path startPath, String filePattern) {

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(String.format("glob:%s", filePattern));

        List<Path> result = new ArrayList<>();
        try (Stream<Path> pathStream = Files.find(startPath, Integer.MAX_VALUE,
                (p, basicFileAttributes) -> matcher.matches(p.getFileName()))
        ) {
            result = pathStream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }

        return result;
    }

    public static List<Path> findAllSubPath(Path startPath) {
        List<Path> allSubPathList = new ArrayList<>();

        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    allSubPathList.add(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allSubPathList;
    }
}