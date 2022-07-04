package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private static Path watchRootPath = Path.of("src", "main", "java");

    private static final File tempClassPath = new File("build/hotswap_temp");

    private Config(){}

    //todo boolean 형을 반환하지 말고 조건이 맞지 않으면 예외를 반환하도록 변경
    public static boolean setWatchRootPath(String pathStr) {
        Path tempPath = Path.of(Optional.ofNullable(pathStr).orElse(""));

        /*
        false를 반환하는 상황이면 agent 자체가 실행되지 않아야 한다 해당 로직에서
        true / false 를 반환하는 것보다는 Runtime 이나 그냥 Exception 을 상속
        받아서 조건이 맞지 않으면 새로운 예외를 반환하게 하는것이 낫지 않을까?
         */
        if(!Files.exists(tempPath)) {
            logger.info("path[{}] is invalid file path. hotswap is not running", tempPath);
            return false;
        }

        if(FileUtil.findByFileName(tempPath, "*.java").isEmpty()) {
            logger.info("path[{}] does not contain java file. hotswap is not running", tempPath);
            return false;
        }

        watchRootPath = tempPath;
        return true;
    }

    public static Path getWatchRootPath() {
        return watchRootPath;
    }

    public static File getTempClassPath() { return tempClassPath; }
}