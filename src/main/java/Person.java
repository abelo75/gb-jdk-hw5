import java.util.concurrent.CountDownLatch;

public class Person extends Thread {
    private final Fork leftFork;
    private final Fork rightFork;
    private int count = 0;
    private final String name;
    private final CountDownLatch latch;

    @Override
    public void run() {
        while (count < Settings.NUMBER_OF_EATS) {
            try {
                think();
                if (eat()) {
                    count++;
                }
            } catch (InterruptedException e) {
                System.out.println("Person.run() interrupted");
            }
        }
        latch.countDown();
    }

    public Person(Fork leftFork, Fork rightFork, String name, CountDownLatch latch) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.name = name;
        this.latch = latch;
    }

    public boolean eat() throws InterruptedException {
        boolean leftFree = leftFork.busy();
        if (!leftFree) {
            System.out.printf(" -- %s Не удалось взять левую вилку\n", name);
            return false;
        }
        boolean rightFree = rightFork.busy();
        if (!rightFree) {
            leftFork.free();
            System.out.printf(" -- %s Не удалось взять правую вилку, кладем левую\n", name);
            return false;
        }
        sleep(Settings.SLEEP_TIME);
        System.out.printf("%s ест %s раз\n", name, count+1);
        leftFork.free();
        sleep(Settings.SLEEP_TIME);
        rightFork.free();
        sleep(Settings.SLEEP_TIME);
        return true;
    }

    public void think() throws InterruptedException {
        System.out.printf("%s думает\n", name);
        sleep(Settings.SLEEP_TIME);
    }
}
