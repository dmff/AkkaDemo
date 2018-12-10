package cn.akka.demo1;

import akka.actor.*;
import akka.japi.Creator;
import akka.japi.Option;
import akka.pattern.Patterns;
import akka.util.Timeout;
import cn.akka.demo1.actor.Greeter;
import cn.akka.demo1.model.Greeting;
import cn.akka.demo1.model.WhoToGreet;
import cn.akka.demo1.actor.GreetPrinter;
import cn.akka.demo1.model.Greet;
import cn.akka.demo1.typeactor.Squarer;
import cn.akka.demo1.typeactor.SquarerImpl;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


public class AkkaMain {

    public static void main(String[] args) throws Exception {
        testUnTypeActor();
    }

    private static void testUnTypeActor() throws Exception{
        final ActorSystem system = ActorSystem.create("helloakka");
        // 创建一个到greeter Actor的管道
        final ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");
        System.out.println(greeter.path());

        final ActorSelection selection = system.actorSelection("akka://helloakka/user/greeter");
        selection.tell(new WhoToGreet("akka"), ActorRef.noSender());

        // 创建邮箱
        final Inbox inbox = Inbox.create(system);
        // 先发第一个消息,消息类型为WhoToGreet
//        greeter.tell(new WhoToGreet("akka"), ActorRef.noSender());
        // 真正的发送消息,消息体为Greet
        inbox.send(greeter, new Greet());

        // 等待5秒尝试接收Greeter返回的消息
        Greeting greeting1 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("Greeting: " + greeting1.message);

        // 发送第三个消息,修改名字
        greeter.tell(new WhoToGreet("typesafe"), ActorRef.noSender());
        // 发送第四个消息
        inbox.send(greeter, new Greet());

        // 等待5秒尝试接收Greeter返回的消息
        Greeting greeting2 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("Greeting: " + greeting2.message);

        // 新创建一个Actor的管道
        ActorRef greetPrinter = system.actorOf(Props.create(GreetPrinter.class));

        //使用schedule 每一秒发送一个Greet消息给 greeterActor,然后把greeterActor的消息返回给greetPrinterActor
        system.scheduler().schedule(Duration.Zero(), Duration.create(1, TimeUnit.SECONDS), greeter, new Greet(), system.dispatcher(), greetPrinter);
        //system.shutdown();
    }

    private static void testTypeActor() throws Exception{
        final ActorSystem system = ActorSystem.create("helloakka");
        /*默认构造方法的Actor*/
        Squarer mySquarer = TypedActor.get(system).typedActorOf(new TypedProps<>(Squarer.class, SquarerImpl.class));
        /*传参构造的Actor*/
        Squarer otherSquarer = TypedActor.get(system).typedActorOf(new TypedProps<>(Squarer.class, (Creator<SquarerImpl>) () -> new SquarerImpl("foo")), "name");
        Option<Integer> oSquare = mySquarer.squareNowPlease(10);
        System.out.println("阻塞异步调用执行外面");
        //获取结果
        System.out.println(oSquare.get());

        Future<Integer> fSquare = mySquarer.square(10);
        System.out.println("非阻塞异步执行外面");
        //等待5秒内返回结果
        System.out.println(Await.result(fSquare, Duration.apply(5, TimeUnit.SECONDS)));
        TypedActor.get(system).stop(mySquarer);
    }

    /**
     * 测试发送接收模式:
     * 1.可以邮箱
     * 2.Akka提供了ask和pipe两个方法来实现Send and Receive模式的消息通信
     *
     * 但是需要注意的是,ask的被调用Actor必须在onReceive方法中显示的调用getSender().tell(xxxx,getSelf())发送Response为返回的Future填充数据.
     * Akka还提供了一个Await.result方法来阻塞的获取Future的结果
     */
    private void testSendReceive() throws Exception{
        Object msg = null;
        ActorRef actor = null;
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(actor, msg, timeout);
        String result = (String) Await.result(future, timeout.duration());
    }
}
