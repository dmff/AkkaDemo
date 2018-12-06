package cn.akka.demo2.client.actor;

import akka.actor.UntypedActor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileReadActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String fileName = (String) message;
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(Thread.currentThread().getContextClassLoader().getResource(fileName).openStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    /*遍历,一行一个消息反馈给消息发送方*/
                    getSender().tell(line,null);
                }
                System.out.println("All lines send !");
                /*发送一个结束标识*/
                getSender().tell(String.valueOf("EOF"),null);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        } else {
            throw new IllegalArgumentException("Unknown message [" + message + "]");
        }
    }
}
