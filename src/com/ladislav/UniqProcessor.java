package com.ladislav;

import java.io.*;
import java.util.*;

/**
 * Created by Ladislav on 3/22/2017.
 */

public class UniqProcessor {

    private static UniqProcessor uniqProcessor = new UniqProcessor();

    private UniqParser up;
    private Collection<String> lines = new ArrayList<>();
    private Map<String, KVPair<String, Integer>> processed;

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

    // TODO test

    public void read() {

        if (up.isRiddenFromFile()) {
            File inputFile = new File(up.getInputFileName());

            try (Scanner sc = new Scanner(new BufferedReader(new FileReader(inputFile)))) {
                while (sc.hasNext()) {
                    lines.add(sc.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Scanner sc = new Scanner(System.in);
            String temp;

            System.out.println("No file to read from provided in arguments.");
            System.out.println("Enter lines you want to process from console! To quit, enter blank line.");

            while (!(temp = sc.nextLine()).equals("")) {
                lines.add(temp);
            }
        }
    }

    // TODO test me !

    /**
     * Second idea (probably final)
     * Processes data only if unique or compressed flagged
     * Stores comparision string as map key and original
     * and number of occurrences in KVPair (key-value pair) object
     */
    public void processData() {

        boolean unique = up.isUnique();
        boolean compressed = up.isCompressed();

        if (unique || compressed) {

            processed = new LinkedHashMap<>();
            boolean caseInsensitive = !up.isCaseSensitive();
            int ignoredCharacters = up.getIgnoredCharsNum();

            for (String line : lines) {

                KVPair<String, Integer> original = new KVPair<>(line, 1);
                String temp = caseInsensitive ? line.toLowerCase() : line;
                temp = temp.substring(ignoredCharacters);

                if (processed.containsKey(temp)) {
                    KVPair<String, Integer> keyValuePair = processed.get(temp);
                    int oldValue = keyValuePair.getValue();
                    keyValuePair.setValue(++oldValue);
                } else {
                    processed.put(temp, original);
                }
            }


        }
    }

        //TODO implement me!

    /**
     * First idea
     */
    public void output() {

        File outputFile = up.isOutputedToFile() ? new File(up.getOutputFileName()) : null;

        boolean unique = up.isUnique();
        boolean compressed = up.isCompressed();
        String temp = "";

        if (processed != null) {

            for (KVPair<String, Integer> original : processed.values()) {
                String line = original.getKey();
                int occurrence = original.getValue();

                if (unique) {
                    temp = occurrence == 1 ? line : "";
                }

                if (compressed) {
                    if (temp.length() > 0) {
                        temp = occurrence + " " + temp;
                    } else {
                        temp = "";
                    }
                }
                if (temp.length() > 0) {
                    if (outputFile != null) {
                        outputToFile(temp, outputFile);
                    } else {
                        System.out.println(temp);
                    }
                }
            }
        } else {
            for (String line : lines) {
                if (outputFile != null) {
                    outputToFile(line, outputFile);
                } else {
                    System.out.println(line);
                }
            }

        }
    }

    

    private void outputToFile(String s, File fileName) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



