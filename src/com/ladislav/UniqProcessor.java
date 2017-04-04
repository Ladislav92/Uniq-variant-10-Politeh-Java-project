package com.ladislav;

import java.io.*;
import java.util.*;

/**
 * Created by Ladislav on 3/22/2017.
 */

public class UniqProcessor {

    private static UniqProcessor uniqProcessor = new UniqProcessor();

    private UniqParser up;
    private List<String> lines = new ArrayList<>();
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

        if (!up.isUnique() && !up.isCompressed()) {
            return;
        }
        processed = new LinkedHashMap<>();

        for (String line : lines) {

            KVPair<String, Integer> original = new KVPair<>(line, 1);
            String temp = line.substring(up.getIgnoredCharsNum());

            if (!up.isCaseSensitive()) {
                temp = temp.toLowerCase();
            }

            if (processed.containsKey(temp)) {
                KVPair<String, Integer> kvp = processed.get(temp);
                kvp.setValue(kvp.getValue() + 1);
            } else {
                processed.put(temp, original);
            }
        }
    }

    //TODO implement me!
    /**
     * Second idea, improved readability
     */
    public void output() {

        if (processed == null) {
            // output what is in to file or console
            return;
        }

        for (KVPair<String, Integer> original : processed.values()) {
            String line = original.getKey();
            int occurrence = original.getValue();

            if (up.isUnique() && occurrence != 1) {
                continue;
            }

            if (up.isCompressed()) {
                line = occurrence + " " + line;
            }

            // output to file or console
        }
    }
}



