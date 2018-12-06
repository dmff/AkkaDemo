package cn.akka.demo5.actor;

import java.util.concurrent.atomic.AtomicInteger;

import akka.actor.UntypedActor;


public class WriterActor extends UntypedActor {

    private static AtomicInteger a = new AtomicInteger(0);
    
    @Override
    public void preStart() throws Exception {
        System.out.println("启动一个新的Actor:"+a.getAndIncrement());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println(getSelf().path()+" "+Thread.currentThread().getName());
    }
}
