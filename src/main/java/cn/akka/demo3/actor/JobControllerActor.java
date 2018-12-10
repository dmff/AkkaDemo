package cn.akka.demo3.actor;

import akka.actor.UntypedActor;


public class JobControllerActor extends UntypedActor{

    int count = 0;
    long startedTime = System.currentTimeMillis();
    int no_of_msgs = 0;

    public JobControllerActor(int no_of_msgs) {
        this.no_of_msgs = no_of_msgs;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String){
            if (message.equals("DONE")){
                count++;
                //计数到达1000万结束，统计时间
                if (count == no_of_msgs) {
                    long now = System.currentTimeMillis();
                    System.out.println("All messages processed in " + (now - startedTime)+" millionseconds");
                    System.out.println("Total Number of messages processed " + count);
                    getContext().system().shutdown();
                }
            }
        }
    }
}
