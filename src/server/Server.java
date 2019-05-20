package server;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;

import resources.OrderRequest;
import resources.SearchRequest;

import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.restart;

public class Server extends AbstractActor {

    final ActorRef databaseManager = getContext().actorOf(Props.create(DBManager.class), "databaseManager");
    final ActorRef orderManager = getContext().actorOf(Props.create(OrderManager.class), "orderManager");
  //  final ActorRef streamManager = getContext().getSystem().actorOf(Props.create(StreamManager.class), "streamManager");
//TODO

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("search")) {
                        System.out.println("Server");
                        SearchRequest request = new SearchRequest(s.substring(7));
                        databaseManager.tell(request, getSender());
                    }else if (s.startsWith("order")) {
                        OrderRequest request = new OrderRequest(s.substring(6));
                        orderManager.tell(request, getSender());
                    }else if (s.startsWith("stream")) {
                       //TODO
                    }

                })
                .build();
    }


//    private static SupervisorStrategy strategy //TODO
//            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
//                    .matchAny(o -> restart())
//                    .build());
//
//    @Override
//    public SupervisorStrategy supervisorStrategy() {
//        return strategy;
//    }
}
