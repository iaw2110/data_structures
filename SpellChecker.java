/* Ivan Wolansky
 * iaw2110
   SpellChecker */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashSet;

public class SpellChecker {

	private static HashSet<String> dictionary = new HashSet<>();

	public static void main(String args[]) {
		File file1 = new File(args[0]);
		File file2 = new File(args[1]);
		Scanner scanDict;
		Scanner scanText;
		ArrayList<String> words = new ArrayList<>();

		try {
			scanDict = new Scanner(file1);
			scanText = new Scanner(file2);

			// while loop reads the dictionary and puts text into the HashSet
			while (scanDict.hasNextLine()) {
				String s = scanDict.next();
				// makes everything lowercase and adds to the HashSet
				dictionary.add(s.toLowerCase());
			}

			while (scanText.hasNextLine()) {
				Scanner sc = new Scanner(scanText.nextLine());

				// scans each word with the punctuation removed
				while (sc.hasNext()) {
					String word = sc.next();
					
					// removes non-alphanumeric characters and leaves
					// apostraphes
					String text = word.replaceAll("[^A-Za-z0-9\\']", "");
					
					// makes everything lowercase and adds to a list
					words.add(text.toLowerCase());
				}
				sc.close();
			}

			// loops through each word to be checked and checks them
			for (int i = 0; i < words.size(); i++) {
				String x = words.get(i);
				if (!dictionary.contains(x)) {
					addCharacter(x);
					if (!dictionary.contains(x)) {
						removeCharacter(x);
						if (!dictionary.contains(x)) {
							swapCharacters(x);
						}
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Please try again with the correct file "
					+ "names");
			Scanner s = new Scanner(System.in);
			Scanner x = new Scanner(System.in);
			args[0] = s.next();
			args[1] = x.next();
			s.close();
			x.close();
		}
	}

	// private static method adds a character to the word
	private static void addCharacter(String s) {
		ArrayList<Character> word = new ArrayList<>();

		// creates an arraylist of the word being checked
		for (char c : s.toCharArray()) {
			word.add(c);
		}

		// constructs the array of all the possible characters to be added
		char[] tempAlphabet = "abcdefghijklmnopqrstuvwxyz'".toCharArray();
		ArrayList<Character> alphabet = new ArrayList<Character>();

		for (char i : tempAlphabet) {
			alphabet.add(i);
		}

		// inserts a character at every possible location
		for (int i = 0; i <= word.size(); i++) {
			for (int j = 0; j < alphabet.size(); j++) {
				word.add(i, alphabet.get(j));

				// reconstructs the word after the letter was removed
				StringBuilder sb = new StringBuilder(word.size());
				for (Character x : word) {
					sb.append(x);
				}

				String temp = sb.toString();

				// prints a suggestion if the dictionary has the word
				if (dictionary.contains(temp)) {
					System.out.println("You typed " + s + "."
							+ " After adding a character I suggest " + temp
							+ ".");
				}

				// allows for checking all possible suggestions by resetting
				word.clear();
				for (char c : s.toCharArray()) {
					word.add(c);
				}
			}
		}
	}

	// private static method removes a character from all possible locations
	// in the word
	private static void removeCharacter(String s) {
		ArrayList<Character> word = new ArrayList<>();

		// allows for the removal of any duplicates if there are words with
		// three consecutive identical letters ie goood would otherwise be
		// printed three times based off of how I implemented this method
		HashSet<String> set = new HashSet<String>();

		for (char c : s.toCharArray()) {
			word.add(c);
		}

		// loop controls the removal of letters
		for (int i = 0; i < s.length(); i++) {

			// removes a letter
			word.remove(i);

			// reconstructs the word after the letter was removed

			StringBuilder sb = new StringBuilder(word.size());
			for (Character x : word) {
				sb.append(x);
			}

			String temp = sb.toString();

			set.add(temp);

			// allows for checking all possible suggestions by resetting
			word.clear();
			for (char c : s.toCharArray()) {
				word.add(c);
			}

		}

		ArrayList<String> possibleWords = new ArrayList<>(set);
		// prints a suggestion if the dictionary has the word
		for (int i = 0; i < possibleWords.size(); i++) {
			if (dictionary.contains(possibleWords.get(i))) {
				System.out.println("You typed " + s + "."
						+ " After removing a character I suggest "
						+ possibleWords.get(i) + ".");
			}
		}
	}

	// private static method swaps adjacent characters in the word
	private static void swapCharacters(String s) {
		ArrayList<Character> word = new ArrayList<>();
		for (char c : s.toCharArray()) {
			word.add(c);
		}

		// swaps adjacent letters
		for (int i = 0; i < word.size() - 1; i++) {

			Collections.swap(word, i, i + 1);

			// reconstructs the word after the letter was removed
			StringBuilder sb = new StringBuilder(word.size());
			for (Character x : word) {
				sb.append(x);
			}

			String temp = sb.toString();

			// prints a suggestion if the dictionary has the word
			if (dictionary.contains(temp)) {
				System.out.println("You typed " + s + "."
						+ " After swapping adjacent characters I suggest "
						+ temp + ".");
			}

			// allows for checking all possible suggestions by resetting
			word.clear();
			for (char c : s.toCharArray()) {
				word.add(c);
			}
		}
	}
}