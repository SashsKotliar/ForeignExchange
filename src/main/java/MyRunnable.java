public abstract class MyRunnable implements Runnable {
    private boolean running = true;
    private static final int SPEED =16;
    private Object object;

    public MyRunnable(Object o) {
        this.object= o.getClass();
    }

    public void stop() {
        running = false;
    }

    public abstract void _run();

    @Override
    public void run() {
        while (running) {
            this._run();
            Utils.sleep(SPEED);
        }
    }
}
