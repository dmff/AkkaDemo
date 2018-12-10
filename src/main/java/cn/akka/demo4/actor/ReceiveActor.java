package cn.akka.demo4.actor;

import java.util.UUID;

import akka.actor.UntypedActor;


public class ReceiveActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("接收到消息:"+message);
        String content = Thread.currentThread().getName()+":"+ UUID.randomUUID().toString();
        //返回结果
        getSender().tell(content,null);
    }
}
