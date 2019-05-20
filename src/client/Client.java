package client;

import akka.actor.*;

import resources.OrderResponse;
import resources.SearchResponse;


public class Client extends AbstractActor{

    final ActorSelection server = getContext().actorSelection("akka.tcp://library@127.0.0.1:8894/user/server");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> { //z konsoli
                    if (s.startsWith("search") || s.startsWith("order") || s.startsWith("stream")) {
                        server.tell(s, getSelf());
                    }else {
                        System.out.println(s);
                    }
                })
                .match(SearchResponse.class, response -> {
                    if(response.getPrice() == 0){
                        System.out.println("Book is not in the database");
                    }else{
                        System.out.println("Title: " + response.getTitle() + ", Price: " + response.getPrice());
                    }
                })
                .match(OrderResponse.class, response -> {
                    if(response.isSucceed()){
                        System.out.println("Order succeed: " + response.getTitle());
                    }else{
                        System.out.println("Order didn't succeed: " + response.getTitle());
                    }
                })
                .build();
    }
}
