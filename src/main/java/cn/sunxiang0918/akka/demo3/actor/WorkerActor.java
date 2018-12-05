package cn.sunxiang0918.akka.demo3.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author SUN
 * @version 1.0
 * @Date 16/1/6 22:35
 */
public class WorkerActor extends UntypedActor {

    private ActorRef jobController;

    @Override
    public void onReceive(Object message) throws Exception {
        // using scheduler to send the reply after 1000 milliseconds
        getContext().system().scheduler().scheduleOnce(Duration.create(1000, TimeUnit.MILLISECONDS),jobController,"DONE",getContext().system().dispatcher(),ActorRef.noSender());
//        System.out.println(Thread.currentThread().getName());
//        jobController.tell("DONE",ActorRef.noSender());
    }

    public WorkerActor(ActorRef inJobController) {
        jobController = inJobController;
    }
}
