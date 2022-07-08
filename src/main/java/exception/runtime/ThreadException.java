package exception.runtime;

public class ThreadException extends RuntimeException{
    public ThreadException(Throwable e) {
        super(e);
    }
}