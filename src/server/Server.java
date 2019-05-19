package server;

import resources.OrderRequest;
import resources.SearchRequest;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Server extends AbstractActor {

    final ActorRef databaseManager = getContext().getSystem().actorOf(Props.create(DBManager.class), "databaseManager");
    final ActorRef orderManager = getContext().getSystem().actorOf(Props.create(OrderManager.class), "orderManager");
  //  final ActorRef streamManager = getContext().getSystem().actorOf(Props.create(StreamManager.class), "streamManager");
//TODO

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("search")) {
                        SearchRequest request = new SearchRequest(s.substring(7));
                        databaseManager.tell(request, getSender());
                    }else if (s.startsWith("order")) {
                        OrderRequest request = new OrderRequest(s.substring(6));
                        orderManager.tell(request, getSender());
                    }
                })
                .build();
    }

    //TODO
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
