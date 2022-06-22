package agent;

import hotswap.thread.FileEventWatchThread;
import hotswap.thread.ProcessFileEventThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class AgentMain {
    private static final Logger logger = LoggerFactory.getLogger(AgentMain.class);

    private AgentMain() {}

    public static void premain(String agentArgs, Instrumentation inst) {
        logger.info("premain called");

        new FileEventWatchThread().startWatchServiceThread();
        new ProcessFileEventThread().processEventThread(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        logger.info("agentmain called");
    }
}
