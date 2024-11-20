import java.util.*;
import java.util.concurrent.Semaphore;

class Buffer {
    private static final int MAX_SIZE = 5; // Maximum size of the buffer
    private final LinkedList<Integer> buffer = new LinkedList<>();
    private final Semaphore empty = new Semaphore(MAX_SIZE);  // Initially MAX_SIZE empty slots
    private final Semaphore full = new Semaphore(0);          // Initially no full slots
    private final Semaphore mutex = new Semaphore(1);         // Mutex to control access to the buffer
    
    // Producer method
    public void produce(int item) throws InterruptedException {
        empty.acquire();  // Wait for an empty slot
        mutex.acquire();  // Lock the buffer for exclusive access
        
        buffer.add(item); // Add the item to the buffer
        System.out.println("Produced: " + item);
        
        mutex.release();  // Release the buffer lock
        full.release();   // Signal that there is one more full slot
    }
    
    // Consumer method
    public void consume() throws InterruptedException {
        full.acquire();   // Wait for a full slot
        mutex.acquire();  // Lock the buffer for exclusive access
        
        int item = buffer.removeFirst(); // Remove the item from the buffer
        System.out.println("Consumed: " + item);
        
        mutex.release();  // Release the buffer lock
        empty.release();  // Signal that there is one more empty slot
    }
}

class Producer extends Thread {
    private final Buffer buffer;
    
    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                buffer.produce(i);
                Thread.sleep(1000); // Simulate time taken to produce an item
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer extends Thread {
    private final Buffer buffer;
    
    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                buffer.consume();
                Thread.sleep(1500); // Simulate time taken to consume an item
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ProducerConsumerExample {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        
        producer.start();
        consumer.start();
    }
}
