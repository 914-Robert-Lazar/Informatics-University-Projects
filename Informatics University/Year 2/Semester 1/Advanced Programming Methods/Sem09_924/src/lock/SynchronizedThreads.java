package lock;

import java.util.concurrent.*;


public class SynchronizedThreads {

    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    Integer nr = 0;

    private void synchStmts() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (map) {//object level lock
                        nr++; //race condition
                        System.out.println("Thread name: " + Thread.currentThread() + " id: " + Thread.currentThread().getId() + "  integer: " + nr);
                        map.put(Thread.currentThread().getName(), String.valueOf(nr));
                    }
                    System.out.println(" id: " + Thread.currentThread().getId() + "  integer: " + map.get(Thread.currentThread().getName()));
                }
            });
        }
            executorService.shutdown();


        }

        //object level lock
        private  void synchMethod () throws ExecutionException, InterruptedException {
            ExecutorService executorService = Executors.newFixedThreadPool(3);

            for (int i = 0; i < 5; i++) {
                executorService.execute(new Runnable() {
                    @Override
                    public synchronized void run() { //object level lock
                        //synchronized (this) {
                        nr++; //race condition
                        System.out.println("Thread name: " + Thread.currentThread() + " id: " + Thread.currentThread().getId() + "  integer: " + nr);
                        map.put(Thread.currentThread().getName(), String.valueOf(nr));
                        System.out.println(" id: " + Thread.currentThread().getId() + "  integer: " + map.get(Thread.currentThread().getName()));
                        // }
                    }

                });

            }
            executorService.shutdown();


        }


        public static void main (String[]v) throws ExecutionException, InterruptedException {

            SynchronizedThreads app = new SynchronizedThreads();
            app.synchStmts();
            //app.synchMethod();

            Thread.sleep(100);
            app.map.entrySet().stream().forEach(s-> System.out.println(s.getKey() +" "+ s.getValue()));

        }

    }



