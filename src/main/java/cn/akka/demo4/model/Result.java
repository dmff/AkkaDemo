package cn.akka.demo4.model;

import java.io.Serializable;


public class Result implements Serializable {

    private String word;
    private String name;

    public Result(String word, String name) {
        this.name = name;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Result{" +
                "word='" + word + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
