package hotswap.thread;

import exception.exception.JavaRedefineException;
import exception.runtime.ThreadException;
import hotswap.compiler.RedefineClass;
import hotswap.processor.ProcessFileEvent;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessFileEventThread {

    private static final ScheduledExecutorService schService = Executors.newScheduledThreadPool(1);

    private final RedefineClass redefineClass = new RedefineClass();

    // consumer (using polling)
    public void processEventThread(Instrumentation inst) {
        schService.scheduleAtFixedRate(() -> {
            ProcessFileEvent processFileEvent = new ProcessFileEvent();
            List<String> targetFileNameList = processFileEvent.getTargetFileNameList(processFileEvent.getWatchedEventList());

            if(!targetFileNameList.isEmpty()) {
                try {
                    redefineClass.execRedefine(targetFileNameList, inst);
                } catch (JavaRedefineException e) {
                    throw new ThreadException(e);
                }
            }
        },1000L, 1000L, TimeUnit.MILLISECONDS);
    }
}
