import java.util.*;
public class PageReplaceFifo {

    private static int calculate(int arr[],int size){
        Queue<Integer> q=new LinkedList<>();
        int ans=0;
        for (int i = 0; i < arr.length; i++) {
           
            if (!q.contains(arr[i])) {
                ans++; 

                if (q.size() == size) {
                    q.poll();
                }
                
                
                q.offer(arr[i]);
            }
        }


        return ans;

    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of page references: ");
        int pages=sc.nextInt();
        int arr[]=new int[pages];
        System.out.println("Enter the elements in the array");
        for(int i=0;i<arr.length;i++){
            arr[i]=sc.nextInt();
        }
        System.out.println("Enter the frame size: ");
        int frameSize=sc.nextInt();
        int res=calculate(arr,frameSize);

        System.out.println("The number of page misses are " + res);

    }
}
