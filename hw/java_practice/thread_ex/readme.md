# A Simple Guide of Java Concurrency

# Concurrency & Parallelism

## Reference
[Stackoverflow: What is the difference between concurrency and parallelism?](https://stackoverflow.com/questions/1050222/what-is-the-difference-between-concurrency-and-parallelism)

## What Is Concurrency?
Concurrency is when two or more tasks can **start, run, and complete in overlapping time periods**. It **doesn't necessarily** mean they'll ever both be **running at the same instant**. For example, multitasking on a single-core machine.

## What Is Parallelism?
Parallelism is when tasks literally run at the same time, e.g., on a multicore processor.

# Modifer

## Reference
[W3C School: Java Modifiers](https://www.w3schools.com/java/java_modifiers.asp)


# Liveness

## Deadlock
Thread A need resource R1 and R2 and have got R1 already. Thread B need resource R1 and R2 and have got R2 already. Thread A and B will wait for each other.

## Livelock
Thread A response to thread B if thread A recieves a message from thread B. Also, thread B response to thread A if thread B recieves a message from thread A. Thread A and B will recieve and response to each other like ping-pong.

# Guarded Blocks

## Wait() & NotifyAll()
When a thread call `wait()`, the thread will sleep untill someone release the lock the call `notifyAll()`. The while loop `while (empty)` check before and after entering the sleep section to ensure the condition is always hold.

Drop.take()
```
public synchronized String take() {
        // Wait until message is
        // available.
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Toggle status.
        empty = true;
        // Notify producer that
        // status has changed.
        notifyAll();
        return message;
    }
```

Drop.take()
```
public synchronized void put(String message) {
        // Wait until message has
        // been retrieved.
        while (!empty) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        // Toggle status.
        empty = false;
        // Store message.
        this.message = message;
        // Notify consumer that status
        // has changed.
        notifyAll();
    }
```

# Immutable Objects
An object is considered immutable if its state cannot change after it is constructed.

## A Synchronized Class Example
For eexample, we have a class that contains synchronized method `getRGB()` and `getName()`.
```
public class SynchronizedRGB {
    public void set(int red, int green, int blue, String name) {
        check(red, green, blue);
        synchronized (this) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }
    }

    public synchronized int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public synchronized String getName() {
        return name;
    }
}
```

However, if there is another thread executing `color.set()` after statement1 and 2, the result would be wrong.

```
int myColorInt = color.getRGB();      //Statement 1
String myColorName = color.getName(); //Statement 2
```

As a result, we need to guarantee 2 statements are atomic like following code. The result would be correct.

```
synchronized (color) {
    int myColorInt = color.getRGB();
    String myColorName = color.getName();
} 
```

## A Strategy for Defining Immutable Objects


# High Level Concurrency Objects
