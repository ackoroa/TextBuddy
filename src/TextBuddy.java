import java.util.*;
import java.io.*;

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
		if (!f.exists()) f.createNewFile();
		return f;
	}

	private static void processCommand(String commandLine, File currentFile) {
		String commandWord;
		int commandParameter, i;
		StringTokenizer tokenizedCommand = new StringTokenizer(commandLine);

		commandWord = tokenizedCommand.nextToken();
		if (commandWord.equals("display")) {
			try {
				display(currentFile);
			} catch (FileNotFoundException e) {
				System.out.println("file for display not found");
				e.printStackTrace();
			}
		} else if (commandWord.equals("clear"))
			clear(currentFile);
		else if (commandWord.equals("add"))
			;
		else if (commandWord.equals("delete"))
			;
		else if (commandWord.equals("exit"))
			System.exit(0);
		// BufferedWriter fout = new BufferedWriter(new
		// FileWriter(fileName,true));

		return;
	}

	//Displays the contents of the file
	public static void display(File f) throws FileNotFoundException {
		Scanner fin = new Scanner(f);
		int i = 0;
		while (fin.hasNext()) {
			i++;
			System.out.println(i + ". " + fin.nextLine());
		}
		fin.close();

		return;
	}

	// Clears the file.
	public static void clear(File f) {
		f.delete();
		
		try {
			f.createNewFile();
		} catch (IOException e) {
			System.out.println("Error creating file during clear");
			e.printStackTrace();
		}
		
		System.out.println("All contents deleted from " + f.getName());

		return;
	}

}
