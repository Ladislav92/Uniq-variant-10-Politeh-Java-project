package com.ladislav;

public class Main {

    public static void main(String[] args) {
        UniqProcessor processor = UniqProcessor.getInstance();

        processor.init(args);
        processor.process();
        processor.output();
    }
}


