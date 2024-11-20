import java.util.Scanner;

class SJF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        int[] process = new int[n];         // Process IDs
        int[] arrivalTime = new int[n];     // Arrival times
        int[] burstTime = new int[n];       // Burst times
        int[] waitingTime = new int[n];     // Waiting times
        int[] turnaroundTime = new int[n];  // Turnaround times
        int[] completionTime = new int[n];  // Completion times

        // Input arrival and burst times
        System.out.println("Enter arrival time and burst time for each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " arrival time: ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Process " + (i + 1) + " burst time: ");
            burstTime[i] = sc.nextInt();
            process[i] = i + 1;  // Assign process IDs
        }

        // Sort processes based on burst time using SJF (non-preemptive)
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (burstTime[i] > burstTime[j]) {
                    // Swap burst times
                    int temp = burstTime[i];
                    burstTime[i] = burstTime[j];
                    burstTime[j] = temp;
                    
                    // Swap arrival times
                    temp = arrivalTime[i];
                    arrivalTime[i] = arrivalTime[j];
                    arrivalTime[j] = temp;

                    // Swap process IDs
                    temp = process[i];
                    process[i] = process[j];
                    process[j] = temp;
                }
            }
        }

        // Calculate completion times
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (arrivalTime[i] > currentTime) {
                currentTime = arrivalTime[i]; // CPU remains idle until process arrives
            }
            currentTime += burstTime[i];
            completionTime[i] = currentTime;
        }

        // Calculate turnaround time and waiting time
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = completionTime[i] - arrivalTime[i]; // TAT = CT - AT
            waitingTime[i] = turnaroundTime[i] - burstTime[i];      // WT = TAT - BT
        }

        // Display the result
        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + process[i] + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" +
                               completionTime[i] + "\t\t" + waitingTime[i] + "\t\t" + turnaroundTime[i]);
        }
        
        sc.close();
    }
}
