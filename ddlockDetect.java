import java.util.Arrays;

public class ddlockDetect{
    public static void main(String[] args) {
        int nProcesses = 3; // Number of processes
        int nResources = 3; // Number of resource types
        
        // Allocation matrix (how much each process is holding)
        int[][] allocation = {
            {0, 1, 0}, // Process 0
            {2, 0, 0}, // Process 1
            {3, 0, 2}  // Process 2
        };
        
        // Request matrix (how much each process still needs)
        int[][] request = {
            {0, 0, 0},  // Process 0
            {2, 0, 2},  // Process 1
            {0, 0, 2}   // Process 2
        };

        int[] available = {5,4,5};
        
        boolean res=detectDeadlock(nProcesses, nResources, allocation, request, available);
        System.out.println(res);
    }
    
    public static boolean detectDeadlock(int nProcesses, int nResources, int [][] allocation, int [][] request, int [] available){
        int [] work=Arrays.copyOf(available, available.length);
        boolean [] finished= new boolean[nProcesses];

        while(true){
            boolean found=false;

            for(int i=0;i<nProcesses;i++){
                if(!finished[i] && isValidRequest(request[i], work)){
                    for(int j=0;j<nResources;j++){
                        work[j]+=allocation[i][j];
                    }
                    finished[i]=true;
                    found=true;
                }
            }

            if(!found) break;
        }

        for(boolean val : finished){
            if(val==false) return true; //deadlock is detected
        }

        return false;

    }

    public static boolean isValidRequest(int [] request, int [] work){
        for(int i=0;i<request.length;i++){
            if(request[i]>work[i]) return false;
        }

        return true;
    }
}