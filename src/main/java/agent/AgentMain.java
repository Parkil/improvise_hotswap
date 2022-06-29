package agent;

import config.Config;
import hotswap.thread.FileEventWatchThread;
import hotswap.thread.ProcessFileEventThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtil;

import java.lang.instrument.Instrumentation;

public class AgentMain {
    private static final Logger logger = LoggerFactory.getLogger(AgentMain.class);

    private AgentMain() {}

    public static void premain(String agentArgs, Instrumentation inst) {
        //vm option example : -javaagent:~.jar -Dhotswap.watch.root=src/main/java
        if(Config.setWatchRootPath(System.getProperty("hotswap.watch.root"))
                && FileUtil.createTempClassPath(Config.getTempClassPath())) {
            logger.info("hotswap starting watch root : {}", Config.getWatchRootPath());

            new FileEventWatchThread().startWatchServiceThread();
            new ProcessFileEventThread().processEventThread(inst);
        }
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        logger.info("agentmain called");
    }
}
