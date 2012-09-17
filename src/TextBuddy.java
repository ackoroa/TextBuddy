/* TextBuddy by Arnold Christopher Koroa
 * A0092101Y
 * 
 * This is a simple text editor as specified in NUS CS2103T CE1 problem statement
 * 
 * It is assumed that the input will be from the keyboard or a redirected text file. 
 * If input comes from a redirected text file, the displayed messages might not look 
 * exactly like the given example as the commands entered will not be shown.
 * 
 * It is also assumed that the argument passed into the program will be a valid plain
 * text format file. 
 * 
 * The text file used is assumed to be small as some of the implementations might
 * not be very efficient for large files.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TextBuddy {
    public static void main(String args[]) throws IOException {
	String fileName = args[0], command;

	Scanner sc = new Scanner(System.in);

	File currentFile = openFile(fileName);
	displayWelcomeMessage(fileName);

	while (true) {
	    System.out.print("command: ");
	    command = sc.nextLine();
	    processCommand(command, currentFile);
	}
    }

    private static void displayWelcomeMessage(String fileName) {
	System.out.println("Welcome to TextBuddy. " + fileName
		+ " is ready to use.");
    }

    private static File openFile(String fileName) throws IOException {
	File f;
	f = new File(fileName);
	if (!f.exists())
	    f.createNewFile();
	return f;
    }

    private static void processCommand(String commandLine, File currentFile) {
	String commandWord = getFirstWord(commandLine);

	if (commandWord.equals("display"))
	    display(currentFile);
	else if (commandWord.equals("clear"))
	    clear(currentFile);
	else if (commandWord.equals("add"))
	    add(commandLine, currentFile);
	else if (commandWord.equals("delete"))
	    delete(commandLine, currentFile);
	else if (commandWord.equals("exit"))
	    System.exit(0);
	else
	    System.out.println(commandWord + " is not a valid command");
    }

    // Displays the contents of the file or a message if file is empty
    private static void display(File file) {
	Scanner inputFile;
	int i = 0;

	if (isEmpty(file)) {
	    System.out.println(file.getName() + " is empty");
	} else {
	    try {
		inputFile = new Scanner(file);

		while (inputFile.hasNext()) {
		    i++;
		    System.out.println(i + ". " + inputFile.nextLine());
		}

		inputFile.close();
	    } catch (FileNotFoundException e) {
		System.out.println("File for display not found.");
		e.printStackTrace();
	    }
	}
    }

    // Performs the clear command's operation
    private static void clear(File file) {
	clearFile(file);
	System.out.println("All contents deleted from " + file.getName());
    }

    // The actual clearing of file
    private static void clearFile(File file) {
	BufferedWriter outputFile;

	try {
	    outputFile = new BufferedWriter(new FileWriter(file.getName(), false));
	    outputFile.write("");
	    outputFile.close();
	} catch (IOException e) {
	    System.out.println("IOException during clear");
	    e.printStackTrace();
	}
    }

    // Performs the add command's operation
    private static void add(String commandLine, File file) {
	String inputText = removeFirstWord(commandLine);

	writeToFile(inputText, file);

	System.out.println("Added to " + file.getName() + ": \"" + inputText
		+ "\"");
    }

    // The actual Writing of text to file
    private static void writeToFile(String inputText, File file) {
	BufferedWriter outputFile;

	try {
	    outputFile = new BufferedWriter(new FileWriter(file.getName(), true));

	    if (!isEmpty(file))
		outputFile.newLine();
	    outputFile.write(inputText);

	    outputFile.close();
	} catch (IOException e) {
	    System.out.println("Error writing to file");
	    e.printStackTrace();
	}
    }

    // Performs the delete command's operation
    private static void delete(String commandLine, File file) {
	int deleteParameter;
	String deletedText;

	String deleteString = removeFirstWord(commandLine);
	boolean valid = validateDeleteParameter(deleteString, file);

	if (valid) {
	    deleteParameter = Integer.parseInt(deleteString);
	    deletedText = removeLine(deleteParameter, file);
	    System.out.println("Deleted from " + file.getName() + ": \""
		    + deletedText + "\"");
	} else {
	    System.out.println("\"" + deleteString
		    + "\" is not a valid parameter");
	}
    }

    // The actual deletion of indicated line from file
    // Returns the deleted line
    private static String removeLine(int deleteParameter, File file) {
	String tempString, deletedString = "";
	Queue<String> lines = new LinkedList<String>();

	deletedString = copyUndeletedLinesToQueue(deleteParameter, file, lines);
	clearFile(file);

	while (!lines.isEmpty()) {
	    tempString = lines.poll();
	    writeToFile(tempString, file);
	}

	return deletedString;
    }

    // Copies file content to lines Queue, except for the deleted line
    // Returns the deleted line
    private static String copyUndeletedLinesToQueue(int deleteParameter,
	    File f, Queue<String> lines) {
	int lineCount = 0;
	String tempString, deletedString = "";
	Scanner fin;

	try {
	    fin = new Scanner(f);

	    while (fin.hasNext()) {
		lineCount++;
		tempString = fin.nextLine();

		if (lineCount != deleteParameter)
		    lines.offer(tempString);
		else
		    deletedString = tempString;
	    }
	    fin.close();
	} catch (FileNotFoundException e) {
	    System.out.println("File not found during delete");
	    e.printStackTrace();
	}

	return deletedString;
    }

    private static boolean validateDeleteParameter(String deleteString, File file) {
	boolean lineIsInFile;

	if (areDigits(deleteString) && (numberOfWords(deleteString) == 1)) {
	    lineIsInFile = Integer.valueOf(deleteString) <= numberOfLines(file);
	    return lineIsInFile;
	}
	return false;
    }

    private static int numberOfLines(File file) {
	int count = 0;
	Scanner inputFile;

	try {
	    inputFile = new Scanner(file);

	    while (inputFile.hasNext()) {
		count++;
		inputFile.nextLine();
	    }
	    inputFile.close();
	} catch (FileNotFoundException e) {
	    System.out.println("File not found while counting lines");
	    e.printStackTrace();
	}

	return count;
    }

    private static int numberOfWords(String s) {
	StringTokenizer st = new StringTokenizer(s);
	return st.countTokens();
    }

    private static boolean areDigits(String deleteString) {
	boolean areDigits;

	areDigits = true;
	for (char c : deleteString.toCharArray()) {
	    if (!Character.isDigit(c)) {
		areDigits = false;
		break;
	    }
	}
	return areDigits;
    }

    private static boolean isEmpty(File file) {
	return file.length() <= 0;
    }

    // Remove commandWord from commandLine. Adapted from
    // CityConnectForRefactoring,java
    private static String removeFirstWord(String commandLine) {
	return commandLine.replace(getFirstWord(commandLine), "").trim();
    }

    // Extracts commandWord from the commandLine
    private static String getFirstWord(String commandLine) {
	StringTokenizer tokenizedCommand = new StringTokenizer(commandLine);
	String commandWord = tokenizedCommand.nextToken();

	return commandWord;
    }
}
