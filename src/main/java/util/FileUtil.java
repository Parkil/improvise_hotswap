package util;

import exception.runtime.FileException;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    private FileUtil(){}

    public static List<Path> findByFileName(Path startPath, String filePattern) {

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(String.format("glob:%s", filePattern));

        List<Path> result;
        try (Stream<Path> pathStream = Files.find(startPath, Integer.MAX_VALUE,
                (p, basicFileAttributes) -> matcher.matches(p.getFileName()))
        ) {
            result = pathStream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileException(e);
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
            throw new FileException(e);
        }

        return allSubPathList;
    }
}