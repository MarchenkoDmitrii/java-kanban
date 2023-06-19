package test;

import managers.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @BeforeEach
    public void setup() {
        manager = new FileBackedTasksManager();
    }
}