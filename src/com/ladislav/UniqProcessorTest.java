package com.ladislav;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Ladislav on 4/6/2017.
 */
public class UniqProcessorTest extends TestCase {

    // reading inputs from actual file
    String[] expectedInput = {"aaaa", "bbbb", "bbbb", "aaaa", "cccc", "CCcc", "abFF", "ccFF", "oooo", "zzzz", "zzzz"};

    public void testProcessAndTestOutput() throws Exception {
        String[] arguments = {"-i", "-s", "2", "-c", "-o output", "input_file.txt"};
        String[] expectedOut = {"1 : aaaa", "2 : bbbb", "1 : aaaa", "2 : cccc",
                "2 : abFF", "1 : oooo", "2 : zzzz", "5 : test", "2 : cd"};

        UniqProcessor processor = UniqProcessor.getInstance();
        processor.init(arguments);
        processor.process();

        System.out.println("Real results:");
        processor.output();

        printExpected(expectedOut);

    }

    public void testProcessAndOutput() throws Exception {
        String[] arguments = {"-i", "-u", "-s", "2", "-c", "-o output", "input_file.txt"};
        String[] expectedOut = {"1 : aaaa", "1 : aaaa", "1 : oooo"};

        UniqProcessor processor = UniqProcessor.getInstance();
        processor.init(arguments);

        System.out.println("Real results:");
        processor.processAndOutput();

        printExpected(expectedOut);
    }

    public void testOutput1() throws Exception {
        String[] arguments = {"-i", "-s", "3", "-c"};
        String[] expectedOut = {"1 : aaaa", "2 : bbbb", "1 : aaaa", "2 : cccc",
                "2 : abFF", "1 : oooo", "2 : zzzz","5 : test","2 : cd"};
        String inputData =
                "aaaa\n" +
                "bbbb\n" +
                "bbbb\n" +
                "aaaa\n" +
                "cccc\n" +
                "CCcc\n" +
                "abFF\n" +
                "ccFF\n" +
                "oooo\n" +
                "zzzz\n" +
                "zzzz\n" +
                "test\n" +
                "test\n" +
                "test\n" +
                "Test\n" +
                "TesT\n" +
                "cd\n"   +
                "ab\n";

        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));

        UniqProcessor processor = UniqProcessor.getInstance();
        processor.init(arguments);
        List<EntryPair<String, Integer>> processed = processor.process();

        System.out.println("Real results:");
        processor.output(processed);

        printExpected(expectedOut);

    }

    // Simple code cleaning method
    public void printExpected(String[] s) {
        System.out.println("Expected results:");
        for (String expected : s) {
            System.out.println(expected);
        }
        System.out.println("=================================");

    }
}
