package com.christopher.tries;


import com.christopher.tries.CompressedNode;

public class CompressedTrie {
    private final CompressedNode root = new CompressedNode(false);
    private final char CASE;

    public CompressedTrie() { this.CASE = 'a'; }           // default: lowercase
    public CompressedTrie(char CASE) { this.CASE = CASE; } // or 'A' for uppercase

    public void insert(String word) {
        if (word == null || word.isEmpty()) return;
        String w = word;
        CompressedNode traverse = root;
        int i = 0;

        while (i < w.length() && traverse.edgeLabel[w.charAt(i) - CASE] != null) {
            int index = w.charAt(i) - CASE, j = 0;
            StringBuilder label = traverse.edgeLabel[index];

            while (j < label.length() && i < w.length() && label.charAt(j) == w.charAt(i)) {
                ++i; ++j;
            }
            if (j == label.length()) {
                traverse = traverse.children[index];
            } else {
                if (i == w.length()) {
                    CompressedNode existingChild = traverse.children[index];
                    CompressedNode newChild = new CompressedNode(true);
                    StringBuilder restOfLabel = strCopy(label, j);
                    label.setLength(j);
                    traverse.children[index] = newChild;
                    newChild.children[restOfLabel.charAt(0) - CASE] = existingChild;
                    newChild.edgeLabel[restOfLabel.charAt(0) - CASE] = restOfLabel;
                } else {
                    StringBuilder restOfLabel = strCopy(label, j);
                    CompressedNode newChild = new CompressedNode(false);
                    StringBuilder remainingWord = strCopy(w, i);
                    CompressedNode temp = traverse.children[index];
                    label.setLength(j);
                    traverse.children[index] = newChild;
                    newChild.edgeLabel[restOfLabel.charAt(0) - CASE] = restOfLabel;
                    newChild.children[restOfLabel.charAt(0) - CASE] = temp;
                    newChild.edgeLabel[remainingWord.charAt(0) - CASE] = remainingWord;
                    newChild.children[remainingWord.charAt(0) - CASE] = new CompressedNode(true);
                }
                return;
            }
        }
        if (i < w.length()) {
            traverse.edgeLabel[w.charAt(i) - CASE] = strCopy(w, i);
            traverse.children[w.charAt(i) - CASE] = new CompressedNode(true);
        } else {
            traverse.isEnd = true;
        }
    }

    private StringBuilder strCopy(CharSequence str, int index) {
        StringBuilder result = new StringBuilder(Math.max(16, str.length() - index));
        while (index != str.length()) result.append(str.charAt(index++));
        return result;
    }

    public boolean search(String word) {
        if (word == null) return false;
        String w = word;
        int i = 0;
        CompressedNode traverse = root;

        while (i < w.length() && traverse.edgeLabel[w.charAt(i) - CASE] != null) {
            int index = w.charAt(i) - CASE;
            StringBuilder label = traverse.edgeLabel[index];
            int j = 0;

            while (i < w.length() && j < label.length()) {
                if (w.charAt(i) != label.charAt(j)) return false;
                ++i; ++j;
            }
            if (j == label.length() && i <= w.length()) {
                traverse = traverse.children[index];
            } else {
                return false;
            }
        }
        return i == w.length() && traverse.isEnd;
    }

    public boolean startsWith(String pre) {
        if (pre == null) return false;
        String p = pre;
        int i = 0;
        CompressedNode traverse = root;

        while (i < p.length() && traverse.edgeLabel[p.charAt(i) - CASE] != null) {
            int index = p.charAt(i) - CASE;
            StringBuilder label = traverse.edgeLabel[index];
            int j = 0;

            while (i < p.length() && j < label.length()) {
                if (p.charAt(i) != label.charAt(j)) return false;
                ++i; ++j;
            }
            if (j == label.length() && i <= p.length()) {
                traverse = traverse.children[index];
            } else {
                return true; // prefix shorter than edge label
            }
        }
        return i == p.length();
    }
}
