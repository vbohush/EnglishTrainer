package net.bohush.english;

import java.util.ArrayList;
import java.util.Scanner;

public class Lesson {
	private ArrayList<String> list = new ArrayList<String>();
	private String name;
	private int index = 0;
	private boolean caseSensitive;
	private boolean strongCheck;
	private int lessonNumber;
	private int correctLines = 0;
	private boolean isCorrectLine = true;
	
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
	
	public int getNumberOfCorrectLines() {
		return correctLines;
	}
	
	public int getNumberOfLines() {
		return list.size();
	}
	
	public int getNumberOfGuessedLines() {
		return index;
	}
	
	public Lesson(int lessonNumber) {
		this(lessonNumber, false, false);
	}
	
	public Lesson(int lessonNumber, boolean caseSensitive, boolean strongCheck) {
		this.lessonNumber = lessonNumber + 1;
		this.caseSensitive = caseSensitive;
		this.strongCheck = strongCheck;
		String text = Data.getLessonText(lessonNumber);
		Scanner input = new Scanner(text);
		name = input.nextLine();
		while(input.hasNextLine()) {
			String nextLine = input.nextLine();
			if (!nextLine.equals("")) {
				list.add(nextLine);
			}				
		}
		input.close();
	}
	
	public void reset() {
		index = 0;
		correctLines = 0;
	}
	
	public String getTip() {
		if(index >= list.size()) {
			return "";
		} else {
			isCorrectLine = false;
			String strTip = list.get(index);
			strTip = strTip.substring(strTip.indexOf(":") + 2, strTip.length());
			return strTip;
		}
	}
	
	public String getGuessedText() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < index; i++) {
			result.append(list.get(i));
			result.append('\n');	
		}
		return result.toString();
	}
	
	public boolean isFinish() {
		if(index >= list.size()) {
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
		String str2 = list.get(index);
		str2 = str2.substring(str2.indexOf(":") + 2, str2.length());
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
			if(isCorrectLine) {
				correctLines++;
			} else {
				isCorrectLine = true;
			}
			return true;
		} else {
			isCorrectLine = false;
			return false;
		}
	}
	
	public void skip() {
		index++;
		isCorrectLine = true;
	}
	
	@Override
	public String toString() {
		return getLessonNumber() + ". " + getName();
	}
}