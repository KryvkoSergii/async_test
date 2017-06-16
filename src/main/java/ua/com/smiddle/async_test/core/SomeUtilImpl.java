package ua.com.smiddle.async_test.core;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ksa on 16.06.17.
 * @project async_test
 */
@Component
public class SomeUtilImpl implements SomeUtil {
    private Map<String, Task> registry = new ConcurrentHashMap<>();

    @Override
    @Async("SDProcessorThreadPool")
    public void task1() {
        String id = generateID();
        registry.put(id, new Task(id, State.RUNNING));
        while(registry.get(id).isAllowedRunning) {
            try {
                registry.get(id).setState(State.WAITING);
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
            }
            registry.get(id).setState(State.RUNNING);
            registry.get(id).setProcess((int) ((int) 10*Math.random()));
            System.out.println("TASK#1-");
        }
        registry.get(id).setState(State.STOPPED);
        registry.remove(id);
    }

    @Override
    @Async("SDProcessorThreadPool")
    public void task2() {
        String id = generateID();
        registry.put(id, new Task(id, State.RUNNING));
        while(registry.get(id).isAllowedRunning) {
            registry.get(id).setAllowedRunning(false);
            try {
                registry.get(id).setState(State.WAITING);
                Thread.currentThread().sleep(500);

            } catch (InterruptedException e) {
            }
            registry.get(id).setState(State.RUNNING);
            registry.get(id).setProcess((int) ((int) 10*Math.random()));
            System.out.println("TASK#2-");
        }
        registry.get(id).setState(State.STOPPED);
        registry.remove(id);
    }

    @Override
    @Async("SDProcessorThreadPool")
    public void task3() {
        String id = generateID();
        registry.put(id, new Task(id, State.RUNNING));
        while(registry.get(id).isAllowedRunning) {
            try {
                registry.get(id).setState(State.WAITING);
                Thread.currentThread().sleep(200);
            } catch (InterruptedException e) {
            }
            registry.get(id).setState(State.RUNNING);
            registry.get(id).setProcess((int) ((int) 10*Math.random()));
            System.out.println("TASK#3-");
        }
        registry.get(id).setState(State.STOPPED);
        registry.remove(id);
    }

    @Override
    public Object getState() {
        return registry.values();
    }

    @Override
    public void stop(String id) {
        registry.get(id).setAllowedRunning(false);
    }

    class Task {
        private String id;
        private volatile State state;
        private volatile boolean isAllowedRunning;
        private volatile int process;

        public Task(final String id, State state) {
            this.id = id;
            this.state = state;
            this.isAllowedRunning = true;
        }

        public String getId() {
            return id;
        }

        public synchronized State getState() {
            return state;
        }

        public synchronized void setState(State state) {
            this.state = state;
        }

        public synchronized boolean isAllowedRunning() {
            return isAllowedRunning;
        }

        public synchronized void setAllowedRunning(boolean allowedRunning) {
            isAllowedRunning = allowedRunning;
        }

        public synchronized int getProcess() {
            return process;
        }

        public synchronized void setProcess(int process) {
            this.process = process;
        }
    }

    enum State {
        RUNNING, WAITING, STOPPED;
    }

    private static String generateID() {
        return UUID.randomUUID().toString();
    }
}
