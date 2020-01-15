
/* Ivan Wolansky
   iaw2110
   Problem1 */

import java.util.Arrays;

public class Problem1 {

	// method provided in the assignment
	public static <AnyType extends Comparable<AnyType>> AnyType findMax(
			AnyType[] arr) {

		int maxIndex = 0;
		for (int i = 1; i < arr.length; i++)
			if (arr[i].compareTo(arr[maxIndex]) > 0)
				maxIndex = i;
		return arr[maxIndex];
	}

	public static void main(String[] args) {

		// creates an array of Rectangle objects
		Rectangle[] arr = new Rectangle[5];
		arr[0] = new Rectangle(4, 5);
		arr[1] = new Rectangle(7, 14);
		arr[2] = new Rectangle(1, 5);
		arr[3] = new Rectangle(12, 8);
		arr[4] = new Rectangle(10, 15);

		// prints each rectangle's (length, width)
		System.out.println(Arrays.toString(arr) + " The largest perimeter is "
				+ findMax(arr).getPerimeter() + ".");
	}
}
