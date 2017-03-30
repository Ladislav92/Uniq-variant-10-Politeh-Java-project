package com.ladislav;

import java.io.*;
import java.util.*;

/**
 * Created by Ladislav on 3/22/2017.
 *
 */

public class UniqProcessor {

    private static UniqProcessor uniqProcessor = new UniqProcessor();

    private UniqParser up;
    private Collection<String> lines = new ArrayList<>();
    private Map<String, Integer> processed;

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

    // TODO finish me !
    // TODO test me !
    // TODO clean code (too much repetition) and optimise me!
    /**
     * First idea
     * For now it reads lines list one by one and puts it in map to prepare output
     * If unique and compress are flagged - will be easy to read it from map
     * If not - it will just read in lines from ArrayList
      */
    public void processData() {

        processed = new HashMap<>();

        // BETTER HERE: if UNIQUE or COMPRESS flagged do: (else just print out)
        for (String line : lines) {

            if (processed.isEmpty()) {
                processed.put(line, 1);
            } else {

                // POSSIBILITY: if ignoring N chars make another Map?

                String temp = line;
                int ignoreTo = up.getIgnoreCharsTo();

                if (!up.isCaseSensitive()) {
                    temp = temp.toLowerCase();
                    if (ignoreTo > 0) {
                        boolean keyExists = false;
                        for (String key : processed.keySet()) {
                            if (temp.substring(0, ignoreTo).equals(key.substring(0, ignoreTo).toLowerCase())) {
                                int oldValue = processed.get(temp);
                                processed.replace(temp, ++oldValue);
                                keyExists = true;
                                break;
                            }
                        }
                        if (!keyExists) {
                            processed.put(temp, 1);
                        }

                    } else {
                        boolean keyExists = false;
                        for (String key : processed.keySet()) {
                            if (temp.equals(key)) {
                                int oldValue = processed.get(temp);
                                processed.replace(temp, ++oldValue);
                                keyExists = true;
                                break;
                            }
                        }
                        if (!keyExists) {
                            processed.put(temp, 1);
                        }
                    }

                } else {
                    if (ignoreTo > 0) {
                        boolean keyExists = false;
                        for (String key : processed.keySet()) {
                            if (temp.substring(0, ignoreTo).equals(key.substring(0, ignoreTo))) {
                                int oldValue = processed.get(temp);
                                processed.replace(temp, ++oldValue);
                                keyExists = true;
                                break;
                            }
                        }
                        if (!keyExists) {
                            processed.put(temp, 1);
                        }
                    } else {
                        if (processed.containsKey(temp)) {
                            int oldValue = processed.get(temp);
                            processed.replace(temp, ++oldValue);
                        } else {
                            processed.put(temp, 1);
                        }
                    }
                }
            }
            // if only unique needed:
            // checkUnique();
            // compress if needed:
            // compress();
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

