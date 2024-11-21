import java.util.*;

class LRUPageReplacement {
    static int pageFaults(int[] pages, int n, int capacity) {
        LinkedHashSet<Integer> s = new LinkedHashSet<Integer>(capacity);
        int page_faults = 0;

        for (int page : pages) {
            if (!s.contains(page)) {
                if (s.size() == capacity) {
                    Iterator<Integer> it = s.iterator();
                    s.remove(it.next());
                }
                s.add(page);
                page_faults++;
            } else {
                s.remove(page);
                s.add(page);
            }
        }
        return page_faults;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of pages: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
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