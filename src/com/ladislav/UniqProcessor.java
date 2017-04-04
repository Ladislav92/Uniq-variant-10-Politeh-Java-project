package com.ladislav;

import java.io.*;
import java.util.*;

/**
 * Created by Ladislav on 3/22/2017.
 */

public class UniqProcessor {

    private static UniqProcessor uniqProcessor = new UniqProcessor();

    private UniqParser up;
    private Scanner input;
    private PrintStream output;
    private Map<String, KVPair<String, Integer>> processed;

    private UniqProcessor() {
    }

    public static UniqProcessor getInstance() {
        return uniqProcessor;
    }

    protected void process(String[] arguments) {
        up = new UniqParser();
        up.parseArgs(arguments);
        setupIO();
        processData();
        output();
    }

    // TODO test

    public void setupIO() {

        if (up.isRiddenFromFile()) {
            try {
                input = new Scanner(new BufferedReader(new FileReader(up.getInputFileName())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            input = new Scanner(System.in);
        }

        if (up.isOutputedToFile()) {
            File f = new File(up.getOutputFileName());
            try {
                output = new PrintStream(new FileOutputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            output = System.out;
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

        while (input.hasNextLine()){

            String line = input.nextLine();
            
            String temp = line.substring(up.getIgnoredCharsNum());

            if (!up.isCaseSensitive()) {
                temp = temp.toLowerCase();
            }

            if (processed.containsKey(temp)) {
                KVPair<String, Integer> kvp = processed.get(temp);
                kvp.setValue(kvp.getValue() + 1);
            } else {
                processed.put(temp, new KVPair<>(line, 1));
            }
        }
    }

    //TODO implement me!
    /**
     * Second idea, improved readability
     */
    public void output() {

        if (processed == null) {
            while (input.hasNextLine()) {
                output.println(input.nextLine());
            }
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

            output.println(line);
        }
    }
}



