package com.javaclasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

/*************************************************************************
 * Compilation: javac TST.java Execution: java TST < words.txt Dependencies:
 * StdIn.java
 *
 * Symbol table with string keys, implemented using a ternary search trie (TST).
 *
 *
 * % java TST < shellsST.txt by 4 sea 6 sells 1 she 0 shells 3 shore 7 the 5
 *
 * 
 * % java TST theory the now is the time for all good men
 * 
 * Remarks -------- - can't use a key that is the empty string ""
 *
 *************************************************************************/

public class SearchTrie<Value> {
	private int N; // size
	private Node root; // root of TST

	private class Node {
		private char c; // character
		private Node left, mid, right; // left, middle, and right subtries
		private Value val; // value associated with string
	}

	// return number of key-value pairs
	public int size() {
		return N;
	}

	/**************************************************************
	 * Is string key in the symbol table?
	 **************************************************************/
	public boolean contains(String key) {
		return get(key) != null;
	}

	public Value get(String key) {
		if (key == null)
			throw new NullPointerException();
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		Node x = get(root, key, 0);
		if (x == null)
			return null;
		return x.val;
	}

	// return subtrie corresponding to given key
	private Node get(Node x, String key, int d) {
		if (key == null)
			throw new NullPointerException();
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		if (x == null)
			return null;
		char c = key.charAt(d);
		if (c < x.c)
			return get(x.left, key, d);
		else if (c > x.c)
			return get(x.right, key, d);
		else if (d < key.length() - 1)
			return get(x.mid, key, d + 1);
		else
			return x;
	}

	/**************************************************************
	 * Insert string s into the symbol table.
	 **************************************************************/
	public void put(String s, Value val) {
		if (!contains(s))
			N++;
		root = put(root, s, val, 0);
	}

	private Node put(Node x, String s, Value val, int d) {
		char c = s.charAt(d);
		if (x == null) {
			x = new Node();
			x.c = c;
		}
		if (c < x.c)
			x.left = put(x.left, s, val, d);
		else if (c > x.c)
			x.right = put(x.right, s, val, d);
		else if (d < s.length() - 1)
			x.mid = put(x.mid, s, val, d + 1);
		else
			x.val = val;
		return x;
	}

	/**************************************************************
	 * Find and return longest prefix of s in TST
	 **************************************************************/
	public String longestPrefixOf(String s) {
		if (s == null || s.length() == 0)
			return null;
		int length = 0;
		Node x = root;
		int i = 0;
		while (x != null && i < s.length()) {
			char c = s.charAt(i);
			if (c < x.c)
				x = x.left;
			else if (c > x.c)
				x = x.right;
			else {
				i++;
				if (x.val != null)
					length = i;
				x = x.mid;
			}
		}
		return s.substring(0, length);
	}

	// all keys in symbol table
	public Iterable<String> keys() {
		Queue<String> queue = new Queue<String>();
		collect(root, "", queue);
		return queue;
	}

	// all keys starting with given prefix
	public Iterable<String> prefixMatch(String prefix) {
		Queue<String> queue = new Queue<String>();
		Node x = get(root, prefix, 0);
		if (x == null)
			return queue;
		if (x.val != null)
			queue.enqueue(prefix);
		collect(x.mid, prefix, queue);
		return queue;
	}

	// all keys in subtrie rooted at x with given prefix
	private void collect(Node x, String prefix, Queue<String> queue) {
		if (x == null)
			return;
		collect(x.left, prefix, queue);
		if (x.val != null)
			queue.enqueue(prefix + x.c);
		collect(x.mid, prefix + x.c, queue);
		collect(x.right, prefix, queue);
	}

	// return all keys matching given wildcard pattern
	public Iterable<String> wildcardMatch(String pat) {
		Queue<String> queue = new Queue<String>();
		collect(root, "", 0, pat, queue);
		return queue;
	}

	private void collect(Node x, String prefix, int i, String pat, Queue<String> q) {
		if (x == null)
			return;
		char c = pat.charAt(i);
		if (c == '.' || c < x.c)
			collect(x.left, prefix, i, pat, q);
		if (c == '.' || c == x.c) {
			if (i == pat.length() - 1 && x.val != null)
				q.enqueue(prefix + x.c);
			if (i < pat.length() - 1)
				collect(x.mid, prefix + x.c, i + 1, pat, q);
		}
		if (c == '.' || c > x.c)
			collect(x.right, prefix, i, pat, q);
	}

	// test client
	public List<Entry<String, Integer>> search(String search) throws IOException {
		Map<String, Integer> fileContentCount = new HashMap<String, Integer>();
		String txt = null;
		//File[] files = new File("C:\\Users\\pavan\\Desktop\\MAC Lab\\Adv Computing\\HttpSearch\\src\\text").listFiles();
		File[] files = new File("F:/Windsor Sem-1/Advance Computing Concepts/Assignments-workspace/searchengine/src/webpages").listFiles();

		// System.out.println("File Reading .. ");
		for (File file : files) {
			System.out.println("File : "+file.toString());
			In input = new In(file);
			while (!input.isEmpty()) {
				txt = input.readAll();

			}
			SearchTrie<Queue<Integer>> tst = new SearchTrie<Queue<Integer>>();
			StringTokenizer st1 = new StringTokenizer(txt, " ", false);
			Queue<Integer> index;
			int indexpos = 0;
			ArrayList<Integer> wordpos;
			System.out.println("TRIE creation started...");
			while (st1.hasMoreTokens()) {
				String key = st1.nextToken().toString();
				wordpos = getMatchingKeysKMP(key, txt);
				index = new Queue<Integer>();
				index.enqueue(indexpos);
				for (int i : wordpos) {
					index.enqueue(i);
				}
				tst.put(key, index);
				indexpos++;
			}
			System.out.println("TRIE created successfully..");

			// Search the word in Trie formed
			Queue<Integer> que = tst.get(search);
			System.out.println("Pattern :" + search);
			if (que == null) {
				System.out.println("The pattern was not found");
			} else {
				System.out.print("IndexNo:" + que.dequeue());
				System.out.println("\tOccurences:" + que.size());
				System.out.print("Offsets:");
				fileContentCount.put(file.toString(), que.size());
				for (int i : que) {
					System.out.print(que.dequeue() + "  ");
				}
			}
			System.out.println("\n\n");
		}
		return entriesSortedByValues(fileContentCount);
	}

	static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());
		// merge sort
		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}

	private static ArrayList<Integer> getMatchingKeysKMP(String pat, String txt) {
		KMP kmp = new KMP(pat);
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
			return patternPosition;
		else
			patternPosition.add(searchPos);
		return patternPosition;
	}

}