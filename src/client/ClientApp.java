package client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import server.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ClientApp {
    public static void main(String[] args) throws Exception {

        File configFile = new File("client.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("client", config);
        final ActorRef client = system.actorOf(Props.create(Client.class), "client");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            client.tell(line, null);
        }
        system.terminate();
    }
}
