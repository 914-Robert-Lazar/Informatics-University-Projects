package atomic_concurrentcollections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AtomicIntegerThreads {

    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    AtomicInteger nr = new AtomicInteger(0);

    Lock lock = new ReentrantLock();
    private void lockStmts() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i=0; i<5; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() { //no lock is required
                    lock.lock();
                    //System.out.println("Thread name: " + Thread.currentThread() + " id: " + Thread.currentThread().getId() + "  integer: " + nr.updateAndGet(x->x+1));
                    map.put(Thread.currentThread().getName(), String.valueOf(nr.updateAndGet(x->x+1)));//changes can occur if not locked
                    lock.unlock();
                    System.out.println("name: "+Thread.currentThread().getName()+" id: " + Thread.currentThread().getId() + "  integer: " + map.get(Thread.currentThread().getName()));
                }
            });

        }
        executorService.shutdown();
    }



    public static void main(String[] v) throws ExecutionException, InterruptedException {

        AtomicIntegerThreads app= new AtomicIntegerThreads();
        app.lockStmts();

        Thread.sleep(100);
        app.map.entrySet().stream().forEach(s-> System.out.println(s.getKey() +" "+ s.getValue()));

    }

}
