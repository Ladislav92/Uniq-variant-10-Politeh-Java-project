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


    private  static UniqProcessor uniqProcessor = new UniqProcessor();

    private UniqParser up;
    private File outputFile = null;  // Convert to local ?
    private File inputFile = null;   // Convert to local ?
    private Scanner sc = null;

    private Collection<String> lines = new ArrayList<>();
    private Collection<String> processed = new ArrayList<>();

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
    private void read() {
        if (up.isRiddenFromFile()) {

            // FIXME get file path, and make File objects from it:
            // Path filePath = FileSystems.getDefault().getPath(up.getInputFileName());

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
    private void processData() {

    }

    //TODO finish method
    private void output() {
        if (up.isOutputedToFile()) {
            outputFile = new File(up.getOutputFileName());
            // TODO Write results from list (or somewhere else) to file

        } else {
            //TODO print results to console
        }
    }
}
