package thread_ex.queue;
import java.util.Random;

abstract class Greet_Abs{
    public String str = "Hi, form producer";
    public abstract void greet();
}

class Hi extends Greet_Abs{
    public void greet(){
        System.out.format("%s\n", this.str);
    }
}

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        String importantInfo[] = {
            "Mares eat oats",
            "Does eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
        };
        Random random = new Random();

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            
            Hi hi = new Hi();
            hi.greet();
            // Greet.hi();
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
        drop.put("DONE");
    }
}