package com.ladislav;

/**
 * Created by Ladislav on 3/22/2017.
 */

// TODO Make inner class of UniqProcessor ?!
    // problem here: Unit testing much harder
    // maybie just put two classes in same package and make UniqParser protected?


class UniqParser {

    private String inputFileName = "";
    private String outputFileName = "";
    private int ignoreCharsTo = -1;         // -s
    private boolean outputedToFile = false;   // -o

    private boolean riddenFromFile = false;
    private boolean caseSensitive = true;   // -i
    private boolean unique = false;         // -u
    private boolean compressed = false;     // -c

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

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public int getIgnoreCharsTo() {
        return ignoreCharsTo;
    }

    public boolean isOutputedToFile() {
        return outputedToFile;
    }

    public boolean isRiddenFromFile() {
        return riddenFromFile;
    }
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public boolean isUnique() {
        return unique;
    }

    public boolean isCompressed() {
        return compressed;
    }
}