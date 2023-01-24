package com.quitter.bagr.core;

import com.quitter.bagr.view.Status;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class bagrException extends Exception {
    public enum Reason {
        ALL_OK,
        INTERNAL_SERVER_ERROR,
        DATABASE_ERROR,
        NOT_AUTHORISED,
        BAD_REQUEST,
        NOT_FOUND
    }
    private int code;
    private String message;
    private Reason reason;
    public Status toStatus(){
        return Status.builder()
                .message(message).code(code)
                .reason(reason.toString()).build();

    }
}
