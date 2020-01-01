package com.ishland.ClientStatsSaver.injector.exception;

public class CSNotFoundException extends CSOperationException {

    /**
     * 
     */
    private static final long serialVersionUID = 2432131109478340390L;

    private static final String reason = "Unable to locate ClientStats API class";

    public CSNotFoundException() {
	super(reason);
    }

    public CSNotFoundException(Throwable cause) {
	super(reason, cause);
    }

}
