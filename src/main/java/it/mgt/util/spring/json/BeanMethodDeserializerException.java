package it.mgt.util.spring.json;

public class BeanMethodDeserializerException extends RuntimeException {

    public BeanMethodDeserializerException(String message) {
        super(message);
    }

    public BeanMethodDeserializerException(Throwable cause) {
        super(cause);
    }

    public BeanMethodDeserializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
