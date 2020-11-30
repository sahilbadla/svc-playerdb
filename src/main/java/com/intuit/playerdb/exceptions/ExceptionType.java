package com.intuit.playerdb.exceptions;

public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    ENTITY_BAD_REQUEST("bad.request"),
    ENTITY_EXCEPTION("exception");

    String value;

    ExceptionType(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
