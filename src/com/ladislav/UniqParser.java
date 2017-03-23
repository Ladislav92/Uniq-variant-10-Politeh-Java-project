package com.ladislav;

/**
 * Created by Ladislav on 3/22/2017.
 */

class UniqParser {

    private String inputFileName = "";
    private String outputFileName = "";

    private int ignoreCharsTo = -1;         // -s

    private boolean outputedToFile = false;   // -o
    private boolean riddenFromFile = false;
    private boolean caseSensitive = true;   // -i
    private boolean unique = false;         // -u
    private boolean compressed = false;     // -c

    void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    caseSensitive = false;
                    break;
                case "-u":
                    unique = true;
                    break;
                case "-c":
                    compressed = true;
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

    int getIgnoreCharsTo() {
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

    public boolean isCompressed() {
        return compressed;
    }
}