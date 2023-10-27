public class Fork {
    private volatile boolean isFree = true;

    public synchronized boolean busy() {
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
