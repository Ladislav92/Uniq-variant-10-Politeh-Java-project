package com.ladislav;

import junit.framework.TestCase;

/**
 *  Unit test for UniqParser
 */
public class UniqParserTest extends TestCase {

    public void testParseArgs() throws Exception {

        UniqParser up = new UniqParser();
        String[] args = {"-i", "-u", "-c", "-s", "5", "-o", "output_file.txt", "input_file.txt"};

        up.parseArgs(args);

        assertFalse(up.isCaseSensitive());
        assertTrue(up.isUnique());
        assertTrue(up.isCounted());
        assertEquals(up.getIgnoredCharsNum(), 5);
        assertEquals(up.getOutputFileName(), "output_file.txt");
        assertEquals(up.getInputFileName(), "input_file.txt");

        up = new UniqParser();
        args = new String[]{"-i", "-u", "-c", "-s", "5"};

        up.parseArgs(args);

        assertFalse(up.isCaseSensitive());
        assertTrue(up.isUnique());
        assertTrue(up.isCounted());
        assertEquals(up.getIgnoredCharsNum(), 5);
        assertEquals(up.getOutputFileName(), "");
        assertEquals(up.getInputFileName(), "");

        up = new UniqParser();
        args = new String[]{"-i", "-u", "-c", "-o", "output.txt"};

        up.parseArgs(args);

        assertFalse(up.isCaseSensitive());
        assertTrue(up.isUnique());
        assertTrue(up.isCounted());
        assertEquals(up.getIgnoredCharsNum(), -1);
        assertEquals(up.getOutputFileName(), "output.txt");
        assertEquals(up.getInputFileName(), "");

        up = new UniqParser();
        args = new String[]{"-u", "-c", "-o", "output.txt", "input.txt"};

        up.parseArgs(args);

        assertTrue(up.isCaseSensitive());
        assertTrue(up.isUnique());
        assertTrue(up.isCounted());
        assertEquals(up.getIgnoredCharsNum(), -1);
        assertEquals(up.getOutputFileName(), "output.txt");
        assertEquals(up.getInputFileName(), "input.txt");
    }
}