import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest {
    @Test
    public void testTextBuddy() {
	String fileName = "testFile.txt";
	File file;
	file = new File(fileName);

	try {
	    if (!file.exists())
		file.createNewFile();
	} catch (IOException e) {
	    System.out.println("Error opening/creating file");
	    e.printStackTrace();
	    System.exit(1);
	}

	testOneCommand("clears file before test",
		("All contents deleted from " + fileName), 
		"clear", file);
	testOneCommand("display before any add", 
		(fileName + " is empty"),
		"display", file);
	testOneCommand("try wrong command",
		"dummywrongcommand is not a valid command",
		"dummywrongcommand", file);
	testOneCommand("simple add",
		("Added to " + fileName + ": \"little brown fox\""),
		"add little brown fox", file);
	testOneCommand("display after add", 
		("1. little brown fox\n"),
		"display", file);
	testOneCommand("more adds",
		("Added to " + fileName + ": \"jumped over the moon\""),
		"add jumped over the moon", file);
	testOneCommand("more adds",
		("Added to " + fileName + ": \"and fell\""),
		"add and fell", file);
	testOneCommand("deletion with empty parameter",
		("\"\" is not a valid parameter"),
		"delete", file);
	testOneCommand("deletion with wrong parameter",
		("\"wrgpar\" is not a valid parameter"),
		"delete wrgpar", file);
	testOneCommand("deletion with out of bound parameter",
		("\"12\" is not a valid parameter"),
		"delete 12", file);
	testOneCommand("deletion with wrong parameter",
		("\"12 wrgpar\" is not a valid parameter"),
		"delete 12 wrgpar", file);
	testOneCommand("deletion with wrong parameter",
		("\"drgp 12\" is not a valid parameter"),
		"delete drgp 12", file);
	testOneCommand("display after wrong deletes", 
		("1. little brown fox\n2. jumped over the moon\n3. and fell\n"),
		"display", file);
	testOneCommand("delete 2",
		("Deleted from " + fileName + ": \"jumped over the moon\""),
		"delete 2", file);
	testOneCommand("display after delete 2", 
		("1. little brown fox\n2. and fell\n"),
		"display", file);
	testOneCommand("clears file after test",
		("All contents deleted from " + fileName), 
		"clear", file);
	testOneCommand("display after clear", 
		(fileName + " is empty"),
		"display", file);
    }

    private void testOneCommand(String description, String expected,
	    String command, File file) {
	assertEquals(description, expected,
		TextBuddy.processCommand(command, file));
    }
}
