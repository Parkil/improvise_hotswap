package hotswap.watcher;

import hotswap.watcher.event_queue.FileEventBlockingQueue;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher {

    public WatchService initWatcher() throws IOException {
        FileSystem defaultFileSystem = FileSystems.getDefault();
        WatchService watchService = defaultFileSystem.newWatchService();
        Path targetPath = defaultFileSystem.getPath("src", "test", "java", "testobj");
        targetPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        return watchService;
    }

    /*
        watchKey.pollEvents()를 받아서 바로 처리하게 되면 구현은 편하겠지만
        event 발생 <-> event 처리 로직이 붙어버리고(event 처리로직을 별도 class 로 뺀다고 해도 문제는 동일하다)
        테스트 작성이 불가능하다(데이터가 polling event 발생 -> polling event 처리 로 이동하면서 내부로직으로 처리됨)  

        이를 
        event 발생 <--- event 저장소 ----> event 처리 로 분리를 해야 할듯
        event 저장소 -> event 처리 부분은 thread 를 이용하면 될듯

        작동방식
            1.fileWatchEventPooling에서 event가 발생하면 해당 event를 collection에 저장한다(thread-safe 한 collection으로)
            2.1번의 collection을 가져오는 ExecutorService(ExecutorCompletionService)를 take() 호출
                2-1.해당 서비스에서는 collection의 값이 있는지 확인
            3.take()의 값이 null이 아닌 경우 event 가 존재한다는 뜻이므로 해당 event를 수행
    */
    public void fileWatchEventPooling(WatchService watchService) throws InterruptedException {
        WatchKey watchKey;
        while((watchKey = watchService.take()) != null) {
            FileEventBlockingQueue.getInstance().putWatchFilEvent(watchKey.pollEvents());
            watchKey.reset();
        }
    }
}
