package com.christopher.tries;

public class Main {
    public static void main(String[] args) {
        CompressedTrie trie = new CompressedTrie('a'); // inputs should be lowercase
        trie.insert("wood");
        trie.insert("worn");

        System.out.println("wood = " + trie.search("wood"));
        System.out.println("worn = " + trie.search("worn"));
        System.out.println("wo = " + trie.startsWith("wo"));
        System.out.println("face = " + trie.startsWith("face"));
    }
}
