import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

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
	    ;
	else if (commandWord.equals("exit"))
	    System.exit(0);
    }

    // Displays the contents of the file
    private static void display(File f) {
	Scanner fin;
	int i = 0;

	if (isEmpty(f)) {
	    System.out.println(f.getName() + " is empty");
	} else {
	    try {
		fin = new Scanner(f);

		while (fin.hasNext()) {
		    i++;
		    System.out.println(i + ". " + fin.nextLine());
		}

		fin.close();
	    } catch (FileNotFoundException e) {
		System.out.println("File for display not found.");
		e.printStackTrace();
	    }
	}
    }

    // Clears the file.
    private static void clear(File f) {
	try {
	    f.delete();
	    f.createNewFile();
	} catch (IOException e) {
	    System.out.println("IOException: Error creating file during clear");
	    e.printStackTrace();
	}

	System.out.println("All contents deleted from " + f.getName());
    }

    // Appends user input to file
    private static void add(String commandLine, File f) {
	String inputText = removeFirstWord(commandLine);

	BufferedWriter fout;
	try {
	    fout = new BufferedWriter(new FileWriter(f.getName(), true));

	    if (!isEmpty(f))
		fout.newLine();
	    fout.write(inputText);

	    fout.close();
	} catch (IOException e) {
	    System.out.println("Error writing to file.");
	    e.printStackTrace();
	}

	System.out.println("Added to " + f.getName() + ": \"" + inputText
		+ "\"");
    }

    private static boolean isEmpty(File f) {
	return f.length() <= 0;
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
