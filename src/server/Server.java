package server;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Server extends AbstractActor {

 //  final ActorRef databaseManager = getContext().getSystem().actorOf(Props.create(DBManager.class), "databaseManager");
  //  final ActorRef orderManager = getContext().getSystem().actorOf(Props.create(OrderManager.class), "orderManager");
  //  final ActorRef streamManager = getContext().getSystem().actorOf(Props.create(StreamManager.class), "streamManager");


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("search")) {
                        System.out.println("I'm searching");
                       // context().child("databaseManager").get().tell(s, getSelf()); // send task to child
                        getSender().tell("respond i'm searching", getSelf());
                    }
                })
                .build();
    }

//   // private static SupervisorStrategy strategy
//            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.
//                    // todo: match arithmetic exception
//                    matchAny(o -> restart()).
//                    build());
//
//    @Override
//    public SupervisorStrategy supervisorStrategy() {
//        return strategy;
//    }
}
