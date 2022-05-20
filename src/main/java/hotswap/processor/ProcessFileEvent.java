package hotswap.processor;

import dto.FileWatchEvent;
import hotswap.event_queue.FileEventBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProcessFileEvent {

    private final Logger logger = LoggerFactory.getLogger(ProcessFileEvent.class);

    public void process() {
        FileEventBlockingQueue fileEventBlockingQueue = FileEventBlockingQueue.getInstance();
        FileWatchEvent fileWatchEvent;

        List<FileWatchEvent> collectedEventList = new ArrayList<>();

        while((fileWatchEvent = fileEventBlockingQueue.poll()) != null) {
            logger.info("collected file watch event : {}", fileWatchEvent);
            collectedEventList.add(fileWatchEvent);
        }
        
        // todo 수집된 정보 처리 로직 구현
    }
}
