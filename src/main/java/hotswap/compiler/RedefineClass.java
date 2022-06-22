package hotswap.compiler;

import config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtil;
import util.JavaFileUtil;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RedefineClass {

    private final Logger logger = LoggerFactory.getLogger(RedefineClass.class);

    public void redefine(List<String> targetNameList, Instrumentation inst) {
        if(!targetNameList.isEmpty()) {
            targetNameList.forEach(targetName -> {
                List<Path> findFileList = FileUtil.findByFileName(Config.WATCH_ROOT_PATH, targetName);

                if(!findFileList.isEmpty()) {
                    logger.info("targetFile compile start");
                    try {
                        new CompileJava().execCompile(findFileList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.info("targetFile compile end");

                    findFileList.forEach(path -> {

                        String classFileName = path.toFile().getName().split("\\.")[0] + ".class";
                        Path classFilePath = Path.of(path.getParent().toString(), classFileName);

                        String packageStr = JavaFileUtil.getPackage(path);

                        logger.info("redefine start class file : {}", classFileName);

                        try {
                            byte[] classByteCode = Files.readAllBytes(classFilePath);

                            String forName = packageStr + ("".equals(packageStr.trim()) ? "" : ".") + path.toFile().getName().split("\\.")[0];
                            logger.info("class forName : {}", forName);

                            Class targetClass = Class.forName(forName);
                            logger.info("target Class : {}",targetClass);
                            ClassDefinition cd = new ClassDefinition(targetClass, classByteCode);
                            inst.redefineClasses(cd);

                            Files.delete(classFilePath);
                        } catch (IOException | ClassNotFoundException | UnmodifiableClassException e) {
                            e.printStackTrace();
                        }

                        logger.info("redefine end");
                    });
                }
            });
        }
    }
}
