package cn.akka.demo3;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import cn.akka.demo3.actor.JobControllerActor;
import cn.akka.demo3.actor.WorkerActor;


public class AkkaMain3 {

    static final int no_of_msgs = 10000;

    final int no_of_workers = 10;

    final ActorRef router;

    public AkkaMain3() {
        //int no_of_workers = 10000;
        ActorSystem system = ActorSystem.create("LoadGeneratorApp");
        ActorRef appManager = system.actorOf(Props.create(JobControllerActor.class, no_of_msgs), "jobController");
        router = system.actorOf(Props.create(WorkerActor.class, appManager).withRouter(new RoundRobinPool(no_of_workers)));
    }

    public static void main(String[] args) throws Exception {
        new AkkaMain3().generateLoad();
    }

    private void generateLoad() {
        //开始产生1000万消息
        for (int i = no_of_msgs; i >= 0; i--) {
            router.tell("Job Id " + i + "# send", ActorRef.noSender());
        }
        System.out.println("All jobs sent successfully");
    }
}
