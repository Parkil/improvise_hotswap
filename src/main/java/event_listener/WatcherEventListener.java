package event_listener;

import config.Config;
import exception.exception.ConfigException;
import exception.exception.JavaCompileException;
import exception.exception.JavaRedefineException;
import exception.runtime.ThreadException;
import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.DirectoryChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class WatcherEventListener implements DirectoryChangeListener {

    private final Logger logger = LoggerFactory.getLogger(WatcherEventListener.class);

    @Override
    public void onEvent(DirectoryChangeEvent event) {
        logger.info("event : {}", event);

        switch (event.eventType()) {
            case CREATE:
            case MODIFY:
                List<String> targetFileNameList = new ArrayList<>();
                targetFileNameList.add(String.valueOf(event.path().getFileName()));
                try {
                    Config.getRedefineClass().execRedefine(targetFileNameList);
                } catch (JavaRedefineException e) {
                    JavaCompileException javaCompileException = (JavaCompileException)e.getCause();
                    javaCompileException.getDiagnosticList().forEach(row ->
                            logger.info("compile error file :{}, line : {}, code : {}, msg : {}",
                                    row.getSource().getName(), row.getLineNumber(), row.getCode(),
                                    row.getMessage(Locale.ENGLISH))
                    );
                } catch (ConfigException e) {
                    throw new ThreadException(e);
                }
                break;
            default:
                String anotherEventName = event.eventType().name();
                logger.info("another event type[{}] passing.", anotherEventName);
        }
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