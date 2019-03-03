package com.javaclasses;

import java.io.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import java.net.*;

public class HTMLtoText extends HTMLEditorKit.ParserCallback {
	StringBuffer s;

	static File[] directoryListing;
	static File dir;
	static String txtname;

	public HTMLtoText() {
	}

	public void parse(Reader in) throws IOException {
		s = new StringBuffer();
		ParserDelegator delegator = new ParserDelegator();
		// the third parameter is TRUE to ignore charset directive
		delegator.parse(in, this, Boolean.TRUE);
	}

	public void handleText(char[] text, int pos) {
		s.append(text);
	}

	public String getText() {
		return s.toString();
	}

	public static void main(String[] args) {

		final String FILE_NAME = "F:/Windsor Sem-1/Advance Computing Concepts/Assignments-workspace/searchengine/src/webpages";
		// get the files in the directory
		directoryListing = new File(FILE_NAME).listFiles();

		// checks if the directory is empty
		if (directoryListing != null) {
			System.out.println("Converting files from HTML to TXT...");
			// looping through the files in the directory
			for (File specificFile : directoryListing) {
				// writing the txt version to the txtfiles folder
				writeTotxt(specificFile);
			}
			System.out.println("Conversion completed \n Refresh the \"txtfiles\" folder to see the new files!!!");
		} else {
			System.out.println("Directory is empty");
		}
	}
	
	private static void writeTotxt(File filename) {
		// creating the absolute path to the txt file
		txtname = FilesUtil.absolutePath(FilesUtil.txtFile()) + FilesUtil.fileSeparator()
				+ FilesUtil.makeTxtName(filename);

		try {

			// Using the HTML parser to write the files to the txt folder
			FileReader in = new FileReader(filename);
			HTMLtoText parser = new HTMLtoText();
			parser.parse(in);
			in.close();
			String textHTML = parser.getText();
			// Write the text to a file
			BufferedWriter writerTxt = new BufferedWriter(new FileWriter(txtname));
			writerTxt.write(textHTML);
			writerTxt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}