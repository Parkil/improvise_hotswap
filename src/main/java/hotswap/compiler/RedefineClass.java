package hotswap.compiler;

import config.Config;
import dto.ClassFileInfo;
import exception.exception.JavaCompileException;
import exception.exception.JavaRedefineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtil;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RedefineClass {

    private final Logger logger = LoggerFactory.getLogger(RedefineClass.class);

    private final CompileJava compileJava;

    private final Instrumentation inst;

    public RedefineClass(Instrumentation inst) {
        this.compileJava = new CompileJava();
        this.inst = inst;
    }

    public void execRedefine(List<String> classNameList) throws JavaRedefineException {
        classNameList = Optional.ofNullable(classNameList).orElse(Collections.emptyList());

        for(String className : classNameList) {
            List<Path> findFileList = FileUtil.findByFileName(Config.getWatchRootPath(), className);

            try {
                compileJava.execCompile(findFileList);
            } catch (JavaCompileException e) {
                throw new JavaRedefineException(e);
            }

            redefineClass(findFileList);
        }
    }

    private void redefineClass(List<Path> javaFilePathList) throws JavaRedefineException{
        List<ClassFileInfo> classFileInfoList = compileJava.findClassPaths(javaFilePathList);

        for(ClassFileInfo classFileInfo : classFileInfoList) {
            try {
                byte[] classByteCode = Files.readAllBytes(classFileInfo.getClassFilePath());

                String forName = classFileInfo.getPackageName()
                        + ("".equals(classFileInfo.getPackageName() .trim()) ? "" : ".")
                        + classFileInfo.getClassFilePath().toFile().getName().split("\\.")[0];

                logger.info("class forName : {}", forName);

                Class<?> targetClass = Class.forName(forName);
                logger.info("target Class : {}",targetClass);
                ClassDefinition cd = new ClassDefinition(targetClass, classByteCode);
                inst.redefineClasses(cd);

                Files.delete(classFileInfo.getClassFilePath());
            } catch (IOException | ClassNotFoundException | UnmodifiableClassException e) {
                throw new JavaRedefineException(e);
            }
        }
    }
}