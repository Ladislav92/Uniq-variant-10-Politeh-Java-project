package com.ladislav;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by Ladislav on 3/22/2017.
 */

public class UniqProcessor {

    private static UniqProcessor uniqProcessor = new UniqProcessor();

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
    // TODO test

    public void read() {
        if (up.isRiddenFromFile()) {
            inputFile = new File(up.getInputFileName());
            String temp;

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                while ((temp = reader.readLine()) != null) {
                    lines.add(temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sc = new Scanner(System.in);
            String temp;

            System.out.println("No file to read from provided in arguments.");
            System.out.println("Enter lines you want to process from console! To quit, enter blank line.");

            while (!(temp = sc.nextLine()).equals("")) {
                lines.add(temp);
            }
        }
    }

    // TODO finish method processData()
    public void processData() {

    }

    //TODO test
    public void output() {
        if (up.isOutputedToFile()) {
            outputFile = new File(up.getOutputFileName());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                for (String line : processed) {
                    writer.write(line);
                    writer.newLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            processed.forEach(System.out::println);
        }
    }
}
