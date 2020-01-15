
/* Ivan Wolansky
 * iaw2110
   Symbol Balance */

import java.util.LinkedList;

public class MyStack<AnyType> {

	private LinkedList<AnyType> stack;

	public MyStack() {
		stack = new LinkedList<AnyType>();
	}

	public int getSize() {
		return stack.size();
	}

	// adds an element onto the stack
	public void push(AnyType e) {
		stack.add(e);
	}

	// takes the top element off the stack
	public AnyType pop() {
		if (isEmpty()) {
			throw new RuntimeException("The stack is empty!!!");
		} else {
			AnyType e = stack.get(getSize() - 1);
			stack.remove(getSize() - 1);
			return e;
		}
	}

	// looks at the top element of the stack
	public AnyType peek() {
		return stack.get(getSize() - 1);
	}

	public boolean isEmpty() {
		return getSize() == 0;
	}

	@Override
	// toString method that overrides the Object toString
	public String toString() {
		String sum = "[";
		for (Object e : stack) {
			sum += e.toString() + " ";
		}
		sum += "]";
		return sum;
	}
}
