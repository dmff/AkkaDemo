package cn.akka.demo1.model;

import java.io.Serializable;


public class WhoToGreet implements Serializable {
    public final String who;
    public WhoToGreet(String who) {
        this.who = who;
    }
}
