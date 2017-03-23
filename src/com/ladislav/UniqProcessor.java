package com.ladislav;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by Ladislav on 3/22/2017.
 */

public class UniqProcessor {

    // TODO remove static fields

    private static UniqProcessor uniqProcessor = new UniqProcessor();

    private static UniqParser up;
    private static File outputFile = null;
    private static File inputFile = null;
    private static Scanner sc = null;

    private static Collection<String> lines = new ArrayList<>();
    private static Collection<String> processed = new ArrayList<>();

    private UniqProcessor() {
    }

    public static UniqProcessor getInstance() {
        return uniqProcessor;
    }

    protected void process(String[] arguments) {
        up = new UniqParser();
        up.parseArgs(arguments);
        read();
        processData();
        output();
    }


    // TODO Buffered reader ili iz fajla ili iz skenera !

    //TODO finish read() method implementation
    private static void read() {
        if (up.isRiddenFromFile()) {

            // FIXME get file path, and make File objects from it!
//            Path filePath = FileSystems.getDefault().getPath(up.getInputFileName());
            inputFile = new File(up.getInputFileName());

            try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {

                while (reader.readLine() != null) {
                    lines.add(reader.readLine());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            sc = new Scanner(System.in);
            //TODO while input is not blank line - read it in list
        }
    }


    // TODO finish method processData()

    private static void processData() {

    }

    //TODO finish method
    private static void output() {
        if (up.isOutputedToFile()) {
            outputFile = new File(up.getOutputFileName());
            // TODO Write results from list (or somewhere else) to file

        } else {
            //TODO print results to console
        }
    }

/*
    private class UniqParser {

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
    }*/
}
