package server;

import resources.*;
import scala.concurrent.duration.Duration;
import java.io.*;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.escalate;

public class DBActor extends AbstractActor {

    private ActorRef client = null;
    private int counter = 2;
    private int dbcounter = 2;

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class, request -> {
                    client = getSender();
                    getContext().actorOf(Props.create(DBSearch.class), "searchDatabase1").tell(new SearchDBRequest(request.getTitle(), "database/db1.txt"), getSelf());
                    getContext().actorOf(Props.create(DBSearch.class), "searchDatabase2").tell(new SearchDBRequest(request.getTitle(), "database/db2.txt"), getSelf());
                })
                .match(SearchResponse.class, response -> {
                    if((response.getPrice() == 0) && counter == 2){
                        counter--;
                    }
                    else{
                        client.tell(response, getSelf());
                        getContext().stop(getSelf());
                    }
                })
                .match(DBErrorResponse.class, response -> {
                    if(dbcounter == 2){
                        counter--;
                        dbcounter--;
                    }else if(dbcounter == 1){
                        client.tell("Databases are not available", getSelf());
                        getContext().stop(getSelf());
                    }
                    if(counter == 0){
                        client.tell(new SearchResponse("aa", 0), getSelf());
                        getContext().stop(getSelf());
                    }
                })
                .build();
    }

    private SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .match(FileNotFoundException.class, o -> SupervisorStrategy.stop())
            .matchAny(o -> restart())
            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}
