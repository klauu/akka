package server;

import akka.actor.AbstractActor;
import resources.OrderRequest;
import resources.OrderResponse;

import java.io.*;

public class OrderWorker extends AbstractActor{
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OrderRequest.class, request -> {
                    boolean succeed = true;
                    try{
                        saveOrder(request.getTitle());
                    }catch (FileNotFoundException e){
                        succeed = false;
                    }

                    OrderResponse response = new OrderResponse(request.getTitle(), succeed);
                    getSender().tell(response, getSelf());
                    getContext().stop(getSelf());

                    System.out.println("OrderWorker");
                })
                .build();
    }

    private void saveOrder (String title) throws IOException {
        FileWriter fileWriter = new FileWriter("database/orders.txt", true);

        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(title);

        printWriter.close();
    }
}


