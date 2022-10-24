package hotswap.watcher;

import event_listener.WatcherEventListener;
import io.methvin.watcher.DirectoryWatcher;
import io.methvin.watcher.hashing.FileHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class DirectoryFileWatcher {

    private final Logger logger = LoggerFactory.getLogger(DirectoryFileWatcher.class);

    public DirectoryWatcher initWatcher(Path rootPath) throws IOException {
        DirectoryWatcher.Builder builder = DirectoryWatcher.builder()
            .path(rootPath)
            .listener(new WatcherEventListener())
            .fileHasher(FileHasher.LAST_MODIFIED_TIME);

        return builder.build();
    }
}
