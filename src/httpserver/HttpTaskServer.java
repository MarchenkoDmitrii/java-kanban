package httpserver;

import handlers.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;


public class HttpTaskServer {
    private static final int PORT = 8080;
    public static void main(String[] args) throws Exception {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler());
        httpServer.createContext("/tasks/", new AllTaskHandler());
        httpServer.createContext("/tasks/epic", new EpicHandler());
        httpServer.createContext("/tasks/subtask", new SubTaskHandler());
        httpServer.createContext("/tasks/history", new HistoryHandler());
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }

}
