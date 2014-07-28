package com.theoriginalbit.minecraft.moarperipherals.dictionary;

public abstract class RestartableTask {

    public final String taskName;
    private Thread thread;
    private volatile boolean restart;
    private volatile boolean stopped;

    public RestartableTask(String name) {
        taskName = name;
    }

    private void start() {
        thread = new Thread(taskName) {
            @Override
            public void run() {
                do {
                    while (stopped) {
                        try {
                            Thread.sleep(1L);
                        } catch (InterruptedException ignored) {}
                    }
                    if (!restart) {
                        execute();
                    }
                } while (!finish());
            }
        };
        thread.start();
    }

    private synchronized boolean finish() {
        if (restart) {
            restart = false;
            return false;
        } else {
            clearTasks();
            return true;
        }
    }

    public synchronized boolean running() {
        return !(stopped && restart);
    }

    public void clearTasks() {
        thread = null;
    }

    public synchronized void restart() {
        if (thread != null) {
            stopped = false;
            restart = true;
        } else {
            start();
        }
    }

    /**
     * Stops execution until restart is called. If a thread is running, the restart flag will be set, and the thread will be paused once the execute method exits.
     */
    public synchronized void stop() {
        if(thread != null) {
            stopped = true;
            restart = true;
        }
    }

    public abstract void execute();

}
