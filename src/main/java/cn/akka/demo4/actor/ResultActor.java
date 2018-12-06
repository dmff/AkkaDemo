package cn.akka.demo4.actor;

import akka.actor.UntypedActor;


public class ResultActor extends UntypedActor{

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("接收到消息:"+message);
    }
}
