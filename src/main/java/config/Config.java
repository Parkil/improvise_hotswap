package config;

import exception.exception.ConfigException;
import hotswap.compiler.RedefineClass;
import util.FileUtil;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Config {

    private static Path watchRootPath = Path.of("src", "main", "java");

    private static final File tempClassPath = new File("build/hotswap_temp");

    private static RedefineClass redefineClass;

    private Config(){}

    public static void setWatchRootPath(String pathStr) throws ConfigException {
        Path tempPath = Path.of(Optional.ofNullable(pathStr).orElse(""));

        if(!Files.exists(tempPath)) {
            throw new ConfigException(String.format("path[%s] is invalid file path. hotswap is not running", tempPath));
        }

        if(FileUtil.findByFileName(tempPath, "*.java").isEmpty()) {
            throw new ConfigException(String.format("path[%s] does not contain java file. hotswap is not running", tempPath));
        }

        watchRootPath = tempPath;
    }

    public static void createTempClassPath() throws ConfigException{
        if(!tempClassPath.exists()) {
            boolean result = tempClassPath.mkdirs();
            if(!result) {
                throw new ConfigException(String.format("temp class directory not created. path[%s]", tempClassPath.getPath()));
            }
        }
    }

    public static Path getWatchRootPath() {
        return watchRootPath;
    }

    public static File getTempClassPath() { return tempClassPath; }

    public static void setRedefineClass(Instrumentation inst) {
        redefineClass = new RedefineClass(inst);
    }

    public static RedefineClass getRedefineClass() throws ConfigException {
        if(redefineClass == null) {
            throw new ConfigException("RedefineClass not initialized");
        }
        return redefineClass;
    }
}