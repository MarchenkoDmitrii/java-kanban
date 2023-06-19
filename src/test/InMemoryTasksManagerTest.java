package test;

import managers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void setup() {
        manager = new InMemoryTaskManager();
    }
}