package dto;

import java.nio.file.WatchEvent;

public class FileWatchEvent {
    private final String context;
    private final WatchEvent.Kind<?> eventKind;

    public FileWatchEvent(String context, WatchEvent.Kind<?> eventKind) {
        this.context = context;
        this.eventKind = eventKind;
    }

    public String getContext() {
        return context;
    }

    public WatchEvent.Kind<?> getEventKind() {
        return eventKind;
    }
}