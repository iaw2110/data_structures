/* Ivan Wolansky
 * iaw2110
   Problem2 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Problem2 {

	public static void main(String args[]) {
		File file = new File(args[0]);
		Scanner scan;
		AvlTree<String> avl = new AvlTree<>();
		int line = 0;
		// erases all punctuation
		Pattern word = Pattern.compile("\\b\\w+['-:]*+\\w*\\b");

		try {
			scan = new Scanner(file);

			// outer while loop takes in a line
			while (scan.hasNextLine()) {
				line++;
				Scanner sc = new Scanner(scan.nextLine());

				// scans each word with the regex applied to it
				while (sc.hasNext(word)) {
					String s = sc.next(word);
					// makes everything lowercase
					avl.indexWord(s.toLowerCase(), line);
				}
				sc.close();
			}
			// at the end prints everything
			if (scan.hasNextLine() == false) {
				scan.close();
				avl.printIndex();
			}

		} catch (IOException e) {
			System.out.println("Please try again with the correct file name");
			Scanner s = new Scanner(System.in);
			args[0] = s.next();
			s.close();
		}
	}
}
