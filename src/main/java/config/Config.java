package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private static Path watchRootPath = Path.of("src", "main", "java");

    private Config(){}

    public static boolean setWatchRootPath(String pathStr) {
        Path tempPath = Path.of(Optional.ofNullable(pathStr).orElse(""));

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
}