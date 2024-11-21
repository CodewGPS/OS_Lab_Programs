import java.util.*;
import java.util.concurrent.Semaphore;
public class prodC {
    private static final int BUFFER_SIZE=5;
    private static final Queue<Integer> q=new LinkedList<>();
    private static final Semaphore empty=new Semaphore(BUFFER_SIZE);    
    private static final Semaphore full=new Semaphore(0);
    private static final Semaphore mutex=new Semaphore(1);
    
    public static void main(String[] args) {
        /*Thread producer=new Thread(() -> {
            try{
                for(int i=1;i<10;i++){
                    empty.acquire();
                    mutex.acquire();
                    q.add(i);
                    System.out.println("Produced item" + i);
                    mutex.release();
                    full.release();
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i < 10; i++) {
                    full.acquire();    // Wait for an item to be available in the queue
                    mutex.acquire();   // Acquire mutual exclusion lock to access the queue
        
                    int item = q.poll();  // Consume an item from the queue
                    System.out.println("Consumed item " + item);
        
                    mutex.release();    // Release the mutual exclusion lock
                    empty.release();    // Notify the producer that there is space available
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Properly handle thread interruption
                e.printStackTrace();
            }
        }); */


        Thread producer = new Thread(() -> {
            try{
                for(int i=1;i<=10;i++){
                empty.acquire();
                mutex.acquire();
                q.add(i);
                System.out.println("Produced item "+ i);
                mutex.release();
                full.release();
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        });

        Thread consumer=new Thread(() -> {
            try{
                for(int i=1;i<=10;i++){
                full.acquire();
                mutex.acquire();
                int item=q.poll();
                System.out.println("Consumed item " + item);
                mutex.release();
                empty.release();
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        });
            
        producer.start();
        consumer.start();

    }
}
