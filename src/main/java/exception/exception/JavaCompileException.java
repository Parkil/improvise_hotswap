package exception.exception;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;

public class JavaCompileException extends Exception{

    private final List<Diagnostic<? extends JavaFileObject>> diagnosticList = new ArrayList<>();

    public JavaCompileException(String msg, List<Diagnostic<? extends JavaFileObject>> diagnosticList) {
        super(msg);
        this.diagnosticList.addAll(diagnosticList);
    }

    public JavaCompileException(String msg, Throwable e) {
        super(msg, e);
    }

    public List<Diagnostic<? extends JavaFileObject>> getDiagnosticList() {
        return diagnosticList;
    }
}