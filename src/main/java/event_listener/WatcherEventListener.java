package event_listener;

import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.DirectoryChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WatcherEventListener implements DirectoryChangeListener {

    private final Logger logger = LoggerFactory.getLogger(WatcherEventListener.class);

    @Override
    public void onEvent(DirectoryChangeEvent event) throws IOException {
        logger.info("event : {}", event);
    }

    @Override
    public boolean isWatching() {
        return DirectoryChangeListener.super.isWatching();
    }

    @Override
    public void onIdle(int count) {
        DirectoryChangeListener.super.onIdle(count);
    }

    @Override
    public void onException(Exception e) {
        DirectoryChangeListener.super.onException(e);
    }
}