/* Ivan Wolansky
 * iaw2110
   Symbol Balance */

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SymbolBalance {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Provide a file!!");
			System.exit(1);
		}
		int charCount = 0;
		try {
			MyStack<Character> Symbols = new MyStack<Character>();
			File file = new File(args[0]);
			FileReader reader = new FileReader(file);
			Character c;
			int r;

			// whether we are looking at a literal string/comment
			boolean openString = false, openComment = false;

			// reads until the end of the file
			while ((r = reader.read()) != -1) {
				c = (char) r;
				charCount++;
				// pushes things not in strings or comments onto stack
				if (isOpenBracket(c) && !openString && !openComment) {
					Symbols.push(c);
					// pops them if they match and aren't in string or comments
				} else if (isCloseBracket(c) && !openString && !openComment) {
					if (!(openBracketOf(c) == Symbols.pop()))
						System.out.println(
								"Expected " + closeBracketOf(Symbols.peek())
										+ " but got " + c);
					// checks for matching quotes
				} else if (quote(c)) {
					if (!openString) {
						openString = true;
						Symbols.push(c);
					} else if (openString) {
						if (Symbols.pop() == c) {
							openString = false;
						} else {
							System.out.println(
									"Expected " + closeBracketOf(Symbols.peek())
											+ " but got " + c);
						}
					}
					// checks to make sure /* */ are balanced
				} else if (comment(c, reader)) {
					if (!openComment && isOpenComment(c, reader)) {
						openComment = true;
						Symbols.push(c);
					} else if (openComment && isCloseComment(c, reader)) {
						if (Symbols.pop() == '/') {
							openComment = false;
						} else {
							System.out.println(
									"Expected " + closeBracketOf(Symbols.peek())
											+ " but got " + c);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Occurred at char " + charCount);
			System.exit(1);
		}
	}

	public static boolean isOpenBracket(Character c) {
		switch (c) {
		case '{':
		case '(':
		case '[':
			return true;
		default:
			return false;
		}
	}

	public static boolean isCloseBracket(Character c) {
		switch (c) {
		case '}':
		case ')':
		case ']':
			return true;
		default:
			return false;
		}
	}

	public static Character openBracketOf(Character c) {
		switch (c) {
		case '}':
			return '{';
		case ')':
			return '(';
		case ']':
			return '[';
		case '"':
			return '"';
		case '\\':
			return '/';
		default:
			return 0;
		}
	}

	public static Character closeBracketOf(Character c) {
		switch (c) {
		case '{':
			return '}';
		case '(':
			return ')';
		case '[':
			return ']';
		case '"':
			return '"';
		case '/':
			return '\\';
		default:
			return 0;
		}
	}

	public static boolean quote(Character c) {
		if (c.equals('"'))
			return true;
		return false;
	}

	public static boolean comment(Character c, FileReader r)
			throws IOException {
		if (c.equals('/') || c.equals('*')) {
			Character char1 = (char) r.read();
			Character char2 = (char) r.read();
			boolean first = char1.equals('*') || char1.equals('/');
			boolean second = char2.equals('*') || char2.equals('/');
			if (first || second) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isOpenComment(Character c, FileReader r)
			throws IOException {
		if (c.equals('/') && ((char) r.read()) == '*') {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isCloseComment(Character c, FileReader r)
			throws IOException {
		if (c.equals('*') && ((char) r.read()) == '/') {
			return true;
		} else {
			return false;
		}
	}
}
