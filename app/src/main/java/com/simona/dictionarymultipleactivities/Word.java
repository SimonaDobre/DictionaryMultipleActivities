package com.simona.dictionarymultipleactivities;

import java.io.Serializable;

public class Word implements Serializable {

    private String word;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
