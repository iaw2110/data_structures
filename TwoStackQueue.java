/* Ivan Wolansky
 * iaw2110
   TwoStackQueue */

public class TwoStackQueue<AnyType> implements MyQueue<AnyType> {

	MyStack<AnyType> S1 = new MyStack<AnyType>();
	MyStack<AnyType> S2 = new MyStack<AnyType>();

	@Override
	// adds an element to the queue
	public void enqueue(AnyType e) {
		S1.push(e);
	}

	@Override
	// takes an element off the queue by flipping the
	// queue onto S2 and then putting the rest
	// of the elements back onto S1
	public AnyType dequeue() {
		if (isEmpty()) {
			return null;
		} 
		else {
			AnyType y;

			// flips the stacks from S1 to S2 to pop off the
			// first inputed element
			while (!S1.isEmpty()) {
				S2.push(S1.pop());
			}
			y = S2.pop();

			while (!S2.isEmpty()) {
				S1.push(S2.pop());
			}
			return y;
		}
	}

	@Override
	public boolean isEmpty() {
		return S1.isEmpty();
	}

	@Override
	public int size() {
		return S1.getSize();
	}
}
