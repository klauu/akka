package server;

import akka.actor.AbstractActor;
import resources.ErrorResponse;
import resources.SearchRequest;
import resources.SearchResponse;

import java.io.*;

public class DBWorker extends AbstractActor {

    @Override //TODO - code cleaning
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class, request -> {

                    boolean flag = true;
                    int price = 0;
                    try{
                        price = searchTitle(request.getTitle());
                    }
                    catch (FileNotFoundException e){
                        ErrorResponse res = new ErrorResponse("Databases not available");
                        getSender().tell(res, getSelf());
                        getContext().stop(getSelf());
                        flag = false;
                    }
                    if(flag){
                        if(price == 0){
                            ErrorResponse res = new ErrorResponse("Book is not in the database");
                            getSender().tell(res, getSelf());
                            getContext().stop(getSelf());
                        }else{
                            SearchResponse response = new SearchResponse(request.getTitle(), price); //TODO - przeszukiwanie
                            getSender().tell(response, getSelf());
                            getContext().stop(getSelf());
                        }
                    }
                })
                .build();
    }

    private Integer searchTitle(String title) throws IOException { //TODO przenieść do innego aktora????

        BufferedReader reader = new BufferedReader(new FileReader("database/db1.txt"));

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
