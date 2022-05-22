package com.natwest.currency.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NWApplicationException extends RuntimeException {

    private final String errorMessage;
    private final Throwable e;
    private long errorCode;

    public NWApplicationException(final long errorCode, final String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    public NWApplicationException(final long errorCode, final String errorMessage, final Throwable e) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.e = e;
    }
}
