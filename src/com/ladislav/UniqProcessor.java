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

    // TODO everything with scanner?
    // TODO test

    public void read() {
        if (up.isRiddenFromFile()) {
            File inputFile = new File(up.getInputFileName());
            String temp;

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                while ((temp = reader.readLine()) != null) {
                    lines.add(temp);
                }
            } catch (IOException e) {
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
     *
     */
    public void processData() {

        if (up.isUnique() || up.isCompressed()) {

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
    public void output() {

        if (up.isOutputedToFile()) {
            File outputFile = new File(up.getOutputFileName());
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
//                for (String line : processed) {
//                    writer.write(line);
//                    writer.newLine();
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else {
//            processed.forEach(System.out::println);
        }
    }
}



