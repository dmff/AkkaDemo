package cn.sunxiang0918.akka.demo2.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import cn.sunxiang0918.akka.demo2.server.actor.AggregateActor;
import cn.sunxiang0918.akka.demo2.server.actor.MapActor;
import cn.sunxiang0918.akka.demo2.server.actor.ReduceActor;
import cn.sunxiang0918.akka.demo2.server.actor.WCMapReduceActor;
import com.typesafe.config.ConfigFactory;

/**
 * @author SUN
 * @version 1.0
 * @Date 16/1/7 10:43
 */
public class WCMapReduceServer{

    public WCMapReduceServer(int reduceWorkers, int mapWorkers) {
        ActorSystem system = ActorSystem.create("WCMapReduceApp", ConfigFactory.load("application").getConfig("WCMapReduceApp"));
        // 创建聚合Actor
        ActorRef aggregateActor = system.actorOf(Props.create(AggregateActor.class));

        // 创建多个Reduce的Actor
        ActorRef reduceRouter = system.actorOf(Props.create(ReduceActor.class,aggregateActor).withRouter(new RoundRobinPool(reduceWorkers)));

        // 创建多个Map的Actor
        ActorRef mapRouter = system.actorOf(Props.create(MapActor.class,reduceRouter).withRouter(new RoundRobinPool(mapWorkers)));

        // create the overall WCMapReduce Actor that acts as the remote actor
        // for clients
        Props props = Props.create(WCMapReduceActor.class,aggregateActor,mapRouter).withDispatcher("priorityMailBox-dispatcher");
        ActorRef wcMapReduceActor = system.actorOf(props, "WCMapReduceActor");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new WCMapReduceServer(50, 50);
    }

}
