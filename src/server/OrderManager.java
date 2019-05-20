package server;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;

import resources.OrderRequest;

import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.restart;

public class OrderManager extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OrderRequest.class, request -> {
                    getContext().actorOf(Props.create(OrderActor.class), "orderWorker" ).tell(request, getSender());
                })
                .build();
    }


    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .matchAny(o -> restart())
            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
