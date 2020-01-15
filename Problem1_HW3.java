/* Ivan Wolansky
 * iaw2110
   Problem1 */

// class that shows the ExpressionTree's methods
public class Problem1 {

	public static void main(String[] args) {
		ExpressionTree et = new ExpressionTree("15 3 - 42 6 / *");
		System.out.println("Evaluated Expression: " + et.eval());
		System.out.println("Postfix Expression: " + et.postfix());
		System.out.println("Prefix Expression: " + et.prefix());
		System.out.println("Infix Expression: " + et.infix());
	}
}
