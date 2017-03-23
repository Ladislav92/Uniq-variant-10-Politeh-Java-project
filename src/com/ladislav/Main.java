package com.ladislav;

/**
 *
 */
public class Main {

    // command line: uniq [-i] [-u] [-c] [-s num] [-o file] [file]
    // Set arguments = "-i", "-u", "-c", "-s", "-o"
    // args = { "-i", "-u", "-c", "-s", "5", "-o", "output_file.txt", "input_file.txt"}

    // -i -> CASE INSENSITIVE comparison
    // -u -> JUST UNIQUE STRINGS in OUTPUT
    // -c -> compress strings(output only 1 string) and write number of times repeated in front of string in output
    // -s num -> ignore first N chars when comparing lines
    // -o file -> output to file, if not flagged output to console
    // if there is no input file output to console

    public static void main(String[] args) {

        UniqProcessor processor = UniqProcessor.getInstance();
        processor.process(args);

    }
}