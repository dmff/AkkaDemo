package cn.akka.demo1.actor;

import akka.actor.UntypedActor;
import cn.akka.demo1.model.Greeting;


public class GreetPrinter extends UntypedActor{

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Greeting)
            System.out.println(((Greeting) message).message);
    }
}
