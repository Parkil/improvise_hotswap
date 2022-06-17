package hotswap.compiler;

import javax.tools.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CompileJava {
    public List<Diagnostic<? extends JavaFileObject>> execCompile(List<Path> javaPathList) throws IOException {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        try(StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits =
                    fileManager.getJavaFileObjectsFromPaths(javaPathList);
            compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        }catch(IOException e) {
            throw new IOException(e);
        }

        return diagnostics.getDiagnostics();
    }
}
