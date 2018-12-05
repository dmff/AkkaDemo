package cn.sunxiang0918.akka.demo5.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;
import cn.sunxiang0918.akka.demo5.model.StartCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SUN
 * @version 1.0
 * @Date 16/1/26 14:31
 */
public class ControlActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof StartCommand) {
            //dispatcher(message);
            //route2(message);
            route1(message);
        }
    }

    private List<ActorRef> createActors(int actorCount) {
        Props props = Props.create(WriterActor.class).withDispatcher("writer-dispatcher");
        List<ActorRef> actors = new ArrayList<>(actorCount);
        for (int i = 0; i < actorCount; i++) {
            actors.add(getContext().actorOf(props, "writer_" + i));
        }
        return actors;
    }

    private void dispatcher(Object message){
        List<ActorRef> actors = createActors(((StartCommand) message).getActorCount());
        actors.stream().parallel().forEach(actorRef -> actorRef.tell("Insert", ActorRef.noSender()));
    }

    private void route1(Object message) {
        List<ActorRef> actors = createActors(((StartCommand) message).getActorCount());
        Router router = new Router(new RoundRobinRoutingLogic());
        for (ActorRef actor : actors) {
            router = router.addRoutee(actor);
        }
        router.route("Insert", ActorRef.noSender());
    }

    private void route2(Object message) {
            /*使用Router方式启动100个Actor*/
        Props props = Props.create(WriterActor.class)
                .withRouter(new RoundRobinPool(((StartCommand) message).getActorCount()))
                .withDispatcher("writer-dispatcher");
        ActorRef actorRef = getContext().actorOf(props);
        for (int i = 0; i < 100; i++) {
            actorRef.tell("Insert", ActorRef.noSender());
        }
    }
}
