package com.ladislav;

public class Main {

    // Command line: uniq [-i] [-u] [-c] [-s num] [-o file] [file]
    // Set arguments = "-i", "-u", "-c", "-s", "-o"
    // args = { "-i", "-u", "-c", "-s", "5", "-o", "output_file.txt", "input_file.txt"}

    // -i -> Case insensitive comparison
    // -u -> Just unique strings in output
    // -c -> Compress strings(output only 1 string if repeats)and write number of times repeated in front of string in output
    // -s num -> Ignore first n chars when comparing lines
    // -o file -> Output to file, if not flagged output to console
    // [file]  -> If there is no input file get input from console, input file is last argument

    public static void main(String[] args) {
        UniqProcessor processor = UniqProcessor.getInstance();
        processor.process(args);
    }
}
