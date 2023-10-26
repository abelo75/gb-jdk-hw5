public class Fork {
    private volatile boolean isFree = true;

    public synchronized boolean busy() {
        int count = 0;
        while (!isFree && count++ < Settings.WAIT_COUNT) {
            try {
                wait(Settings.WAIT_TIME);
            } catch (InterruptedException e) {
                System.out.println("Fork.busy() interrupted");
            }
        }
        if (!isFree) {
            return false;
        }
        isFree = false;
        return true;
    }

    public synchronized void free() {
        isFree = true;
        notifyAll();
    }
}
