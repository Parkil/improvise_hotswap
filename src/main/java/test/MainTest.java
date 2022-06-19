package test;


import hotswap.thread.FileEventWatchThread;
import hotswap.thread.ProcessFileEventThread;

public class MainTest {
    public static void main(String[] args) {
        new FileEventWatchThread().startWatchServiceThread();
        new ProcessFileEventThread().processEventThread(null);
        /*
        Path testFilePath = Path.of("src", "test", "java", "hotswap", "watcher", "mock_json_data", "multi_event.json");
        List<String> tempList = Files.readAllLines(testFilePath, StandardCharsets.UTF_8);
        String aaa = String.join(" ", tempList);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        List<FileWatchEvent> studentList = objectMapper.readValue(aaa, new TypeReference<List<FileWatchEvent>>() {});
        System.out.println(studentList);

         */
    }
}
