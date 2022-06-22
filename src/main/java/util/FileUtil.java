package util;

import config.Config;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
역할 - 기본 경로에서 주어진 파일명으로 파일을 검색
책임 - 파일을 검색하여 있으면 파일리스트를 없으면 빈 리스트를 반환
협력
    아는것 - 검색시작 경로, 검색할 파일명
    수행해야 하는것 - 검색시작 경로로부터 파일을 검색
 */
public class FileUtil {
    private FileUtil(){}

    public static List<Path> findByFileName(Path startPath, String fileName) {

        List<Path> result = new ArrayList<>();
        try (Stream<Path> pathStream = Files.find(startPath,
                Integer.MAX_VALUE,
                (p, basicFileAttributes) ->
                        p.getFileName().toString().equalsIgnoreCase(fileName))
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