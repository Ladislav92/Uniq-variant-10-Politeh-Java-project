package com.ladislav;

import junit.framework.TestCase;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Ladislav on 4/6/2017.
 */

public class UniqProcessorTest extends TestCase {

    public void testCloseIONullPointerException() throws Exception {
        UniqProcessor processorClient = UniqProcessor.getInstance();

        try {
            processorClient.destroy();
            fail("Should throw NullPointerException if processor isn't initialized");
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    public void testProcessAndOutput() throws Exception {

        /*
         * FIRST test case: from file to console
         * All flags set except -o fileName flag
         */
        UniqProcessor processor = UniqProcessor.getInstance();

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String[] arguments = {"-i", "-u", "-s", "2", "-c", "input_file.txt"};
        String[] expectedResults = {"1 : FirstOne", "1 : aaaa", "1 : aaaa", "1 : oooo", "1 : LastOne"};

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

        processor.init(arguments);
        processor.processAndOutput();


        Path p = Paths.get("output_file.txt");
        File testFile = p.toFile();

        try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {

            for (String anExpectedResult : expectedResults) {
                assertEquals(anExpectedResult, br.readLine().trim());
            }
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

        processor.init(arguments);
        processor.processAndOutput();

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


        processor.init(arguments);
        processor.processAndOutput();

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


        processor.init(arguments);
        processor.processAndOutput();

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


        processor.init(arguments);
        processor.processAndOutput();

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


        processor.init(arguments);
        processor.processAndOutput();

        realOut = outputStream.toString().split("\n");

        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], realOut[i].trim());
        }

        inputStream.reset();
        outputStream.reset();

    }

    public void testProcessAndTestOutput() throws Exception {

        /*
         * FIRST test case: from file to console
         * All flags set except -o fileName flag
         */
        UniqProcessor processor = UniqProcessor.getInstance();

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String[] arguments = {"-i", "-u", "-s", "2", "-c", "input_file.txt"};
        String[] expectedResults = {"1 : FirstOne", "1 : aaaa", "1 : aaaa", "1 : oooo", "1 : LastOne"};

        processor.init(arguments);
        processor.process();
        processor.output();

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

        processor.init(arguments);
        processor.process();
        processor.output();

        Path p = Paths.get("output_file.txt");
        File testFile = p.toFile();

        try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {

            for (String anExpectedResult : expectedResults) {
                assertEquals(anExpectedResult, br.readLine().trim());
            }
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

        processor.init(arguments);
        processor.process();
        processor.output();

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


        processor.init(arguments);
        processor.process();
        processor.output();

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

        processor.init(arguments);
        processor.process();
        processor.output();

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


        processor.init(arguments);
        processor.process();
        processor.output();

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

        processor.init(arguments);
        processor.process();
        processor.output();

        realOut = outputStream.toString().split("\n");

        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], realOut[i].trim());
        }

        processor.destroy();
        inputStream.reset();
        outputStream.reset();
    }

    public void testOutputWithParams() throws Exception {

        /**
         * Testing client with no args, just output needed - output is already tested
         * Since no args output will be to console
         */

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String testInput[] = {"FirstOne", "aaaa", "bbbb", "LastOne"};

        ArrayList<EntryPair<String, Integer>> testList = new ArrayList<>();
        for (String s : testInput) {
            testList.add(new EntryPair<>(s, 1));
        }

        String[] args = {};

        UniqProcessor processor = UniqProcessor.getInstance();

        processor.init(args);
        processor.output(testList);

        String[] realOut = outputStream.toString().split("\n");

        for (int i = 0; i < testInput.length; i++) {
            assertEquals(testInput[i], realOut[i]);
        }
        processor.destroy();

    }

    public void testCloseIOIllegalStateException(){
        String input = "Hey Joe Where You Goin' With That Gun Of Yours";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        String[] args = {};

        UniqProcessor processor = UniqProcessor.getInstance();

        processor.init(args);
        processor.destroy();

        try {
            processor.process();
            fail("Should throw IllegalStateException if I/O is closed");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

}
