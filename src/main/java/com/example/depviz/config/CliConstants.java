package com.example.depviz.config;

public final class CliConstants {

    public static final String MODE_REAL = "real";
    public static final String MODE_TEST = "test";
    public static final String DEFAULT_MODE = MODE_REAL;

    private CliConstants() {
        throw new AssertionError("Utility class must not be instantiated");
    }
}
