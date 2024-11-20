import java.util.HashSet;
import java.util.Scanner;

class OptimalPageReplacement {
    static int pageFaults(int pages[], int n, int capacity) {
        HashSet<Integer> s = new HashSet<>(capacity);
        int page_faults = 0;

        for (int i = 0; i < n; i++) {
            if (!s.contains(pages[i])) {
                if (s.size() < capacity) {
                    s.add(pages[i]);
                } else {
                    int farthest = i, pageToRemove = -1;

                    for (int page : s) {
                        int j;
                        for (j = i + 1; j < n; j++) {
                            if (pages[j] == page) {
                                if (j > farthest) {
                                    farthest = j;
                                    pageToRemove = page;
                                }
                                break;
                            }
                        }
                        if (j == n) {
                            pageToRemove = page;
                            break;
                        }
                    }

                    s.remove(pageToRemove);
                    s.add(pages[i]);
                }
                page_faults++;
            }
        }

        return page_faults;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of pages: ");
        int n = sc.nextInt();

        int pages[] = new int[n];
        System.out.println("Enter the page sequence:");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        System.out.print("Enter the cache capacity: ");
        int capacity = sc.nextInt();

        System.out.println("Number of page faults: " + pageFaults(pages, n, capacity));
        sc.close();
    }
}
