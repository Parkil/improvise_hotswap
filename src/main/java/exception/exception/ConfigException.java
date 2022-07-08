package exception.exception;

public class ConfigException extends Exception{
    public ConfigException(String msg) {
        super(msg);
    }

    public ConfigException(Throwable e) {
        super(e);
    }
}