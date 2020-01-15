/* Ivan Wolansky
 * iaw2110
   TwoStackQueueTester */


public class Program2 {
	
	/** Returns the following
	 * null
	 * 0
     * New York
     * Chicago
     * 1
     * Los Angeles
     * London
     * 2
     * Istanbul
     * 3
     * Beijing
     * Madrid
     * Kyiv
	 */
	public static void main (String[] args) {
		TwoStackQueue<Object> test = new TwoStackQueue<Object>();
	
		System.out.println(test.dequeue());
		System.out.println(test.size());
		test.enqueue("New York");
		test.enqueue("Chicago");
		System.out.println(test.dequeue());
		test.enqueue("Los Angeles");
		System.out.println(test.dequeue());	
		System.out.println(test.size());
		test.enqueue("London");
		System.out.println(test.dequeue());
		test.enqueue("Istanbul");
		System.out.println(test.dequeue());
		test.enqueue("Beijing");
		System.out.println(test.size());
		test.enqueue("Madrid");
		test.enqueue("Kyiv");
		System.out.println(test.dequeue());
		System.out.println(test.size());
		
		while(!test.isEmpty()) {
			System.out.println(test.dequeue());
		}
		
	}
}
