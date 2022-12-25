package agent;

import config.Config;
import exception.exception.ConfigException;
import hotswap.thread.FileEventWatchThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class AgentMain {
    private static final Logger logger = LoggerFactory.getLogger(AgentMain.class);

    private AgentMain() {}

    public static void premain(String agentArgs, Instrumentation inst) {
        /*
        //vm option example : -javaagent:~.jar -Dhotswap.watch.root=src/main/java
        try {
            Config.setWatchRootPath(System.getProperty("hotswap.watch.root"));
            Config.createTempClassPath();
            Config.setRedefineClass(inst);

            new FileEventWatchThread().startWatchServiceThread();
        } catch (ConfigException e) {
            e.printStackTrace();
        }*/
        inst.addTransformer(new TestTransformer(), true);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        logger.info("agentmain called");
    }
}
