package com.ladislav;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Ladislav on 4/6/2017.
 */

public class UniqProcessorTest extends TestCase {

    @Test
    public void testProcessAndOutput() throws Exception {

        /*
         * FIRST test case: from file to console
         * All flags set except -o fileName flag
         */

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String[] arguments = {"-i", "-u", "-s", "2", "-c", "input_file.txt"};
        String[] expectedResults = {"1 : FirstOne", "1 : aaaa", "1 : aaaa", "1 : oooo", "1 : LastOne"};

        ProcessorClient processor = new ProcessorClient(UniqProcessor.getInstance());
        processor.init(arguments);
        processor.processAndOutput();

        String[] realOut = outputStream.toString().split("\n");
        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], realOut[i].trim());
        }
        outputStream.reset();

        /*
         * SECOND test case: from file to file
         * All flags set
         */
        arguments = new String[]{"-i", "-u", "-s", "2", "-c", "-o", "output_file.txt", "input_file.txt"};
        expectedResults = new String[]{"1 : FirstOne", "1 : aaaa", "1 : aaaa", "1 : oooo", "1 : LastOne"};

        processor = new ProcessorClient(UniqProcessor.getInstance());
        processor.init(arguments);
        processor.processAndOutput();
        processor.closeIO();

        Path p = Paths.get("output_file.txt");
        File testFile = p.toFile();

        try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {

            for (String anExpectedResult : expectedResults) {
                assertEquals(anExpectedResult, br.readLine().trim());
            }
        } finally {
            Files.delete(p);
        }

        /*
         *  THIRD test case: from console to file
         *  Flags set: -c, -s 2, -i
         */
        String testInput = "FirstOne\naaaa\nbbbb\nbbbb\naaaa\ncccc\nCCcc\nabFF\nccFF\noooo\nzzzz\nzzzz\n" +
                "test\ntest\ntest\nTest\nTesT\ncd\nab\nLastOne\n";

        arguments = new String[]{"-i", "-s", "2", "-c"};
        expectedResults = new String[]{"1 : FirstOne", "1 : aaaa", "2 : bbbb", "1 : aaaa",
                "2 : cccc", "2 : abFF", "1 : oooo", "2 : zzzz", "5 : test", "2 : cd", "1 : LastOne"};

        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);

        processor = new ProcessorClient(UniqProcessor.getInstance());
        processor.init(arguments);
        processor.processAndOutput();
        processor.closeIO();

        realOut = outputStream.toString().split("\n");

        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], realOut[i].trim());
        }

        inputStream.reset();
        outputStream.reset();
        /*
         *  FOURTH test case: from console to console
         *  Note: I/O tested. Need to test few different cases of args and input
         *  With different input and combination
         *  For simplicity, testing will go from console to console
         *  Args: -i only
         */
        testInput = "Quick\nQUICK\nBrown\nBROWN\nFox\nFOX\nJumps\nJUMPS\nOver\nOVER\nThe\nTHE\nLazy\nLAZY\nDog\nDOG\n";

        inputStream = new ByteArrayInputStream(testInput.getBytes());

        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        arguments = new String[]{"-i"};
        expectedResults = new String[]{"Quick", "Brown", "Fox", "Jumps", "Over", "The", "Lazy", "Dog"};


        processor = new ProcessorClient(UniqProcessor.getInstance());
        processor.init(arguments);
        processor.processAndOutput();
        processor.closeIO();

        realOut = outputStream.toString().split("\n");

        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], realOut[i].trim());
        }

        inputStream.reset();
        outputStream.reset();

        /*
         * FIFTH test case: -c -s 7
         * Checking when -s flag is greater than every word size
         */
        testInput = "Quick\nQUICK\nBrown\nBROWN\nFox\nFOX\nJumps\nJUMPS\nOver\nOVER\nThe\nTHE\nLazy\nLAZY\nDog\nDOG\n";

        inputStream = new ByteArrayInputStream(testInput.getBytes());

        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        arguments = new String[]{"-c", "-s", "7"};
        expectedResults = new String[]{"16 : Quick"};


        processor = new ProcessorClient(UniqProcessor.getInstance());
        processor.init(arguments);
        processor.processAndOutput();
        processor.closeIO();

        realOut = outputStream.toString().split("\n");

        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], realOut[i].trim());
        }

        inputStream.reset();
        outputStream.reset();

        /*
         *  SIXTH test case: no args
         */

        testInput = "Quick\nQUICK\nBrown\nBROWN\nFox\nFOX\nJumps\nJUMPS\nOver\nOVER\nThe\nTHE\nLazy\nLAZY\nDog\nDOG\n";

        inputStream = new ByteArrayInputStream(testInput.getBytes());

        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        arguments = new String[]{};
        expectedResults = new String[]{"Quick", "QUICK", "Brown", "BROWN", "Fox", "FOX", "Jumps",
                "JUMPS", "Over", "OVER", "The", "THE", "Lazy", "LAZY", "Dog", "DOG"};


        processor = new ProcessorClient(UniqProcessor.getInstance());
        processor.init(arguments);
        processor.processAndOutput();
        processor.closeIO();

        realOut = outputStream.toString().split("\n");

        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], realOut[i].trim());
        }

        inputStream.reset();
        outputStream.reset();

        /*
         * SEVENTH case: empty input
         */

        testInput = "";

        inputStream = new ByteArrayInputStream(testInput.getBytes());

        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        arguments = new String[]{};
        expectedResults = new String[]{};


        processor = new ProcessorClient(UniqProcessor.getInstance());
        processor.init(arguments);
        processor.processAndOutput();
        processor.closeIO();

        realOut = outputStream.toString().split("\n");

        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], realOut[i].trim());
        }

        inputStream.reset();
        outputStream.reset();

    }

    public void testOutput1() throws Exception {


    }

    public void testProcessAndTestOutput() throws Exception {


    }

}

/**
 * Dependency Injection, has purpose to simplify testing
 * Takes UniqProcessor as parameter in constructor and delegates its methods
 */
class ProcessorClient {

    UniqProcessor up;

    public ProcessorClient(UniqProcessor up) {
        this.up = up;
    }

    public void init(String[] arguments) {
        up.init(arguments);
    }

    public List<EntryPair<String, Integer>> process() {
        return up.process();
    }

    public void processAndOutput() {
        up.processAndOutput();
    }

    public void output() {
        up.output();
    }

    public void output(List<EntryPair<String, Integer>> list) {
        up.output(list);
    }

    public void closeIO() {
        up.closeIO();
    }
}