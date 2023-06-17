package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.Managers;
import managers.TaskManager;

import java.io.IOException;

public class HistoryHandler implements HttpHandler {
    private TaskManager taskManager;

    public HistoryHandler() throws IOException, InterruptedException {
        this.taskManager = Managers.getFile();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
