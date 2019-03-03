package com.javaclasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesUtil {

	public static List<String> readTextFileByLines(String fileName) {
		List<String> linesList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(fileName));
			while ((sCurrentLine = br.readLine()) != null) {
				linesList.add(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return linesList;
	}

	public static String absolutePath(String name) {

		File f = new File(name);
		String absolutePath = f.getAbsolutePath();
		return absolutePath;
	}

	public static String txtFile() {
		final String TEXT_FILE = "F:/Windsor Sem-1/Advance Computing Concepts/Assignments-workspace/searchengine/src/text";
		return TEXT_FILE;
	}

	public final static String fileSeparator() {
		return File.separator;
	}

	public static String makeTxtName(File filename) {
		String name = filename.getName();
		String[] temp = name.split("htm");
		return temp[0] + "txt";
	}

}
