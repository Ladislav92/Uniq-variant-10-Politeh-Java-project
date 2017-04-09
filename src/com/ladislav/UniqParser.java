package com.ladislav;

/**
 * UniqParser is helper class to parse arguments given for UniqProcessor
 * It has parsArgs(String[] args) method provided and getters and setters for fields that contain information given in arguments.
 * Shouldn't be used without UniqProcessor
 */

class UniqParser implements IParser {

    private String inputFileName = "";
    private String outputFileName = "";

    private int ignoreCharsTo = 0;

    private boolean outputedToFile = false;
    private boolean riddenFromFile = false;
    private boolean caseSensitive = true;
    private boolean unique = false;
    private boolean counted = false;

    /**
     * Parses arguments for UniqProcessor
     *
     * @param args arguments provided for input processing in class UniqProcessor
     */
    public void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    caseSensitive = false;
                    break;
                case "-u":
                    unique = true;
                    break;
                case "-c":
                    counted = true;
                    break;
                case "-s":
                    ignoreCharsTo = Integer.parseInt(args[++i]);
                    break;
                case "-o":
                    outputedToFile = true;
                    outputFileName = args[++i];
                    break;
                default:
                    inputFileName = args[i];
                    riddenFromFile = inputFileName.length() > 0;
                    break;
            }
        }
    }

    String getInputFileName() {
        return inputFileName;
    }

    String getOutputFileName() {
        return outputFileName;
    }

    int getIgnoredCharsNum() {
        return ignoreCharsTo;
    }

    boolean isOutputedToFile() {
        return outputedToFile;
    }

    boolean isRiddenFromFile() {
        return riddenFromFile;
    }

    boolean isCaseSensitive() {
        return caseSensitive;
    }

    boolean isUnique() {
        return unique;
    }

    public boolean isCounted() {
        return counted;
    }
}