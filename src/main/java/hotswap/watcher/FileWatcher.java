package hotswap.watcher;

import hotswap.event_queue.FileEventBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher {

    private final Logger logger = LoggerFactory.getLogger(FileWatcher.class);

    public WatchService initWatcher(Path watchTargetPath) throws IOException {
        FileSystem defaultFileSystem = FileSystems.getDefault();
        WatchService watchService = defaultFileSystem.newWatchService();
        Path targetPath = defaultFileSystem.getPath("src", "test", "java", "testobj");
        watchTargetPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

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


        여기에서 문제가 되는점
        일반적인 File 생성 -> 삭제의 경우 event가 1개만 나오지만
        Intellij 에서 생성시에는 1개의 파일에서 여러개의 event가 생성되는 경우가 있다

        처음에는 polling을 고려하고 있지 않았지만 IDE에서 파일 등록/수정시 임시파일 생성 -> 본파일 생성 -> 임시파일 삭제
        프로세스로 가고 있고 이를 event 별로 처리를 하게 되면 임시파일도 compile이나 기타처리를 해야 하는 문제가 있어 보이기 
        때문에 이를 polling으로 변경하는게 맞아 보인다
        
        queue에 저장하는 로직은 그대로 가고 queue를 가져오는 로직에서 polling을 처리하는게 맞을듯
    */
    public void fileWatchEventPooling(WatchService watchService) throws InterruptedException {
        WatchKey watchKey;
        while((watchKey = watchService.take()) != null) {
            FileEventBlockingQueue.getInstance().addWatchFilEvent(watchKey.pollEvents());
            watchKey.reset();
        }
    }
}
