package com.example.depviz.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public final class CliParser {

    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";


    public Config parse(final String[] args) {
        ParseState state = new ParseState();
        for (int i = 0; i < args.length; i++) {
            i = consume(args, i, state);
        }
        state.validate();
        return state.toConfig();
    }

    private int consume(final String[] args, final int index,
                        final ParseState state) {
        String arg = args[index];
        switch (arg) {
            case "-p":
            case "--package":
                state.packageName = requireValue(args, index, "package");
                return index + 1;
            case "-r":
            case "--repo":
                state.repository = requireValue(args, index, "repo");
                return index + 1;
            case "-m":
            case "--mode":
                state.mode = requireValue(args, index, "mode");
                return index + 1;
            case "-f":
            case "--filter":
                state.filter = requireValue(args, index, "filter");
                return index + 1;
            case "-a":
            case "--ascii":
                state.asciiMode = true;
                return index;
            default:
                throw new IllegalArgumentException("Unknown option: " + arg);
        }
    }

    private String requireValue(final String[] args, final int index,
                                final String optionName) {
        if (index + 1 >= args.length) {
            throw new IllegalArgumentException(
                    "Option --" + optionName + " requires a value");
        }
        return args[index + 1];
    }


    private static final class ParseState {
        private String packageName;
        private String repository;
        private String mode = CliConstants.DEFAULT_MODE;
        private boolean asciiMode;
        private String filter;

        void validate() {
            validatePackageName();
            validateRepository();
            validateMode();
        }

        private void validatePackageName() {
            if (packageName == null || packageName.isBlank()) {
                throw new IllegalArgumentException(
                        "Package name is required (--package)");
            }
        }

        private void validateRepository() {
            if (repository == null || repository.isBlank()) {
                throw new IllegalArgumentException(
                        "Repository is required (--repo)");
            }
            if (CliConstants.MODE_TEST.equals(mode)) {
                validateTestRepository();
            } else {
                validateRealRepository();
            }
        }

        private void validateTestRepository() {
            try {
                Path path = Path.of(repository);
                if (!Files.isRegularFile(path)) {
                    throw new IllegalArgumentException(
                            "Test repository file not found: " + repository);
                }
            } catch (InvalidPathException e) {
                throw new IllegalArgumentException(
                        "Invalid test repository path: " + repository, e);
            }
        }

        private void validateRealRepository() {
            try {
                URI uri = new URI(repository);
                if (!isHttpScheme(uri.getScheme())) {
                    throw new IllegalArgumentException(
                            "Repository URL must use http or https: "
                                    + repository);
                }
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(
                        "Invalid repository URL: " + repository, e);
            }
        }

        private boolean isHttpScheme(final String scheme) {
            return SCHEME_HTTP.equals(scheme) || SCHEME_HTTPS.equals(scheme);
        }

        private void validateMode() {
            if (!CliConstants.MODE_REAL.equals(mode)
                    && !CliConstants.MODE_TEST.equals(mode)) {
                throw new IllegalArgumentException(
                        "Mode must be '" + CliConstants.MODE_REAL + "' or '"
                                + CliConstants.MODE_TEST + "', got: " + mode);
            }
        }

        Config toConfig() {
            return new Config(packageName, repository, mode, asciiMode, filter);
        }
    }
}
