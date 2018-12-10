package cn.akka.demo5.actor;

import akka.actor.ActorSystem;
import akka.routing.CustomRouterConfig;
import akka.routing.Router;


public class VoteCountRouter extends CustomRouterConfig{

    @Override
    public Router createRouter(ActorSystem system) {
        return null;
    }
}
