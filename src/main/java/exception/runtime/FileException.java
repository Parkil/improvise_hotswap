package exception.runtime;

public class FileException extends RuntimeException{
    public FileException(Throwable e) {
        super(e);
    }
}