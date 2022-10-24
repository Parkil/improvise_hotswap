package hotswap.compiler;

import config.Config;
import dto.ClassFileInfo;
import exception.exception.JavaCompileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtil;
import util.JavaFileUtil;

import javax.tools.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompileJava {

    private final Logger logger = LoggerFactory.getLogger(CompileJava.class);

    public void execCompile(List<Path> javaPathList) throws JavaCompileException {
        if(javaPathList == null || javaPathList.isEmpty()) {
            logger.info("target file list empty");
            return;
        }

        logger.info("java file compile start [java file cnt : {}]", javaPathList.size());
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        try(StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(Config.getTempClassPath())); //class output path
            Iterable<? extends JavaFileObject> compilationUnits =
                    fileManager.getJavaFileObjectsFromPaths(javaPathList);
            compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        }catch(IOException e) {
            throw new JavaCompileException("io error", e);
        }

        List<Diagnostic<? extends JavaFileObject>> diagnosticList = diagnostics.getDiagnostics();

        if(!diagnosticList.isEmpty()) {
            throw new JavaCompileException("java compile error", diagnosticList);
        }

        logger.info("java file compile success [java file cnt : {}]", javaPathList.size());
    }

    public List<ClassFileInfo> findClassPaths(List<Path> javaPathList) {
        return javaPathList.stream().map(this::classFileLambda)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    private ClassFileInfo classFileLambda(Path javaFilePath) {
        String packageStr = JavaFileUtil.getPackage(javaFilePath);
        String classFileName = javaFilePath.getFileName().toString().split("\\.")[0] + ".class";

        List<Path> findResultList = FileUtil.findByFileName(Config.getTempClassPath().toPath(), classFileName);

        if(findResultList.size() != 1) {
            logger.info("invalid class file : {}", classFileName);
            return null;
        }

        return new ClassFileInfo(packageStr, findResultList.get(0));
    }
}