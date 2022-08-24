package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class EnglishToFrenchTranslator {

	public static void main(String[] args) {

		HashMap<String, String> dataDictionary = new HashMap<String, String>();
		String shakesPeareText = null;
		String finalWord = null;
		dataDictionary = convertCsvtoMap();
		shakesPeareText = readShakesPeareFile();
		finalWord = replaceWords(shakesPeareText, dataDictionary);
		writeFile(finalWord);
	}

	public static HashMap<String, String> convertCsvtoMap() {
		HashMap<String, String> englishFrenchMapper = new HashMap<String, String>();
		File file = new File("E:\\dictionary\\french_dictionary.csv");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String st;
		try {
			while ((st = br.readLine()) != null) {
				String[] word = st.split(",");
				englishFrenchMapper.put(word[0], word[1]);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return englishFrenchMapper;
	}

	public static String readShakesPeareFile() {

		String shakesPeareText = "";
		File shakespeareTextFile = new File("E:\\dictionary\\t8.shakespeare.txt");

		int bufferSize = 999999999;
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader(shakespeareTextFile), bufferSize);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String str;

		try {
			while ((str = buffer.readLine()) != null) {
				shakesPeareText = shakesPeareText.concat(str);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return shakesPeareText;

	}

	public static String replaceWords(String text, HashMap<String, String> dataDictionary) {

		File findWords = new File("E:\\dictionary\\find_words.txt");

		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader(findWords));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String word;

		try {
			while ((word = buffer.readLine()) != null) {
				int replaceCount = countMatches(text, word);
				System.out.println(word + " - " + replaceCount + " Times");
				text = text.replaceAll(word, dataDictionary.get(word));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}

	public static void writeFile(String finalWord) {
		FileWriter fileWrite = null;
		try {
			fileWrite = new FileWriter("E:\\dictionary\\translator.txt");
			fileWrite.write(finalWord);
			System.out.println("File written into this location - E:\\dictionary\\translator.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWrite.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int countMatches(String str, String sub) {
		if (str == null || sub == null)
			return 0;
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}
		return count;
	}
}
