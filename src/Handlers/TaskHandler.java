package Handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.FileBackedTasksManager;
import managers.ManagerSaveException;
import managers.Managers;
import managers.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TaskHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private TaskManager taskManager;
    Gson gson = new Gson();

    public TaskHandler() throws IOException, InterruptedException {
        this.taskManager = Managers.getFile();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()) {
            case "GET": {
                getTask(exchange);
                break;
            }
            case "POST": {
                createTask(exchange);
                break;
            }
            case "DELETE": {
                removeTask(exchange);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }

        }

    public void removeTask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String[] params = query.split("=");
        int id = Integer.parseInt(params[1]);
        taskManager.removeTaskById(id);
        writeResponse(exchange,"Удалено",200);
    }

    public void getTask(HttpExchange exchange) throws IOException{
        String query = exchange.getRequestURI().getQuery();
        String[] params = query.split("=");
        int id = Integer.parseInt(params[1]);
        writeResponse(exchange,gson.toJson(taskManager.getTaskById(id)),200);
    }
    public void createTask(HttpExchange exchange) throws IOException{
        InputStream requestBody = exchange.getRequestBody();
        String requestBodyString = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
        try {
            writeResponse(exchange,gson.toJson(taskManager.createTask(gson.fromJson(requestBodyString,Task.class))),200);
        }catch (JsonSyntaxException e){
            writeResponse(exchange, "Некорректный формат JSON", 400);
        }
    }
    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if(responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }
}