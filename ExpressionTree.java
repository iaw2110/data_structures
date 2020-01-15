/* Ivan Wolansky
 * iaw2110
   ExpressionTree */

import java.util.Scanner;

public class ExpressionTree {

	private ExpressionNode rootNode;
	private MyStack<ExpressionNode> stack;

	// constructor calls the private method
	public ExpressionTree(String postfix) {
		generateExpressionTree(postfix);
	}

	// private method to create the ExpressionTree
	private void generateExpressionTree(String postfix) {
		stack = new MyStack<ExpressionNode>();
		Scanner scan = new Scanner(postfix);
		String current = "";
		while (scan.hasNext()) {
			current = scan.next();
			if (operator(current)) {
				try {
					ExpressionNode rightNode = stack.pop();
					ExpressionNode leftNode = stack.pop();
					stack.push(
							new ExpressionNode(current, leftNode, rightNode));
				} catch (RuntimeException e) {
					System.out.println(e.getMessage());
				}
			} else {
				stack.push(new ExpressionNode(current, null, null));
			}
		}
		if (stack.isEmpty()) {
			scan.close();
			throw new RuntimeException("Stack is empty!");
		} else if (stack.getSize() == 1) {
			rootNode = stack.pop();
		} else {
			scan.close();
			throw new RuntimeException("There's something left!!");
		}
		scan.close();
	}

	// public method to evaluate the expression tree
	public int eval() {
		return eval(rootNode);
	}

	// private method that applies the operator its operands
	private int apply(String operator, int left, int right) {
		switch (operator) {
		case "+":
			return left + right;
		case "-":
			return left - right;
		case "*":
			return left * right;
		case "/":
			return left / right;
		default:

			// will return a large number if invalid
			return Integer.MAX_VALUE;
		}
	}

	// recursively evaluates the ExpressionTree
	private int eval(ExpressionNode r) {
		if (r.left == null) {
			return Integer.parseInt(r.element);
		}
		String ro = r.element;
		int lt = eval(r.left);
		int rt = eval(r.right);
		return apply(ro, lt, rt);
	}

	// public method prints the postfix notation of the ExpressionTree
	public String postfix() {
		return postfix(rootNode);
	}

	// private recursive driver to create the postfix notation
	private String postfix(ExpressionNode r) {
		if (r.left == null) {
			return r.element;
		}
		String root = r.element + " ";
		String left = postfix(r.left) + " ";
		String right = postfix(r.right) + " ";

		return left + right + root;
	}

	// public method prints the prefix notation of the ExpressionTree
	public String prefix() {

		return prefix(rootNode);
	}

	// private recursive driver to create the prefix notation
	private String prefix(ExpressionNode r) {
		if (r.left == null) {
			return r.element;
		}
		String left = prefix(r.left) + " ";
		String right = prefix(r.right) + " ";
		String root = r.element + " ";

		return root + left + right;
	}

	// public method prints the infix notation of the ExpressionTree
	public String infix() {
		return infix(rootNode);
	}

	// private recursive driver to create the infix notation
	private String infix(ExpressionNode r) {
		if (r.left == null) {
			return r.element;
		}
		String left = "( " + infix(r.left);
		String middle = " " + r.element + " ";
		String right = infix(r.right) + " )";
		return left + middle + right;
	}

	// private method tells what an operator is
	private static boolean operator(String o) {
		switch (o) {
		case "*":
		case "/":
		case "-":
		case "+":
			return true;
		default:
			return false;
		}
	}

	// private static nested ExpressionNode class
	// modeled after the BinaryTree Node
	private static class ExpressionNode {
		ExpressionNode(String theElement, ExpressionNode lt,
				ExpressionNode rt) {

			element = theElement;
			left = lt;
			right = rt;
		}

		String element; // the data in the node
		ExpressionNode left; // Left child
		ExpressionNode right; // Right child
	}
}
