package hotswap.compiler;

import config.Config;

import javax.tools.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/*
    역할 : 인자로 들어온 java 파일을 compile 하는것
    책임 : java file 을 compile 하고 class file 리스트를 전달
 */
public class CompileJava {
    public List<Diagnostic<? extends JavaFileObject>> execCompile(List<Path> javaPathList) throws IOException {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        try(StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(Config.getTempClassPath()));
            Iterable<? extends JavaFileObject> compilationUnits =
                    fileManager.getJavaFileObjectsFromPaths(javaPathList);
            compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        }catch(IOException e) {
            throw new IOException("java compile error", e);
        }

        return diagnostics.getDiagnostics();
    }
}
