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

    //todo System.getProperty("hotswap.watch.root"), Config.getTempClassPath() 를 인자로 받지 말고 Class 내부 변수로 처리
    //todo 로직을 premain에 다 넣지 말로 agent 초기화 / agent 실행으로 class를 분리
    
    /*
    여기에서 필요한 동작
    agent 실행전 설정 초기화해라
    agent 실행시켜라 
     */
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
