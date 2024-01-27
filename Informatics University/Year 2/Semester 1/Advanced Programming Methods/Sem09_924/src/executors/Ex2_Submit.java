package executors;

import java.util.concurrent.*;

//run immediately

public class Ex2_Submit {
    public static void main(String v[]) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //3 threads for executing tasks are created

        for (int i = 0; i < 5; i++){
//            Future future = executorService.submit(new Callable() {
//                public Object call() throws Exception {
//                    //System.out.println("Asynchronous Callable " + Thread.currentThread() + " id: " + Thread.currentThread().getId());
//                    return "Callable Result thread id: " + Thread.currentThread() + "id: "+Thread.currentThread().getId();
//                }
//            });
            Future future = executorService.submit(
                    ()->{return "Callable Result thread id: " + Thread.currentThread() + "id: "+Thread.currentThread().getId();
            });
            System.out.println("future.get() = " + future.get());
        }


//        Future result =
//                executorService.submit(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        System.out.println("Another asynchronous task name: "+Thread.currentThread()+ " id: "+Thread.currentThread().getId());
//                        //return new Future<String>(""+Thread.currentThread().getId());
//                    }
//                });

        Future result =
                executorService.submit(()->{
                        //System.out.println("Another asynchronous task name: "+Thread.currentThread()+ " id: "+Thread.currentThread().getId());
                        return ""+Thread.currentThread().getId();
                });

        executorService.shutdown();
        System.out.println("result.get() = " + result.get());
        if (result.get()==null)System.out.println("successfully executed");//get()==null=> finished, result.isDone()==true

    }
}


