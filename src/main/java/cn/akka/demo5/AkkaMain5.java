package cn.akka.demo5;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import cn.akka.demo5.model.StartCommand;
import cn.akka.demo5.actor.ControlActor;
import cn.akka.demo5.actor.WriterActor;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


public class AkkaMain5 {

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("demo5", ConfigFactory.load("demo5").getConfig("demo5"));
        //testDispatcher(system);
        testScheduler(system);
        //system.shutdown();
    }

    private static void testDispatcher(ActorSystem system) {
        final ActorRef controlActor = system.actorOf(Props.create(ControlActor.class), "control");
        controlActor.tell(new StartCommand(100), ActorRef.noSender());
    }

    private static void testScheduler(ActorSystem system) throws Exception{
        ActorRef actorRef = system.actorOf(Props.create(WriterActor.class));
        //延时5秒后执行且执行一次
//        system.scheduler().scheduleOnce(Duration.create(5, TimeUnit.SECONDS), actorRef, "1111", system.dispatcher(), ActorRef.noSender());
        //每个一秒执行一次，会新开一个线程执行，在10秒后取消这个任务
        Cancellable cancellable = system.scheduler().schedule(Duration.Zero(), Duration.create(1, TimeUnit.SECONDS), actorRef, "1111", system.dispatcher(), ActorRef.noSender());
        Thread.sleep(10000);
        cancellable.cancel();
    }
}
