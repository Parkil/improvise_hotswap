package test;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.FileWatchEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws IOException {
        Path testFilePath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_json_data", "multi_event.json");
        List<String> tempList = Files.readAllLines(testFilePath, StandardCharsets.UTF_8);
        String aaa = String.join(" ", tempList);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        List<FileWatchEvent> studentList = objectMapper.readValue(aaa, new TypeReference<List<FileWatchEvent>>() {});
        System.out.println(studentList);
    }
}