package server;

import akka.actor.AbstractActor;
import akka.actor.Props;
import resources.OrderRequest;
import resources.SearchRequest;

public class OrderManager extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OrderRequest.class, request -> {
                    //getContext().getSystem().actorOf(Props.create(DBWorker.class), "databaseWorker").tell(request, getSender());
                    System.out.println("OrderManager");
                })
                .build();
    }
}
