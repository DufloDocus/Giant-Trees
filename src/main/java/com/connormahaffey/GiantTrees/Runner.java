package com.connormahaffey.GiantTrees;

public abstract class Runner implements Runnable {

    public static final int WAITING_TIME = 100;
    private boolean done = false;

    /**
     * Wait a WAITING_TIME (milliseconds)
     */
    public static void pause() {
        pause(WAITING_TIME);
    }

    public static void pause(final int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            GiantTrees.logError("Could not wait!", e);
        }
    }

    @Override
    public void run() {
        doJob();
        this.done = true;
    }

    public boolean isDone() {
        return this.done;
    }

    protected abstract void doJob();
}
