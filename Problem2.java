
/* Ivan Wolansky
   iaw2110
   Problem2 */

import java.util.Arrays;

public class Problem2 {

	public static <AnyType extends Comparable<AnyType>> int binarySearch(
			AnyType[] a, AnyType x) {

		return binarySearch(a, x, 0, a.length - 1);
	}

	// private recursive helper method that keeps track of the start
	// and end of the array
	private static <AnyType extends Comparable<AnyType>> int binarySearch(
			AnyType[] a, AnyType x, int start, int end) {

		// base case
		if (start > end) {
			return -1;
		}
		int mid = (start + end) / 2;

		// looks at the left half of the array
		if (a[mid].compareTo(x) > 0) {
			return binarySearch(a, x, start, mid - 1);
		}
		// looks at the right half of the array
		else if (a[mid].compareTo(x) < 0) {
			return binarySearch(a, x, mid + 1, end);
		}
		// returns the middle element since it is the rectangle we were
		// looking for
		else {
			return mid;
		}
	}

	public static void main(String[] args) {

		// creates an array of Rectangle objects
		Rectangle[] arr = new Rectangle[5];
		arr[0] = new Rectangle(4, 5);
		arr[1] = new Rectangle(7, 14);
		arr[2] = new Rectangle(1, 5);
		arr[3] = new Rectangle(12, 8);
		arr[4] = new Rectangle(10, 15);

		Arrays.sort(arr);

		System.out.println(
				"The array is sorted like this: " + Arrays.toString(arr));
		System.out.println("Found rectangle " + arr[2] + " " + "at index "
				+ binarySearch(arr, arr[2]));
	}
}
