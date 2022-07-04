package exception.exception;

public class JavaCompileException extends Exception{
    public JavaCompileException(String msg) {
        super(msg);
    }

    public JavaCompileException(String msg, Throwable e) {
        super(msg, e);
    }
}