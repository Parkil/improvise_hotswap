package exception.runtime;

public class GlobalException extends RuntimeException{
    public GlobalException(Exception e) {
        super(e);
    }
}