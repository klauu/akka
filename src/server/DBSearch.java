package server;

import akka.actor.AbstractActor;

import java.io.*;

import resources.DBErrorResponse;
import resources.SearchDBRequest;
import resources.SearchResponse;

public class DBSearch extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchDBRequest.class, request -> {
                    try{
                        SearchResponse response = new SearchResponse(request.getTitle(), searchPrice(request.getTitle(), request.getDatabase()));
                        getSender().tell(response, getSelf());
                    }catch (FileNotFoundException e){
                        DBErrorResponse response = new DBErrorResponse("database not available");
                        getSender().tell(response, getSelf());
                    }

                })
                .build();
    }

    private Integer searchPrice(String title, String database) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(database));

        int price = 0;
        while(true){
            String line = reader.readLine();
            if(line == null) break;
            if(line.contains(title)){
                String[] elems = line.split(" ");
                price = Integer.parseInt(elems[elems.length - 1]);
                break;
            }
        }

        reader.close();
        return price;
    }
}
