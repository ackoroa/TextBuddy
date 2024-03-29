/* Tester for TextBuddy.sort(file)
 * 
 * Tests the functionality of sort
 */

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class SortTest {
    
    @Test
    public void testSort() {
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
	testOneCommand("simple add",
		("Added to " + fileName + ": \"little brown fox\""),
		"add little brown fox", file);
	testOneCommand("more adds",
		("Added to " + fileName + ": \"jumped over the moon\""),
		"add jumped over the moon", file);
	testOneCommand("more adds",
		("Added to " + fileName + ": \"and fell\""),
		"add and fell", file);
	testOneCommand("display before sort", 
		("1. little brown fox\n2. jumped over the moon\n3. and fell\n"),
		"display", file);
	testOneCommand("sort file",
		("Sorted alphabetically: " + fileName),
		"sort", file);
	testOneCommand("display after sort", 
		("1. and fell\n2. jumped over the moon\n3. little brown fox\n"),
		"display", file);
	testOneCommand("more adds",
		("Added to " + fileName + ": \"BOOM!\""),
		"add BOOM!", file);
	testOneCommand("display before sort", 
		("1. and fell\n2. jumped over the moon\n3. little brown fox\n4. BOOM!\n"),
		"display", file);
	testOneCommand("sort file",
		("Sorted alphabetically: " + fileName),
		"sort", file);
	testOneCommand("display after sort", 
		("1. and fell\n2. BOOM!\n3. jumped over the moon\n4. little brown fox\n"),
		"display", file);
	testOneCommand("clears file after test",
		("All contents deleted from " + fileName), 
		"clear", file);
    }

    private void testOneCommand(String description, String expected,
	    String command, File file) {
	assertEquals(description, expected,
		TextBuddy.processCommand(command, file));
    }
}
