package com.ishland.ClientStatsSaver.injector.exception;

public class CSOperationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1551146992913489572L;

    public CSOperationException() {
	super();
    }

    public CSOperationException(String s) {
	super(s);
    }

    public CSOperationException(String s, Throwable cause) {
	super(s, cause);
    }
}
