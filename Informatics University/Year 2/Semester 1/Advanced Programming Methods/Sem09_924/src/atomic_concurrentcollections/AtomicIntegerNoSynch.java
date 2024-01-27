package atomic_concurrentcollections;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerNoSynch {

    public static void main(String v[]) throws InterruptedException {
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, 1000)
                .forEach(i -> {
                    Runnable task = () ->
                    {
                        atomicInt.updateAndGet(n -> n + 2); //thread-safe without synchronization
                    };
                    //System.out.println(Thread.currentThread()+" "+atomicInt.get());
                    executor.submit(task);
                    System.out.println(Thread.currentThread().getId()+" "+atomicInt.get());
                });
        executor.shutdown();
        Thread.sleep(100);
        System.out.println(atomicInt.get()); // => 2000
    }
}