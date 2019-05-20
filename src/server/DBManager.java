package server;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;

import resources.SearchRequest;

import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.restart;

public class DBManager extends AbstractActor{

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class, request -> {
                    getContext().actorOf(Props.create(DBWorker.class), "databaseWorker").tell(request, getSender());
                })
                .build();
    }

    private static SupervisorStrategy strategy //TODO
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
                    .matchAny(o -> restart())
                    .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
