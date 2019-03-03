package com.javaclasses;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchFiles {
	static HashMap<String, List<String>> fileContentByLines;

	public SearchFiles(String folderPath) {
		if (fileContentByLines == null) {
			fileContentByLines = new HashMap<String, List<String>>();
			File[] files = new File(folderPath).listFiles();
			try {
				loadFilesContent(files);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Entry<String, Integer>> search(String searchContent) throws IOException {
		searchContent = searchContent.toLowerCase();
		Map<String, Integer> fileContentCount = new HashMap<String, Integer>();
		if (fileContentByLines != null) {
			List<String> contentList = null;
			int count = 0;
			String tokens[] = null;
			for (String fileName : fileContentByLines.keySet()) {
				contentList = fileContentByLines.get(fileName); // gets all lines in a particular file in List<String>
																// form
				count = 0;
				if (contentList != null) {
					tokens = null;
					for (String line : contentList) {
						//System.out.println(line.toLowerCase().contains(searchContent));
						if (line.toLowerCase().contains(searchContent)) {
							tokens = line.split(" ");
							for (String word : tokens) {
								if (word.toLowerCase().contains(searchContent))
									count++;
							}
						}
					}
					if (count > 0)
						fileContentCount.put(fileName, count);
				}
			}
		}

		System.out.println("fileContentCount: " + fileContentCount);

		List<Entry<String, Integer>> sortedFileContentCount = entriesSortedByValues(fileContentCount);

		return sortedFileContentCount;
	}

	public ArrayList<String> searchphonenumber(String searchContent) throws IOException {

		String pattern = "(\\()?(\\d){3}(\\))?[- ](\\d){3}-(\\d){4}";
		ArrayList<String> fileContentCount = new ArrayList<String>();
		Pattern r = Pattern.compile(pattern);
		HTMLtoText parser = new HTMLtoText();
		String textHTML = "";
		String t = "";
		File dir = new File(FilesUtil.absolutePath(FilesUtil.txtFile()));
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			int counter = 1;
			for (File child : directoryListing) {
				FileReader in = new FileReader(child);
				parser.parse(in);
				in.close();
				textHTML = parser.getText();
				// Now create matcher object.
				Matcher m = r.matcher(textHTML);
				while (m.find()) {
					if (m.group(0).contains(searchContent)) {
						System.out.println("File name  : "+child.getName());
						fileContentCount.add(child.getName());
					}
				}

			}

		}

		return fileContentCount;

	}

	public static void loadFilesContent(File[] files) throws IOException {
		for (File file : files) {
			if (file.isDirectory()) {
				// System.out.println("Directory: " + file.getName());
				loadFilesContent(file.listFiles()); // Calls same method again.
			} else {
				// System.out.println("File: " + file.getName());
				fileContentByLines.put(file.getName(), FilesUtil.readTextFileByLines(file.getPath()));
			}
		}
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
}