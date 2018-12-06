package cn.akka.demo2.server;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.dispatch.PriorityGenerator;
import akka.dispatch.UnboundedPriorityMailbox;
import com.typesafe.config.Config;


public class MyPriorityMailBox extends UnboundedPriorityMailbox {

    /**
     * 创建一个自定义优先级的无边界的邮箱. 用来规定命令的优先级. 这个就保证了DISPLAY_LIST 这个事件是最后再来处理.
     */
    public MyPriorityMailBox(ActorSystem.Settings settings, Config config) {
        super(new PriorityGenerator() {
            @Override
            public int gen(Object message) {
                if (message.equals("DISPLAY_LIST"))
                    return 2;
                else if (message.equals(PoisonPill.getInstance()))
                    return 3;
                else
                    return 0;
            }
        });
    }

}
