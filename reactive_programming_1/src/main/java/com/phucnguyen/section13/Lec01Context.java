package com.phucnguyen.section13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    Context is for providing metadata about the request (similar to HTTP headers)
    Why do they do this? (The "Why")
    In Reactive programming, data flows across many threads asynchronously.
    If Context were mutable (changeable like a standard HashMap), you would have a nightmare scenario:
        1. Thread A reads the context: "User is Phuc".
        2.Thread B suddenly changes the map: "User is null".
        3. Thread A crashes because the data changed right under its feet.
    By forcing you to create a new context every time you add data,
    Reactor ensures that Thread A always holds onto its own
    specific version of the data (context1), no matter what Thread B
    does with its version (context2).
 */
public class Lec01Context {
    private static final Logger log = LoggerFactory.getLogger(Lec01Context.class);

    public static void main(String[] args) {

    }
}
