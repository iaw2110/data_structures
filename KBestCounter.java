/* Ivan Wolansky
 * iaw2110
   KBestCounter */

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class KBestCounter<T extends Comparable<? super T>> {

	PriorityQueue<T> heap;
	int k;

	public KBestCounter(int k) {
		heap = new PriorityQueue<>();
		this.k = k;
	}

	// inserts another number to the heap in log k time
	public void count(T x) {
		heap.offer(x);
	}

	// returns a list sorted from largest to smallest in k log k time
	public List<T> kbest() {
		List<T> sorted = new ArrayList<>();
		
		// adds the entire heap to the list from largest to smallest
		while (heap.size() > 0) {
			sorted.add(0, heap.poll());	
		}
		
		// restores the priority queue to its original state after retrieval
		for (int i = 0; i < sorted.size(); i++) {
			heap.offer(sorted.get(i));
		}
		
		// removes everything that exceeds the k-largest elements
		for (int i = sorted.size() - 1; i >= k; i--) {
			sorted.remove(i);
		}
		return sorted;
	}
}
