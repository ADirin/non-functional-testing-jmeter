package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DemoAppTest {

    @Test
    void testMainRunsWithoutError() {
        assertDoesNotThrow(() -> DemoApp.main(new String[]{}));
    }

    @Test
    void testRunWorkloadHandlesInterruption() throws InterruptedException {
        DemoApp app = new DemoApp();
        Thread worker = new Thread(() -> app.runWorkload(5000));
        worker.start();
        Thread.sleep(50);
        worker.interrupt();
        worker.join(1000);
        assertTrue(!worker.isAlive(), "Worker thread should exit cleanly after interruption");
    }
}