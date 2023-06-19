package kvserver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final String serverUrl;
    private final String apiToken;

    public String getServerUrl() {
        return serverUrl;
    }

    public String getApiToken() {
        return apiToken;
    }

    public KVTaskClient(String serverUrl) throws IOException, InterruptedException {
        this.serverUrl = serverUrl;
        this.apiToken = registerAndGetApiToken();
    }
    private String registerAndGetApiToken() throws IOException, InterruptedException {
        String url = serverUrl + "register";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        // отправляем запрос и получаем ответ от сервера
        HttpResponse<String> response = client.send(request, handler);

        return response.body();
    }


    public void put(String key, String json) throws IOException, InterruptedException, URISyntaxException {
        String url = serverUrl + "save/" + key + "?API_TOKEN=" + apiToken;
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        if (response.statusCode() != 200) {
            throw new IOException("Failed to save data (status code " + response.statusCode() + ")");
        }
    }

    public String load(String key) throws IOException, InterruptedException, URISyntaxException {
        String url = serverUrl + "load/" + key + "?API_TOKEN=" + apiToken;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        // отправляем запрос и получаем ответ от сервера
        HttpResponse<String> response;
        try {
            response = client.send(request, handler);
        }catch (IOException e){
            return "";
        }
            return response.body();
    }

}
