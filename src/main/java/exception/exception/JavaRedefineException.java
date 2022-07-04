package exception.exception;

public class JavaRedefineException extends Exception{
    public JavaRedefineException(String msg) {
        super(msg);
    }

    public JavaRedefineException(Throwable e) {
        super(e);
    }
}