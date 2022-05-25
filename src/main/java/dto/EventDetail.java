package dto;

import dto.enum_vo.WatchEventType;

public class EventDetail {
    private final String context;
    private final WatchEventType watchEventType;

    public EventDetail() {
        this.context = "";
        this.watchEventType = WatchEventType.NONE;
    }

    public EventDetail(String context, String watchEventTypeStr) {
        this.context = context;
        this.watchEventType = WatchEventType.valueOf(watchEventTypeStr);
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
