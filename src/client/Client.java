package client;

import akka.actor.*;
import resources.ErrorResponse;
import resources.SearchResponse;


public class Client extends AbstractActor{

    final ActorSelection server = getContext().actorSelection("akka.tcp://library@127.0.0.1:8894/user/server");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> { //z konsoli
                    if (s.startsWith("search")) {
                        server.tell(s, getSelf());
                    }else if (s.startsWith("order")) {
                        server.tell(s, getSelf());
                    }
                })
                .match(SearchResponse.class, response -> {   //odpowiedzi od serwera na zapytania klienta
                    System.out.println("Title: " + response.getTitle());
                    System.out.println("Price: " + response.getPrice());
                })
                .match(ErrorResponse.class, response -> {
                    System.out.println(response.getMsg());
                })
                .build();
    }
}
