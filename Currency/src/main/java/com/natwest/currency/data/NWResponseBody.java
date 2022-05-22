package com.natwest.currency.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NWResponseBody<T> {

    private Long traceId;
    private T responseData;
    private NWResponseStatus status;

    public NWResponseBody() {
    }

    public NWResponseBody(final Long traceId, final NWResponseStatus status) {
        this.traceId = traceId;
        this.status = status;
    }
}
