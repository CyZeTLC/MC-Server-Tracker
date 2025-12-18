package de.cyzetlc.mst;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.cyzetlc.mst.console.Console;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MSTracker {
    private static final String API_URL = "https://api.mcsrvstat.us/2/";

    private static Console console;

    public static void main(String[] args) throws IOException {
        if (args.length != 2 || !args[0].equals("-ip")) {
            System.err.println("Use mst -ip <SERVER_IP>");
            return;
        }

        console = new Console(args);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            try {
                trackServer(args[1]);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    public static void trackServer(String ip) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + ip))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        System.out.println("Daten empfangen am " + java.time.LocalDateTime.now());
        System.out.println(json.get("ip").getAsString());
    }
}