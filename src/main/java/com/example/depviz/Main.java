package com.example.depviz;

import com.example.depviz.config.CliParser;
import com.example.depviz.config.Config;

public final class Main {

    private static final int EXIT_INVALID_ARGS = 1;

    private Main() {
        throw new AssertionError("Entry point must not be instantiated");
    }


    public static void main(final String[] args) {
        try {
            Config config = new CliParser().parse(args);
            System.out.println(config);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(EXIT_INVALID_ARGS);
        }
    }
}
