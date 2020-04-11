package com.github.heqiao2010.executor.queue_cached_executor.utils;

public class JsonException extends RuntimeException {
    public JsonException(String s) {
        super(s);
    }

    public JsonException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JsonException(Throwable throwable) {
        super(throwable);
    }

    public JsonException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
