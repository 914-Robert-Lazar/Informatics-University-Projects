package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//run immediately

public class Ex1_Execute {
    public static void main(String v[]){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
                        //3 threads for executing tasks are created

        for (int i=0;i<=10; i++)
//            executorService.execute(new Runnable() {
//                public void run() {
//                    System.out.println("Asynchronous task name: "+Thread.currentThread()+ " id: "+Thread.currentThread().getId());
//                }
//            });
            executorService.execute(()->System.out.println("Another asynchronous task name: "+Thread.currentThread()+ " id: "+Thread.currentThread().getId()));


//        executorService.execute(new Runnable() {
//            public void run() {
//                System.out.println("Another asynchronous task name: "+Thread.currentThread()+ " id: "+Thread.currentThread().getId());
//            }
//        });
        executorService.execute(()->System.out.println("Another asynchronous task name: "+Thread.currentThread()+ " id: "+Thread.currentThread().getId()));
        System.out.println(Thread.activeCount());

        executorService.shutdown();
    }
}
