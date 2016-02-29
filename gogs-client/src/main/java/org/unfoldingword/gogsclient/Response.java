package org.unfoldingword.gogsclient;

/**
 * Holds the response from the api
 */
public class Response {
    public final int code;
    public final String data;
    public final Exception exception;

    public Response(int responseCode, String responseData, Exception exception) {
        this.code = responseCode;
        this.data = responseData;
        this.exception = exception;
    }

    @Override
    public String toString() {
        return this.data;
    }
}
