package com.christopher.tries;

public class CompressedNode {
    static final int LETTERS = 26;
    CompressedNode[] children = new CompressedNode[LETTERS];
    StringBuilder[] edgeLabel = new StringBuilder[LETTERS];
    boolean isEnd;

    public CompressedNode(boolean isEnd) {
        this.isEnd = isEnd;
    }
}
