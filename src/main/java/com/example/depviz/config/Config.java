package com.example.depviz.config;
public final class Config {

    private final String packageName;
    private final String repository;
    private final String mode;
    private final boolean asciiMode;
    private final String filter;

    public Config(final String packageName, final String repository,
                  final String mode, final boolean asciiMode,
                  final String filter) {
        this.packageName = packageName;
        this.repository = repository;
        this.mode = mode;
        this.asciiMode = asciiMode;
        this.filter = filter;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getRepository() {
        return repository;
    }

    public String getMode() {
        return mode;
    }

    public boolean isAsciiMode() {
        return asciiMode;
    }

    public String getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return "packageName: " + packageName
                + System.lineSeparator()
                + "repository: " + repository
                + System.lineSeparator()
                + "mode: " + mode
                + System.lineSeparator()
                + "asciiMode: " + asciiMode
                + System.lineSeparator()
                + "filter: " + (filter == null ? "(not set)" : filter);
    }
}