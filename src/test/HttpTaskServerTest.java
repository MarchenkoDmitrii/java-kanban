package test;


import com.google.gson.Gson;


import managers.InMemoryTaskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    private static final String URL = "http://localhost:8080/";
    private HttpClient client = HttpClient.newHttpClient();
    private InMemoryTaskManager taskManager = new InMemoryTaskManager();

    private Gson gson = new Gson();

    @BeforeEach
    public void initData() throws IOException, InterruptedException {

        sendPostRequest(taskManager.createTask(new Task("Task 1", "test")));
        sendPostRequest(taskManager.createTask(new Task("Task 2", "test")));
        sendPostRequest(taskManager.createTask(new Task("Task 3", "test")));

    }

    @Test
    public void testPost() throws IOException, InterruptedException {
        HttpResponse<String> response = sendPostRequest(taskManager.createTask(new Task("Task 4", "test")));
        assertEquals(201, response.statusCode());
        response = sendGetRequest();
        assertEquals(200, response.statusCode());
        List<Task> users = new ArrayList<>(taskManager.tasks.values());
        assertEquals(4, users.size());
    }

    @Test
    public void testGet() throws IOException, InterruptedException {
        HttpResponse<String> response = sendGetRequest(1);
        assertEquals(200, response.statusCode());
        Task task = gson.fromJson(response.body(), Task.class);
        assertEquals("Task 1", task.getName());
    }

    @Test
    public void testDelete() throws IOException, InterruptedException {
        HttpResponse<String> response = sendDeleteRequest(1);
        assertEquals(204, response.statusCode());

        response = sendGetRequest();
        assertEquals(200, response.statusCode());
        List<Task> users = new ArrayList<>(taskManager.tasks.values());
        assertNotNull(taskManager.getTaskById(2));
        assertNotNull(taskManager.getTaskById(3));
    }


    private HttpResponse<String> sendPostRequest(Task user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                .uri(URI.create(URL+user.getId()))
                .header("Content-Type", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        return client.send(request, handler);
    }

    private HttpResponse<String> sendDeleteRequest() throws IOException, InterruptedException {
        return client.send(HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(URL))
                .build(), HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> sendDeleteRequest(long id) throws IOException, InterruptedException {
        return client.send(HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(URL + "/" + id))
                .build(), HttpResponse.BodyHandlers.ofString());
    }


    private HttpResponse<String> sendGetRequest() throws IOException, InterruptedException {
        return client.send(HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL))
                .build(), HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> sendGetRequest(long id) throws IOException, InterruptedException {
        return client.send(HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL + "/" + id))
                .build(), HttpResponse.BodyHandlers.ofString());
    }

}