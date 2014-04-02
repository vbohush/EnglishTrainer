package net.bohush.english;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Lesson {
	private ArrayList<String> listEnglish = new ArrayList<>();
	private ArrayList<String> listUkraine = new ArrayList<>();
	private String name;
	private int index = 0;
	private int numberOfErrors = 0;
	private boolean caseSensitive;
	private boolean strongCheck;
	private int lessonNumber;
	private int tipUsed = 0;
	
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public void setStrongCheck(boolean strongCheck) {
		this.strongCheck = strongCheck;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLessonNumber() {
		return lessonNumber;
	}
	
	public int getNumberOfErrors() {
		return numberOfErrors;
	}
	
	public int getNumberOfLines() {
		return listEnglish.size();
	}
	
	public int getNumberOfGuessedLines() {
		return index;
	}

	public int getNumberOfUsedTips() {
		return tipUsed;
	}
	
	public Lesson(int lessonNumber, boolean caseSensitive, boolean strongCheck) throws FileNotFoundException {
		String fileName = "data/" + lessonNumber + ".txt";
		this.lessonNumber = lessonNumber;
		this.caseSensitive = caseSensitive;
		this.strongCheck = strongCheck;
		URL url = this.getClass().getResource(fileName);
		if(url != null) {
			File file = new File(url.getFile());
			Scanner input = new Scanner(file);
			name = input.nextLine();
			while(input.hasNextLine()) {
				String nextLine = input.nextLine();
				if (!nextLine.equals("")) {
					listEnglish.add(nextLine);
					listUkraine.add(input.nextLine());
				}				
			}
			input.close();
		}
	}
	
	public void reset() {
		index = 0;
		numberOfErrors = 0;
	}
	
	public String getTip() {
		if(index >= listEnglish.size()) {
			return null;
		} else {
			tipUsed++;
			return listEnglish.get(index);
		}
	}
	
	public String getGuessedText() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < index; i++) {
			result.append(listEnglish.get(i));
			result.append('\n');	
		}
		if(!isFinish()) {
			result.append(listUkraine.get(index));
		}
		return result.toString();
	}
	
	public boolean isFinish() {
		if(index >= listEnglish.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String clearString(String string) {
		string = string.replaceAll(" " , "");
		string = string.replaceAll("\t" , "");
		string = string.replaceAll("," , "");
		string = string.replaceAll("\\." , "");
		string = string.replaceAll("!" , "");
		string = string.replaceAll("\\?" , "");
		string = string.replaceAll(":" , "");
		string = string.replaceAll(";" , "");
		return string;
	}
	
	public boolean isValid(String nextLine) {
		String str1 = nextLine;
		String str2 = listEnglish.get(index);
		if(!caseSensitive) {
			str1 = str1.toLowerCase();
			str2 = str2.toLowerCase();
		}
		if(!strongCheck) {
			str1 = clearString(str1);
			str2 = clearString(str2);
		}
		if(str1.equals(str2)) {
			index++;
			return true;
		} else {
			numberOfErrors++;
			return false;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (String nextLine : listEnglish) {
			result.append(nextLine);
			result.append('\n');	
		}
		return result.toString();
	}
}