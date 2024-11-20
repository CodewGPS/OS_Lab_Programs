import java.util.Scanner;

public class RoundRobin {
    
    public static void queueUpdation(int[] queue, int timer, int[] arrival, int n, int maxProcessIndex) {
        int zeroIndex = -1;
        for (int i = 0; i < n; i++) {
            if (queue[i] == 0) {
                zeroIndex = i;
                break;
            }
        }
        queue[zeroIndex] = maxProcessIndex + 1;
    }

    public static void queueMaintenance(int[] queue, int n) {
        for (int i = 0; (i < n - 1) && (queue[i + 1] != 0); i++) {
            int temp = queue[i];
            queue[i] = queue[i + 1];
            queue[i + 1] = temp;
        }
    }

    public static void checkNewArrival(int timer, int[] arrival, int n, int maxProcessIndex, int[] queue) {
        if (timer <= arrival[n - 1]) {
            boolean newArrival = false;
            for (int j = (maxProcessIndex + 1); j < n; j++) {
                if (arrival[j] <= timer) {
                    if (maxProcessIndex < j) {
                        maxProcessIndex = j;
                        newArrival = true;
                    }
                }
            }
            // Add the incoming process to the ready queue if any arrives
            if (newArrival)
                queueUpdation(queue, timer, arrival, n, maxProcessIndex);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n, tq, timer = 0, maxProcessIndex = 0;
        float avgWait = 0, avgTT = 0;

        System.out.print("Enter the time quantum: ");
        tq = sc.nextInt();
        System.out.print("Enter the number of processes: ");
        n = sc.nextInt();
        
        int[] arrival = new int[n];
        int[] burst = new int[n];
        int[] wait = new int[n];
        int[] turn = new int[n];
        int[] queue = new int[n];
        int[] temp_burst = new int[n];
        boolean[] complete = new boolean[n];

        System.out.println("Enter the arrival time of the processes: ");
        for (int i = 0; i < n; i++) {
            arrival[i] = sc.nextInt();
        }

        System.out.println("Enter the burst time of the processes: ");
        for (int i = 0; i < n; i++) {
            burst[i] = sc.nextInt();
            temp_burst[i] = burst[i];
        }

        for (int i = 0; i < n; i++) { // Initializing the queue and complete array
            complete[i] = false;
            queue[i] = 0;
        }

        while (timer < arrival[0]) { // Incrementing timer until the first process arrives
            timer++;
        }
        queue[0] = 1;

        while (true) {
            boolean flag = true;
            for (int i = 0; i < n; i++) {
                if (temp_burst[i] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                break;

            for (int i = 0; (i < n) && (queue[i] != 0); i++) {
                int ctr = 0;
                while ((ctr < tq) && (temp_burst[queue[0] - 1] > 0)) {
                    temp_burst[queue[0] - 1] -= 1;
                    timer += 1;
                    ctr++;

                    // Checking and updating the ready queue until all the processes arrive
                    checkNewArrival(timer, arrival, n, maxProcessIndex, queue);
                }
                // If a process is completed, store its exit time and mark it as completed
                if ((temp_burst[queue[0] - 1] == 0) && (!complete[queue[0] - 1])) {
                    // Turn array currently stores the completion time
                    turn[queue[0] - 1] = timer;
                    complete[queue[0] - 1] = true;
                }

                // Checks whether or not CPU is idle
                boolean idle = true;
                if (queue[n - 1] == 0) {
                    for (int j = 0; j < n && queue[j] != 0; j++) {
                        if (!complete[queue[j] - 1]) {
                            idle = false;
                        }
                    }
                } else {
                    idle = false;
                }

                if (idle) {
                    timer++;
                    checkNewArrival(timer, arrival, n, maxProcessIndex, queue);
                }

                // Maintaining the entries of processes after each preemption in the ready Queue
                queueMaintenance(queue, n);
            }
        }

        for (int i = 0; i < n; i++) {
            turn[i] = turn[i] - arrival[i];
            wait[i] = turn[i] - burst[i];
        }

        System.out.println("\nProcess\tArrival Time\tBurst Time\tWait Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + arrival[i] + "\t\t" + burst[i] + "\t\t" + wait[i] + "\t\t" + turn[i]);
        }

        for (int i = 0; i < n; i++) {
            avgWait += wait[i];
            avgTT += turn[i];
        }

        System.out.println("\nAverage wait time: " + (avgWait / n));
        System.out.println("Average Turnaround Time: " + (avgTT / n));

        sc.close();
    }
}
