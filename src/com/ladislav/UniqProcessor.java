package com.ladislav;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Unique processor is used for combining sequences of identical consecutive
 * lines in the file into one based on arguments passed in command line:
 * <p>
 * -file specifies the name of the input file. If the parameter is missing, output on the console is needed
 * -i flag means that comparison of the lines should not be case sensitive.
 * -s N flag indicates that the string comparison should ignore first N characters of each line.
 * -u means that, as a result, only unique lines should be taken.
 * -c flag means that in front of every line of output program should output the number of lines that were replaced
 * (i.e. if in the input contains 2 same lines in the output should be one with the prefix "2").
 * <p>
 * Command line: uniq[-i][-u][-c][-s num][-o file][file]
 *
 * @author Ladislav on 3/22/2017.
 */

public class UniqProcessor implements IProcessor {

    private static UniqProcessor uniqProcessor = new UniqProcessor();

    private UniqParser up;
    private Scanner input;
    private PrintStream output;
    private List<EntryPair<String, Integer>> processed;

    private UniqProcessor() {
    }

    public static UniqProcessor getInstance() {
        return uniqProcessor;
    }

    /**
     * Initialises processor and input output setup.
     *
     * @param arguments with directives for data processing, taken from command line.
     */
    public void init(String[] arguments) {
        up = new UniqParser();
        up.parseArgs(arguments);
        setupIO();
    }

    /**
     * Private method, initialises I/O objects.
     * Uses Scanner class for input (from file or console) and
     * PrintStream for output (to file or console) depending on args.
     */
    private void setupIO() {

        //Input setup
        if (up.isRiddenFromFile()) {
            try {
                input = new Scanner(new BufferedReader(new FileReader(up.getInputFileName())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            input = new Scanner(System.in);
        }

        //Output setup
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

    // TODO Unit test me !

    /**
     * Processes data and stores its result in a list of key-value pairs.
     * EntryPair(String, Integer) holds original input and number of its sequential occurrences
     * It is preferred to use this method only if needed direct access in between process and output to processed data,
     * since it stores results of processing in the memory.
     *
     * @return ArrayList of processed data
     * @throws IllegalStateException if Scanner is closed
     */
    public List<EntryPair<String, Integer>> process() {

        if (input == null || output == null) {
            throw new IllegalStateException();
        }

        processed = new ArrayList<>();

        while (input.hasNextLine()) {

            String line = input.nextLine();

            if (processed.isEmpty()) {
                processed.add(new EntryPair<>(line, 1));
            } else {

                EntryPair<String, Integer> previous = processed.get(processed.size() - 1);
                String previousTemp = previous.getKey();
                String temp = line;

                if (up.getIgnoredCharsNum() > previousTemp.length()) {
                    previousTemp = "";
                } else {
                    previousTemp = previousTemp.substring(up.getIgnoredCharsNum());
                }

                if (up.getIgnoredCharsNum() > temp.length()) {
                    temp = "";
                } else {
                    temp = line.substring(up.getIgnoredCharsNum());
                }

                if (!up.isCaseSensitive()) {
                    previousTemp = previousTemp.toLowerCase();
                    temp = temp.toLowerCase();
                }

                if (temp.equals(previousTemp)) {
                    previous.setValue(previous.getValue() + 1);
                } else {
                    processed.add(new EntryPair<>(line, 1));
                }
            }
        }
        return processed;
    }

    //TODO Unit test me !

    /**
     * Processes and immediately outputs the given data by calling helper method output(String line, int occurrences)
     * It is preferable to use this method over methods process() and output() (if not needed access between those processes)
     * It is faster and takes less memory,  since it doesn't store processed data in a List, like method process() does.
     *
     * @throws IllegalStateException if Scanner or PrintStream are closed
     */
    public void processAndOutput() {

        String previous = input.hasNextLine() ? input.nextLine() : "";
        int counter = 1;

        if (!input.hasNextLine()) {
            output(previous, counter);
        }

        while (input.hasNextLine()) {

            String line = input.nextLine();
            String temp = line;
            String tempPrevious = previous;

            if (up.getIgnoredCharsNum() > tempPrevious.length()) {
                tempPrevious = "";
            } else {
                tempPrevious = tempPrevious.substring(up.getIgnoredCharsNum());
            }

            if (up.getIgnoredCharsNum() > temp.length()) {
                temp = "";
            } else {
                temp = line.substring(up.getIgnoredCharsNum());
            }

            if (!up.isCaseSensitive()) {
                temp = temp.toLowerCase();
                tempPrevious = tempPrevious.toLowerCase();
            }

            if (temp.equals(tempPrevious)) {
                counter++;

            } else {
                output(previous, counter);
                previous = line;
                counter = 1;
            }
            if (!input.hasNextLine()) {
                output(previous, counter);
            }
        }
    }

    /**
     * Private helper method for output. Checks for unique and counted flag.
     *
     * @param line       of processed input.
     * @param occurrence of processed line.
     * @throws IllegalStateException when PrintStream is closed
     */
    private void output(String line, int occurrence) {

        if (up.isUnique() && occurrence != 1 || line.length() == 0) {
            return;
        }
        if (up.isCounted()) {
            output.println(occurrence + " : " + line);
        } else {
            output.print(line + "\n");
        }
    }

    //TODO unit test me!

    /**
     * Outputs the processed data stored inside UniqProcessor in the List of key-value pairs.
     * This is preferred method to use if no changes are made to processed data.
     *
     * @throws NullPointerException if data isn't processed.
     */
    public void output() {
        if (processed == null) {
            throw new NullPointerException("Data has to be processed first.");
        }
        for (EntryPair<String, Integer> original : processed) {
            output(original.getKey(), original.getValue());
        }
    }

    //TODO Unit test me !

    /**
     * Gives possibility to output data that user processed and changed afterwards.
     * If processed data is not changed method without any parameters should be used.
     *
     * @param list list of key-value pairs of processed data
     * @throws NullPointerException if passed list is null
     */
    public void output(List<EntryPair<String, Integer>> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        for (EntryPair<String, Integer> pair : list) {
            output(pair.getKey(), pair.getValue());
        }
    }

    /**
     * Closes input and output streams.
     * Calling this method is mandatory after finishing processing and output,
     * since file may stay locked down if they stay open.
     *
     * @throws NullPointerException if any of streams is set to null.
     */
    public void destroy() {
        if (input == null || output == null) {
            throw new NullPointerException();
        }
        input.close();
        output.close();
        input = null;
        output = null;
        up = null;
    }
}