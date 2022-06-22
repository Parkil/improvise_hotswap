package hotswap.thread;

import hotswap.compiler.RedefineClass;
import hotswap.processor.ProcessFileEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessFileEventThread {

    private static final ScheduledExecutorService schService = Executors.newScheduledThreadPool(1);

    private final Logger logger = LoggerFactory.getLogger(ProcessFileEventThread.class);

    // consumer (using polling)
    public void processEventThread(Instrumentation inst) {
        schService.scheduleAtFixedRate(() -> {
//            logger.info("ProcessFileEventThread polling...");
            ProcessFileEvent processFileEvent = new ProcessFileEvent();
            List<String> targetFileNameList = processFileEvent.getTargetFileNameList(processFileEvent.getWatchedEventList());

            if(!targetFileNameList.isEmpty()) {
                logger.info("targetList : {}", targetFileNameList);
                new RedefineClass().redefine(targetFileNameList, inst);
            }
        },1000L, 1000L, TimeUnit.MILLISECONDS);
    }
}
