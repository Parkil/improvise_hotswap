package dto;

import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileWatchEvent {
    private final List<EventDetail> eventDetailList = new ArrayList<>();

    public FileWatchEvent(){}
    public FileWatchEvent(List<WatchEvent<?>> watchEventList) {
        eventDetailList.addAll(watchEventList
                .stream()
                .map(watchEvent -> new EventDetail(watchEvent.context().toString(), watchEvent.kind().name()))
                .collect(Collectors.toList()));
    }

    public List<EventDetail> getEventDetailList() {
        return eventDetailList;
    }

    @Override
    public String toString() {
        return "FileWatchEvent{" +
                "eventDetailList=" + eventDetailList +
                '}';
    }
}