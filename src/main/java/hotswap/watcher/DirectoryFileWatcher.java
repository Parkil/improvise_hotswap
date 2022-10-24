package hotswap.watcher;

import event_listener.WatcherEventListener;
import io.methvin.watcher.DirectoryWatcher;
import io.methvin.watcher.hashing.FileHasher;

import java.io.IOException;
import java.nio.file.Path;

public class DirectoryFileWatcher {

    public DirectoryWatcher initWatcher(Path rootPath) throws IOException {
        DirectoryWatcher.Builder builder = DirectoryWatcher.builder()
            .path(rootPath)
            .listener(new WatcherEventListener())
            .fileHasher(FileHasher.LAST_MODIFIED_TIME);

        return builder.build();
    }
}
