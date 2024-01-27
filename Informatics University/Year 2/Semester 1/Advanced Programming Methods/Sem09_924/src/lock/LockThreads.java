package lock;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class LockThreads {

    HashMap<String, String> map = new HashMap<>();
    Integer nr = 0;

    Lock lock = new ReentrantLock();
    private void lockStmts() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i=0; i<5; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    nr++;
                    System.out.println("Thread name: " + Thread.currentThread() + " id: " + Thread.currentThread().getId() + "  integer: " + nr);
                    map.put(Thread.currentThread().getName(), String.valueOf(nr));//changes can occur if not locked
                    lock.unlock();
                    System.out.println(" id: " + Thread.currentThread().getId() + "  integer: " + map.get(Thread.currentThread().getName()));
                }
            });

        }
        executorService.shutdown();


    }



    public static void main(String[] v) throws ExecutionException, InterruptedException {

        LockThreads app= new LockThreads();
        app.lockStmts();

        Thread.sleep(100);
        app.map.entrySet().stream().forEach(s-> System.out.println(s.getKey() +" "+ s.getValue()));
    }


}
