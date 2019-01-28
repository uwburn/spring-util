package it.mgt.util.spring.exception;

public class CodedException extends RuntimeException {

    private String code;

    public CodedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CodedException(String code, String message, Exception cause) {
        super(message, cause);
        this.code = code;
    }

    public CodedException(ErrorDefinition errorDefinition) {
        super(errorDefinition.getMessage());
        this.code = errorDefinition.getCode();
    }

    public CodedException(ErrorDefinition errorDefinition, Exception cause) {
        super(errorDefinition.getMessage(), cause);
        this.code = errorDefinition.getCode();
    }

    public String getCode() {
        return code;
    }

}
