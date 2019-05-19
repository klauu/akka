package client;

import akka.actor.*;


public class Client extends AbstractActor{

    final ActorSelection server = getContext().actorSelection("akka.tcp://library@127.0.0.1:8894/user/server");



    @Override
    public Receive createReceive() {  //odpowiedzi od serwera na zapytania klienta
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("search")) {
                        server.tell(s, getSelf());
                    }
                    else if(s.startsWith("respond")){
                        System.out.println(s);
                    }
                })
                .build();
    }
}
