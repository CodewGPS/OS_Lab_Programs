import java.util.concurrent.*;

public class ReaderWriter {
    private static final Semaphore wrt = new Semaphore(1);
    private static final ReentrantLock mutex = new ReentrantLock();
    private static int cnt = 1;
    private static int numReader = 0;

    public static class Writer implements Runnable {
        private final int wno;

        public Writer(int wno) {
            this.wno = wno;
        }

        @Override
        public void run() {
            try {
                wrt.acquire();
                cnt = cnt * 2;
                System.out.println("Writer " + wno + " modified cnt to " + cnt);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                wrt.release();
            }
        }
    }

    public static class Reader implements Runnable {
        private final int rno;

        public Reader(int rno) {
            this.rno = rno;
        }

        @Override
        public void run() {
            try {
                mutex.lock();
                numReader++;
                if (numReader == 1) {
                    wrt.acquire(); // Block writer if this is the first reader
                }
            } finally {
                mutex.unlock();
            }

            // Reading section
            System.out.println("Reader " + rno + ": read cnt as " + cnt);

            try {
                mutex.lock();
                numReader--;
                if (numReader == 0) {
                    wrt.release(); // Wake up writer if this is the last reader
                }
            } finally {
                mutex.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] readThreads = new Thread[10];
        Thread[] writeThreads = new Thread[5];

        // Creating 10 readers and 5 writers
        for (int i = 0; i < 10; i++) {
            final int index = i + 1;
            readThreads[i] = new Thread(new Reader(index));
        }
        for (int i = 0; i < 5; i++) {
            final int index = i + 1;
            writeThreads[i] = new Thread(new Writer(index));
        }

        // Starting the threads
        for (Thread readThread : readThreads) {
            readThread.start();
        }
        for (Thread writeThread : writeThreads) {
            writeThread.start();
        }

        // Waiting for all threads to finish
        for (Thread readThread : readThreads) {
            readThread.join();
        }
        for (Thread writeThread : writeThreads) {
            writeThread.join();
        }
    }
}
