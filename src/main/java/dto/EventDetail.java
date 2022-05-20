package dto;

import java.nio.file.WatchEvent;

public class EventDetail {
    private final String context;
    private final WatchEvent.Kind<?> eventKind;

    public EventDetail(String context, WatchEvent.Kind<?> eventKind) {
        this.context = context;
        this.eventKind = eventKind;
    }

    public String getContext() {
        return context;
    }

    public WatchEvent.Kind<?> getEventKind() {
        return eventKind;
    }

    @Override
    public String toString() {
        return "EventDetail{" +
                "context='" + context + '\'' +
                ", eventKind=" + eventKind +
                '}';
    }
}
