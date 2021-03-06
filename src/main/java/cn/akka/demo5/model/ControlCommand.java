package cn.akka.demo5.model;

import java.io.Serializable;


public class ControlCommand implements Serializable {
    
    private String message;

    private Long startTime;

    public ControlCommand(String message, Long startTime) {
        this.message = message;
        this.startTime = startTime;
    }

    public ControlCommand() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
