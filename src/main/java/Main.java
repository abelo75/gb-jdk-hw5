import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        int size = Settings.NUMBER_OF_PEOPLE;
        CountDownLatch latch = new CountDownLatch(size);

        List<Fork> forks = new ArrayList<>(size);
        List<Person> people = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            forks.add(new Fork());
        }
        for (int i = 0; i < size; i++) {
            people.add(new Person(forks.get(i), forks.get((i + 1) % size), Settings.PERSON_PREFIX + (i+1), latch));
        }

        for (int i = 0; i < size; i++) {
            people.get(i).start();
        }
        try {
            latch.await();
            System.out.printf("Все покушали по %s раз(а)", Settings.NUMBER_OF_EATS);
        } catch (InterruptedException e) {
            System.out.println("Main.main() interrupted");
        }
    }
}
