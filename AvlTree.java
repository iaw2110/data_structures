/* Ivan Wolansky
 * iaw2110
   AvlTree */

/* In order to implement this code, I went and altered the Weiss code.
 * This means that I changed the AnyType parameterizations to Strings 
 * since the AvlNode and the element it takes in should be words. 
 * Additionally, I erased methods unnecessary to the assignment such as
 *  findMin, findMax, and remove. */

import java.util.List;
import java.util.LinkedList;

public class AvlTree<AnyType extends Comparable<? super AnyType>> {

	/** The tree root. */
	private AvlNode<String> root;

	/**
	 * Construct the tree.
	 */

	public AvlTree() {
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x
	 *            the item to insert.
	 */

	// inserts a word into the tree, or adds another line to
	// the existing node
	public void indexWord(String word, int line) {
		if (!this.contains(word)) {
			this.insert(word, line);
		} else {
			AvlNode<String> node = this.find(word, root);
			// makes sure that another instance of a word in a line is
			// not inserted into the list
			if (!node.lines.contains(line)) {
				node.lines.add(line);
			}
		}
	}

	// returns the lines for a given word
	public List<Integer> getLinesForWord(String word) {
		if (!this.contains(word)) {
			return null;
		}
		return this.find(word, root).lines;
	}

	// prints each node by using the AvlTree recursive printTree method
	public void printIndex() {
		this.printTree();
	}

	// private recursive method to find a word --> modified from
	// the contains method except it returns the node being searched for
	private AvlNode<String> find(String x, AvlNode<String> t) {
		while (t != null) {
			int compareResult = x.compareTo(t.element);

			if (compareResult < 0)
				t = t.left;
			else if (compareResult > 0)
				t = t.right;
			else
				return t; // Match
		}

		return null; // No match
	}

	public void insert(String x, int line) {
		root = insert(x, root, line);
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return true if x is found.
	 */
	public boolean contains(String x) {
		return contains(x, root);
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}

	private static final int ALLOWED_IMBALANCE = 1;

	// Assume t is either balanced or within one of being balanced
	private AvlNode<String> balance(AvlNode<String> t) {
		if (t == null)
			return t;

		if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE)
			if (height(t.left.left) >= height(t.left.right))
				t = rotateWithLeftChild(t);
			else
				t = doubleWithLeftChild(t);
		else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE)
			if (height(t.right.right) >= height(t.right.left))
				t = rotateWithRightChild(t);
			else
				t = doubleWithRightChild(t);

		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}
	
	// Weiss code checkBalance
	public void checkBalance() {
		checkBalance(root);
	}

	// the Weiss code's private recursive balance checker for AvlTree
	private int checkBalance(AvlNode<String> t) {
		if (t == null)
			return -1;

		if (t != null) {
			int hl = checkBalance(t.left);
			int hr = checkBalance(t.right);
			if (Math.abs(height(t.left) - height(t.right)) > 1
					|| height(t.left) != hl || height(t.right) != hr)
				System.out.println("OOPS!!");
		}

		return height(t);
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	// modified to take in the line of a word
	private AvlNode<String> insert(String x, AvlNode<String> t, int line) {
		if (t == null)
			return new AvlNode<String>(x, line, null, null);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left, line);
		else if (compareResult > 0)
			t.right = insert(x, t.right, line);
		else
			; // Duplicate; do nothing
		return balance(t);
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x
	 *            is item to search for.
	 * @param t
	 *            the node that roots the tree.
	 * @return true if x is found in subtree.
	 */
	private boolean contains(String x, AvlNode<String> t) {
		while (t != null) {
			int compareResult = x.compareTo(t.element);

			if (compareResult < 0)
				t = t.left;
			else if (compareResult > 0)
				t = t.right;
			else
				return true; // Match
		}

		return false; // No match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 */
	// private recursive modified print method
	// to print out line numbers of each word
	// but after it prints each word itself
	private void printTree(AvlNode<String> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			System.out.println(t.lines);
			printTree(t.right);
		}
	}

	/**
	 * Return the height of node t, or -1, if null.
	 */
	private int height(AvlNode<String> t) {
		return t == null ? -1 : t.height;
	}

	/**
	 * Rotate binary tree node with left child. For AVL trees, this is a single
	 * rotation for case 1. Update heights, then return new root.
	 */
	private AvlNode<String> rotateWithLeftChild(AvlNode<String> k2) {
		AvlNode<String> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	/**
	 * Rotate binary tree node with right child. For AVL trees, this is a single
	 * rotation for case 4. Update heights, then return new root.
	 */
	private AvlNode<String> rotateWithRightChild(AvlNode<String> k1) {
		AvlNode<String> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.right), k1.height) + 1;
		return k2;
	}

	/**
	 * Double rotate binary tree node: first left child with its right child;
	 * then node k3 with new left child. For AVL trees, this is a double
	 * rotation for case 2. Update heights, then return new root.
	 */
	private AvlNode<String> doubleWithLeftChild(AvlNode<String> k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	/**
	 * Double rotate binary tree node: first right child with its left child;
	 * then node k1 with new right child. For AVL trees, this is a double
	 * rotation for case 3. Update heights, then return new root.
	 */
	private AvlNode<String> doubleWithRightChild(AvlNode<String> k1) {
		k1.right = rotateWithLeftChild(k1.right);
		return rotateWithRightChild(k1);
	}

	// altered nested private class to take in the line of the word
	private static class AvlNode<AnyType> {
		// Constructor
		AvlNode(AnyType theElement, int line, AvlNode<AnyType> lt,
				AvlNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
			height = 0;
			// adds the line to the LinkedList in the node
			lines.add(line);
		}

		LinkedList<Integer> lines = new LinkedList<>(); // list contains
														// the lines a
														// word appears
		AnyType element; // The data in the node
		AvlNode<AnyType> left; // Left child
		AvlNode<AnyType> right; // Right child
		int height; // Height
	}
}