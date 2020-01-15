/* Ivan Wolansky
   iaw2110
   Problem3 */

public class Problem3 {

	public static void fragment1(int n) {
		long starTime = System.nanoTime();
		int sum = 0;
		for (int i = 0; i < 23; i++)
			for (int j = 0; j < n; j++)
				sum = sum + 1;
		long endTime = System.nanoTime();

		System.out.println(endTime - starTime);
	}

	public static void fragment2(int n) {
		long starTime = System.currentTimeMillis();
		int sum = 0;
		for (int i = 0; i < n; i++)
			for (int k = i; k < n; k++)
				sum = sum + 1;
		long endTime = System.currentTimeMillis();

		System.out.println(endTime - starTime);
	}

	public int foo(int n, int k) {
		if (n <= k)
			return 1;
		else
			return foo(n / k, k) + 1;

	}

	// private method calls the recursive method to allow for timing
	private static void foo2() {
		long starTime = System.nanoTime();
		Problem3 f = new Problem3();
		f.foo(10000, 2);
		f.foo(20000, 2);
		f.foo(30000, 2);
		f.foo(40000, 2);
		long endTime = System.nanoTime();

		System.out.println(endTime - starTime);
	}

	public static void main(String args[]) {
		System.out.println(
				"Fragment 1's times are as follows (in nanoseconds): ");
		fragment1(10000);
		fragment1(20000);
		fragment1(30000);
		fragment1(40000);
		System.out.println(
				"Fragment 2's times are as follows (in milliseconds): ");
		fragment2(10000);
		fragment2(20000);
		fragment2(30000);
		fragment2(40000);
		System.out.println(
				"Fragment 3's times are as follows (in nanoseconds): ");
		foo2();
		foo2();
		foo2();
		foo2();
	}
}
