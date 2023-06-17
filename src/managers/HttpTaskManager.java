package managers;

import KVServer.KVTaskClient;
import com.google.gson.Gson;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TypeTask;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class HttpTaskManager extends FileBackedTasksManager implements TaskManager {
    Gson gson = new Gson();
    private KVTaskClient kvTaskClient;

    public HttpTaskManager(String url) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(url);

    }

    @Override
    public void save() throws ManagerSaveException {
        try {
        for (HashMap<Integer, ? extends Task> map : Arrays.asList(tasks, epics, subTaskHashMap)) {
            for (Task value : map.values()) {
                kvTaskClient.put(String.valueOf(value.getId()),gson.toJson(value));
            }
        }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new ManagerSaveException("Error saving tasks", e);
        }
    }


    public HttpTaskManager load() throws IOException, URISyntaxException, InterruptedException {
        HttpTaskManager httpTaskManager = new HttpTaskManager(kvTaskClient.getServerUrl());
        int id = 1;
        if (kvTaskClient.load(String.valueOf(id)).isEmpty()) return null;

        while (kvTaskClient.load(String.valueOf(id)).isEmpty()) {
            Task task = gson.fromJson(kvTaskClient.load(String.valueOf(id)), Task.class);
            if (task.getTypeTask() == TypeTask.Task) {
                tasks.put(task.getId(), task);

                // добавляем задачу в ту хэш-мапу, которая соответствует типу задачи
            } else if (task.getTypeTask() == TypeTask.Epic) {
                Epic epic = new Epic(task.getId(), task.getName(), task.getDescription());
                epic.setId(task.getId());
                epics.put(epic.getId(), epic);

            } else if (task.getTypeTask() == TypeTask.SubTask) {
                SubTask subTask = new SubTask(task.getId(), task.getName(), task.getDescription(), task.getStatus(),
                        task.getDuration(),
                        task.getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), task.epicId);
                epics.get(subTask.getEpicsID()).getSubTasks().add(subTask.getId());
                subTask.setId(task.getId());
                subTaskHashMap.put(subTask.getId(), subTask);
            }
            id++;
        }
        return httpTaskManager;
    }
}