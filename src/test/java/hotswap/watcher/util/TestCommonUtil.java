package hotswap.watcher.util;

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

public class TestCommonUtil {
    public static List<FileWatchEvent> getObjectFromJson(Path jsonPath) throws IOException {
        List<String> tempList = Files.readAllLines(jsonPath, StandardCharsets.UTF_8);
        String jsonStr = String.join(" ", tempList);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper.readValue(jsonStr, new TypeReference<List<FileWatchEvent>>() {});
    }
}
