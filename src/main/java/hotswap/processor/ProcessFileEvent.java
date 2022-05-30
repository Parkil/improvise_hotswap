package hotswap.processor;

import dto.EventDetail;
import dto.FileWatchEvent;
import dto.enum_vo.WatchEventType;
import hotswap.event_queue.FileEventBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessFileEvent {

    private final Logger logger = LoggerFactory.getLogger(ProcessFileEvent.class);

    public List<FileWatchEvent> getWatchedEventList() {
        FileEventBlockingQueue fileEventBlockingQueue = FileEventBlockingQueue.getInstance();
        FileWatchEvent fileWatchEvent;

        List<FileWatchEvent> collectedEventList = new ArrayList<>();
        while((fileWatchEvent = fileEventBlockingQueue.poll()) != null) {
            logger.info("collected file watch event : {}", fileWatchEvent);
            collectedEventList.add(fileWatchEvent);
        }

        return collectedEventList;
    }

    public List<String> getTargetFileNameList(List<FileWatchEvent> fileWatchEvents) {
        Map<String, List<EventDetail>> groupByContextMap = fileWatchEvents.stream()
                .flatMap(event -> event.getEventDetailList().stream())
                .collect(Collectors.groupingBy(EventDetail::getContext));

        return groupByContextMap.entrySet().stream()
                .filter(entry -> entry.getValue().stream().noneMatch(listRow -> listRow.getWatchEventType() == WatchEventType.ENTRY_DELETE))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
