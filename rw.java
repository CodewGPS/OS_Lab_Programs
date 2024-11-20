import java.util.concurrent.Semaphore;
class Main{
    private static int readCount = 0;
    private static final Semaphore mutex = new Semaphore(1);
    private static final Semaphore writeLock = new Semaphore(1); 
    static class Reader implements Runnable {
        @Override
        public void run() {
            try {
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    writeLock.acquire();
                }
                mutex.release();
                System.out.println(Thread.currentThread().getName() + " is reading.");
                Thread.sleep((long) (Math.random() * 1000));
                mutex.acquire();
                readCount--;
                if (readCount == 0) {
                    writeLock.release(); 
                }
                mutex.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    static class Writer implements Runnable {
        @Override
        public void run() {
            try {
                writeLock.acquire();
                System.out.println(Thread.currentThread().getName() + " is writing.");
                Thread.sleep((long) (Math.random() * 1000));
                writeLock.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args) {
        Thread[] readers = new Thread[5];
        Thread[] writers = new Thread[2];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Thread(new Reader(), "Reader-" + (i + 1));
            readers[i].start();
        }
        for (int i = 0; i < writers.length; i++) {
            writers[i] = new Thread(new Writer(), "Writer-" + (i + 1));
            writers[i].start();
        }
    }
}