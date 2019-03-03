package com.javaclasses;

import java.util.ArrayList;

/***************************************************************
 *
 * Compilation: javac KMP.java Execution: java KMP pattern text
 *
 * Reads in two strings, the pattern and the input text, and searches for the
 * pattern in the input text using the KMP algorithm.
 *
 * % java KMP abracadabra abacadabrabracabracadabrabrabracad text:
 * abacadabrabracabracadabrabrabracad pattern: abracadabra
 *
 * % java KMP rab abacadabrabracabracadabrabrabracad text:
 * abacadabrabracabracadabrabrabracad pattern: rab
 *
 * % java KMP bcara abacadabrabracabracadabrabrabracad text:
 * abacadabrabracabracadabrabrabracad pattern: bcara
 *
 * % java KMP rabrabracad abacadabrabracabracadabrabrabracad text:
 * abacadabrabracabracadabrabrabracad pattern: rabrabracad
 *
 * % java KMP abacad abacadabrabracabracadabrabrabracad text:
 * abacadabrabracabracadabrabrabracad pattern: abacad
 *
 ***************************************************************/

public class KMP {
	private final int R; // the radix
	private int[][] dfa; // the KMP automoton

	private char[] pattern; // either the character array for the pattern
	private String pat; // or the pattern string

	// create the DFA from a String
	public KMP(String pat) {
		//System.out.println(pat);
		this.R = 256;
		this.pat = pat;

		// build DFA from pattern
		int M = pat.length();
		dfa = new int[R][M];
		dfa[pat.charAt(0)][0] = 1;
		for (int X = 0, j = 1; j < M; j++) {
			for (int c = 0; c < R; c++)
				dfa[c][j] = dfa[c][X]; // Copy mismatch cases.
			dfa[pat.charAt(j)][j] = j + 1; // Set match case.
			X = dfa[pat.charAt(j)][X]; // Update restart state.
		}
	}

	// create the DFA from a character array over R-character alphabet
	public KMP(char[] pattern, int R) {
		this.R = R;
		this.pattern = new char[pattern.length];
		for (int j = 0; j < pattern.length; j++)
			this.pattern[j] = pattern[j];

		// build DFA from pattern
		int M = pattern.length;
		dfa = new int[R][M];
		dfa[pattern[0]][0] = 1;
		for (int X = 0, j = 1; j < M; j++) {
			for (int c = 0; c < R; c++)
				dfa[c][j] = dfa[c][X]; // Copy mismatch cases.
			dfa[pattern[j]][j] = j + 1; // Set match case.
			X = dfa[pattern[j]][X]; // Update restart state.
		}
	}

	// return offset of first match; N if no match
	public int search(String txt) {

		// simulate operation of DFA on text
		int M = pat.length();
		int N = txt.length();
		int i, j;
		for (i = 0, j = 0; i < N && j < M; i++) {
			j = dfa[txt.charAt(i)][j];
		}
		if (j == M)
			return i - M; // found
		return N; // not found
	}

	// return offset of first match; N if no match
	public int search(char[] text) {

		// simulate operation of DFA on text
		int M = pattern.length;
		int N = text.length;
		int i, j;
		for (i = 0, j = 0; i < N && j < M; i++) {
			j = dfa[text[i]][j];
		}
		if (j == M)
			return i - M; // found
		return N; // not found
	}

	// test client
	public static void main(String[] args) {
		String[] patterns = { "hard", "disk", "hard disk", "hard drive", "hard dist", "xltpru" };
		long startTime = 0;
		long endTime = 0;
		long totalTime = 0;
		String txt = null;
		In input = new In("C:\\Users\\pavan\\Desktop\\MAC Lab\\Adv Computing\\Assignment4\\Hard disk");
		while (!input.isEmpty()) {
			txt = input.readAll();
		}

		startTime = System.currentTimeMillis();
		for (int count = 0; count < 1000; count++) {
			for (String pat : patterns) {
				KMP kmp = new KMP(pat);
				StdOut.println("\n\n" + pat + " at pos :");
				int offsetPos = 0, searchPos = 0;
				ArrayList<Integer> patternPosition = new ArrayList<Integer>();
				int patternLength = pat.length();
				String subStr;
				while ((offsetPos <= (txt.length() - patternLength + 1))) {
					subStr = txt.substring(offsetPos);
					searchPos = kmp.search(txt);
					if (searchPos == subStr.length())
						break;
					patternPosition.add(offsetPos + searchPos);
					offsetPos = offsetPos + searchPos + patternLength;
				}
				if (patternPosition.size() != 0)
					for (int i = 0; i < patternPosition.size() - 1; i++) {
						System.out.print(patternPosition.get(i) + ",");
					}
				else
					patternPosition.add(searchPos);
			}
			System.out.println("_____________________________________________________________________");
		}
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("\n\nAverage time to search the given patterns at all occurrences in a file 100 times using KMP is : "
				+ (double) (totalTime / 100) +" milliseconds");
	}
}
