import java.util.HashSet;
import java.util.Scanner;

class OptimalPageReplacement {
    static int pageFaults(int pages[], int n, int capacity) {
        // If no pages or no capacity, return 0
        if (pages == null || n == 0 || capacity == 0) return 0;

        // Set to represent current page frames
        HashSet<Integer> s = new HashSet<>(capacity);
        
        // Track page faults
        int page_faults = 0;

        // Iterate through all pages
        for (int i = 0; i < n; i++) {
            // If page not in current set, it's a page fault
            if (!s.contains(pages[i])) {
                // If set is not full, simply add the page
                if (s.size() < capacity) {
                    s.add(pages[i]);
                } 
                // If set is full, need to find page to replace
                else {
                    // Find page to replace
                    int pageToRemove = findOptimalPageToReplace(pages, s, i);
                    
                    // Remove the page identified for replacement
                    s.remove(pageToRemove);
                    
                    // Add current page
                    s.add(pages[i]);
                }
                
                // Increment page faults
                page_faults++;
            }
        }

        return page_faults;
    }

    // Method to find optimal page to replace
    static int findOptimalPageToReplace(int pages[], HashSet<Integer> s, int currentIndex) {
        int farthestPageIndex = -1;
        int pageToRemove = -1;

        // Check each page in current set
        for (int page : s) {
            // Find next occurrence of this page
            int nextIndex = findNextOccurrence(pages, page, currentIndex);

            // If page not found in remaining sequence
            if (nextIndex == -1) {
                return page;  // Immediately return this page
            }

            // Update farthest page if this page occurs later
            if (nextIndex > farthestPageIndex) {
                farthestPageIndex = nextIndex;
                pageToRemove = page;
            }
        }

        return pageToRemove;
    }

    // Find next occurrence of a page from current index
    static int findNextOccurrence(int pages[], int page, int currentIndex) {
        for (int i = currentIndex + 1; i < pages.length; i++) {
            if (pages[i] == page) {
                return i;
            }
        }
        return -1;  // Page not found in remaining sequence
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