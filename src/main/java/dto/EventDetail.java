package dto;

import dto.enum_vo.WatchEventType;

import java.nio.file.WatchEvent;

public class EventDetail {
    private final String context;
    private final WatchEventType watchEventType;

    public EventDetail(String context, WatchEvent.Kind<?> eventKind) {
        this.context = context;
        watchEventType = WatchEventType.valueOf(eventKind.name());
    }

    public String getContext() {
        return context;
    }

    public WatchEventType getWatchEventType() {
        return watchEventType;
    }

    @Override
    public String toString() {
        return "EventDetail{" +
                "context='" + context + '\'' +
                ", watchEventType=" + watchEventType.name() +
                '}';
    }
}
