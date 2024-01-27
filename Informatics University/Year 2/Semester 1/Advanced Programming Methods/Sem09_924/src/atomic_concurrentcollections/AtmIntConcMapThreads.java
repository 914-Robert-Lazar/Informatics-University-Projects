package atomic_concurrentcollections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 1. create a concurrent hash map <String=thread name, String= toString(integer_value)>.
 * 2. create an atomic integer (nr).
 * 3. define a list of 7 callables that concurrently access the map and put/update date <thread_name, some int incremented value>
 * 3.1. a callable returns a string = "thread_name "+ used_int_value
 * 4. use an executor service to invoke all callables using 3 threads
 * 5. collect the results of the executed threads and print them
 */
public class AtmIntConcMapThreads {

    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    AtomicInteger nr= new AtomicInteger(0);

    private void concurrentMap() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Callable<String>> callables = new ArrayList<Callable<String>>();
        for (int i=0; i<7; i++) {
            callables.add(new Callable<String>() {
                public String call() throws Exception {//no sync or lock is required AtomicInteger is used
                    //synchronized (this) {
                    //lock.unlock();
                    map.put(Thread.currentThread().getName(), String.valueOf(nr.updateAndGet(x->x+1)));//changes can occur if not locked
                    System.out.println("Thread name: " + Thread.currentThread().getName() + " id: " + Thread.currentThread().getId() + " atomic integer: " + map.get(Thread.currentThread().getName()));
                    return "name: "+Thread.currentThread().getName()+" id: " + Thread.currentThread().getId() + "  integer: " + map.get(Thread.currentThread().getName());
                    //}
                    //lock.unlock();
                }
            });
        }
        executorService.invokeAll(callables)
                .stream()
                .map(f-> {
                    try {
                        return f.get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }).forEach(System.out::println);

        executorService.shutdown();
    }


    public static void main(String[] v) throws ExecutionException, InterruptedException {

        AtmIntConcMapThreads app= new AtmIntConcMapThreads();
        app.concurrentMap();

        }

}