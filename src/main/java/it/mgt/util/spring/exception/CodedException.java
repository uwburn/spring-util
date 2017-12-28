package it.mgt.util.spring.exception;

public class CodedException extends RuntimeException {

    private String code;

    public CodedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CodedException(ErrorDefinition errorDefinition) {
        super(errorDefinition.getMessage());
        this.code = errorDefinition.getCode();
    }

    public String getCode() {
        return code;
    }

}
