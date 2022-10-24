package hotswap.thread;

import exception.exception.JavaCompileException;
import exception.exception.JavaRedefineException;
import hotswap.compiler.RedefineClass;
import hotswap.processor.ProcessFileEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessFileEventThread {

    private final Logger logger = LoggerFactory.getLogger(ProcessFileEventThread.class);

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
                    JavaCompileException javaCompileException = (JavaCompileException)e.getCause();
                    javaCompileException.getDiagnosticList().forEach(row ->
                        logger.info("compile error file :{}, line : {}, code : {}, msg : {}",
                                row.getSource().getName(), row.getLineNumber(), row.getCode(),
                                row.getMessage(Locale.ENGLISH))
                    );
                }
            }
        },1000L, 1000L, TimeUnit.MILLISECONDS);
    }
}
