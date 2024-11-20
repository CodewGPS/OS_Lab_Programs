import java.util.*;  
public class FCFS {   
    // Process class to represent a process  
    static class Process {  
        private int at; // arrival time  
        private int bt; // burst time  
        private int ct; // completion time  
        private int tat; // turnaround time  
        private int wt; // waiting time  
        private int pid; // process ID  
        // Getter method to get a variable value of the process  
        public int getVar(String var) {  
            if (var.equals("at"))  
                return at;  
            if (var.equals("bt"))  
                return bt;  
            if (var.equals("ct"))  
                return ct;  
            if (var.equals("tat"))  
                return tat;  
            if (var.equals("wt"))  
                return wt;  
            return pid;  
        }  
        // Setter method to set a variable value of the process  
        public void setVar(String var, int value) {  
            if (var.equals("at"))  
                at = value;  
            else if (var.equals("bt"))  
                bt = value;  
            else if (var.equals("ct"))  
                ct = value;  
            else if (var.equals("tat"))  
                tat = value;  
            else if (var.equals("wt"))  
                wt = value;  
            else  
                pid = value;  
        }  
        // Update the turnaround time and waiting time after completion  
        public void updateAfterCt() {  
            tat = ct - at;  
            wt = tat - bt;  
        }  
        // Display the process details  
        public void display() {  
            System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n", pid, at, bt, ct, tat, wt);  
        }  
    }  
    // Calculate the average of a variable value for all processes  
    public static float average(ArrayList<Process> P, String var) {  
        int total = 0;  
        for (Process temp : P) {  
            total += temp.getVar(var);  
        }  
        return (float) total / P.size();  
    }  
    public static void main(String[] args) {  
        /* 
        Input description. 
        First line contains an integer n 
        the next n lines contain 2 space separated integers 
        containing values for arrival time and burst time for 
        example: 
        2 
        0 3 
        1 2 
        */  
        Scanner sc = new Scanner(System.in);  
        int n = sc.nextInt();  
        int counter = 0;  
        ArrayList<Process> P = new ArrayList<Process>(n);  
        // Create a process object for each input and add to the process list  
        for (int i = 0; i < n; i++) {  
            Process temp = new Process();  
            temp.setVar("pid", counter++);  
            temp.setVar("at", sc.nextInt());  
            temp.setVar("bt", sc.nextInt());  
            P.add(temp);  
        }  
        // Sort the process list by arrival time  
        Collections.sort(P, new Comparator<Process>() {  
            public int compare(Process first, Process second) {  
                return first.getVar("at") - second.getVar("at");  
            }  
        });  
        System.out.println("pid\tat\tbt\tct\ttat\twt");  
        // Calculate completion time and display the details of the first process  
        P.get(0).setVar("ct", P.get(0).getVar("at") + P.get(0).getVar("bt"));  
        P.get(0).updateAfterCt();  
        P.get(0).display();  
        // Calculate completion time and display the details of the remaining processes  
    for (int i = 1; i < P.size(); i++) {  
        // Loop through the remaining processes  
        if (P.get(i).getVar("at") < P.get(i - 1).getVar("ct")) {  
            // If the process arrives before the previous process completes  
            P.get(i).setVar("ct", P.get(i - 1).getVar("ct") + P.get(i).getVar("bt"));  
            // Calculate completion time as the completion time of previous process plus its burst time  
        } else {  
            // If the process arrives after the previous process completes  
            System.out.printf("curr['at'] : %d, prev['ct'] : %d\n\n", P.get(i).getVar("at"),  
                    P.get(i - 1).getVar("ct"));  
            P.get(i).setVar("ct", P.get(i).getVar("at") + P.get(i).getVar("bt"));  
            // Calculate completion time as the arrival time plus its burst time  
        }  
        P.get(i).updateAfterCt(); // Update the turnaround time and waiting time for the current process  
        P.get(i).display(); // Display the details of the current process  
    }  
    System.out.printf("Average waiting time : %f\n", average(P, "wt"));  
    sc.close(); // Close the scanner  
}  
}  