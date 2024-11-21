
import java.util.concurrent.Semaphore;
public class readerW {
    private static int readCount=0;
    private static final Semaphore resource=new Semaphore(1);
    private static final Semaphore mutex= new Semaphore(1);

    public static void main(String[] args) {
        Runnable reader=() ->{
            try{
                mutex.acquire();
                readCount++;
                if(readCount==1) resource.acquire();
                mutex.release();

                System.out.println(Thread.currentThread().getName() + "is reading");
                Thread.sleep(1000);

                mutex.acquire();
                readCount--;
                if(readCount==0) resource.release();
                mutex.release();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        };

        Runnable writer=() -> {
            try{
                resource.acquire();
                System.out.println(Thread.currentThread().getName() + "is writing");
                Thread.sleep(1000);
                resource.release();

            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        };

        Thread r1=new Thread(reader,"reader1");
        
        Thread r2=new Thread(reader,"reader2");
        Thread w1=new Thread(writer,"writer1");

        r1.start();
        r2.start();
        w1.start();

    }
}
