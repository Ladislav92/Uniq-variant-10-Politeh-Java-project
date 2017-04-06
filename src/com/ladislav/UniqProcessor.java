package com.ladislav;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ladislav on 3/22/2017.
 */

public class UniqProcessor {

    private static UniqProcessor uniqProcessor = new UniqProcessor();

    private UniqParser up;
    private Scanner input;
    private PrintStream output;
    private List<KVPair<String, Integer>> processed;

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

        processed = new ArrayList<>();

        while (input.hasNextLine()){

            String line = input.nextLine();

            if (!processed.isEmpty()) {
                processed.add(new KVPair<>(line, 1));
            } else {

                KVPair<String, Integer> lastInput = processed.get(processed.size() - 1);

                // FIXME check when flag for ignore is greater than string size
                String lastLine = lastInput.getKey().substring(up.getIgnoredCharsNum());
                String temp = line.substring(up.getIgnoredCharsNum());

                if (!up.isCaseSensitive()) {
                    lastLine = lastLine.toLowerCase();
                    temp = temp.toLowerCase();
                }

                if (lastLine.equals(temp)) {
                    lastInput.setValue(lastInput.getValue() + 1);
                } else {
                    processed.add(new KVPair<>(line, 1));
                }
            }
        }
    }

    //TODO test me !
    public void processAndOutput(){

        String previous = input.hasNextLine() ? input.nextLine() : "";
        int counter = 1;

        while (input.hasNextLine()) {

            String line = input.nextLine();

            //FIXME check if ignored is greater than string length
            String temp = line.substring(up.getIgnoredCharsNum());
            String tempPrevious = previous.substring(up.getIgnoredCharsNum());

            if (!up.isCaseSensitive()) {
                temp = temp.toLowerCase();
                tempPrevious = tempPrevious.toLowerCase();
            }

            if (temp.equals(tempPrevious)) {
                counter++;
            } else {
                output(previous, counter);
            }
        }

    }

    //TODO test me!
    /**
     * Second idea, improved readability
     */
    public void output() {

        if (processed == null) {
            throw new NullPointerException("Data has to be processed first.");
        }

        for (KVPair<String, Integer> original : processed) {

            String line = original.getKey();
            int occurrence = original.getValue();

            if (up.isUnique() && occurrence != 1) {
                continue;
            }

            if (up.isCounted()) {
                line = occurrence + " : " + line;
            }
            output.println(line);
        }
    }


    //TODO test me !
    private void output(String line, int occurrence){

        if (up.isUnique() && occurrence != 1) {
                return;
        }
        if (up.isCounted()) {
            output.println(occurrence + " : " + line);
        } else {
            output.println(line);
        }

    }
}

/**
 *
 * Input:
 *
 * aaa
 * bbb
 * ccc
 * ccc
 * bbb
 *
 * Without flags:
 *
 * aaa
 * bbb
 * ccc
 * bbb
 *
 * Unique:
 *
 * aaa
 * bbb
 * bbb
 *
 * Count:
 *
 * 1 aaa
 * 1 bbb
 * 2 ccc
 * 1 bbb
 *
 * Unique and Count:
 *
 * 1 aaa
 * 1 bbb
 * 1 bbb
 *
 */
