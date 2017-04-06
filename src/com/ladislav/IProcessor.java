package com.ladislav;

import java.util.List;

/**
 * Simple interface for implementation of input processor.
 */
public interface IProcessor {
    void processAndOutput();
    List process();
    void output();
}
