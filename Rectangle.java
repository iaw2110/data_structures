/* Ivan Wolansky
   iaw2110
   Rectangle */

public class Rectangle implements Comparable<Rectangle> {

	// kept private since the main method doesn't require these variables
	private int length;
	private int width;

	public Rectangle(int length, int width) {
		this.length = length;
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getPerimeter() {
		return length * 2 + width * 2;
	}

	@Override
	// overrides compareTo to compare by perimeter
	public int compareTo(Rectangle r) {
		if (this.length * 2 + this.width * 2 < r.length * 2 + r.width * 2) {
			return -1;
		}

		else if (this.length * 2 + this.width * 2 > r.length * 2
				+ r.width * 2) {
			return 1;
		}

		else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return ("(" + Integer.toString(this.getLength()) + ","
				+ Integer.toString(this.getWidth())) + ")";
	}
}
