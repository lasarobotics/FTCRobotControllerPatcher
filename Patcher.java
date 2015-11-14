import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Collections;

public class Patcher {
	private static ArrayList<String> onlyInOurs = new ArrayList<>();
	private static ArrayList<String[]> differ = new ArrayList<>();
	private static final String officialName = "ftc_app";
	private static final String ourName = "FTCVision";
	
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("out.sh");
		PrintWriter pw = new PrintWriter(f);
		File in = new File("diff.txt");
		Scanner scan = new Scanner(in);
		while(scan.hasNextLine()) {
			parseLine(scan.nextLine());
		}
		Collections.reverse(onlyInOurs);
		generateOutput(pw);
		scan.close();
		pw.close();
		System.out.println("Successfully generated out.sh");
	}
	
	private static String allBesidesBasename(String s) {
		String[] split = s.split("/");
		return s.substring(split[0].length() + 1);
	}
	
	private static String allBesidesLast(String s) {
		String[] split = s.split("/");
		return s.substring(0, s.length() - split[split.length - 1].length() - 1);
	}
	
	private static void generateOutput(PrintWriter pw) {
		pw.print("#!/bin/bash -e\n");
		pw.print("################################################\n");
		pw.print("#                  PATCH FILE                  #\n");
		pw.print("################################################\n");
		pw.print("# Beginning of patch file generated " + (new java.util.Date()) + "\n");
		pw.print("# File generated by user " + System.getProperty("user.name") + "\n");
		pw.print("\n");
		pw.print("# Initial copy of folder\n");
		pw.print("echo Duplicating ftc_app...\n");
		pw.print("cp -r \"" + officialName + "\" patched_folder\n");
		pw.print("mv patched_folder/FtcRobotController patched_folder/ftc-robotcontroller\n");
		pw.print("\n# Move new files from FTCVision -> patched_foler\n");
		pw.print("echo Moving files from FTCVision to patched_folder...\n");
		for(String s : onlyInOurs) {
			pw.print("cp -r \"" + s + "\" \"patched_folder/" + allBesidesBasename(s) + "\"\n");
			pw.print("echo '    'Moved file " + s + "\n");
		}
		pw.print("\n# Update existing files by removing old versions and replacing with new\n");
		pw.print("echo Replacing modified files...\n");
		for(String sarr[] : differ) {
			String s = sarr[1];
			pw.print("rm \"patched_folder/" + allBesidesBasename(s) + "\"\n");
			pw.print("cp \"" + s + "\" \"patched_folder/" + allBesidesBasename(s) + "\"\n");
			pw.print("echo '    'Replaced " + s + "\n");
		}
		pw.print("echo Almost done'!' Extracting ftc-robotcontroller from patched_folder...\n");
		pw.print("mv patched_folder/ftc-robotcontroller patched_ftc-robotcontroller\n");
		pw.print("echo Removing patched_folder...\n");
		pw.print("rm -rf patched_folder\n");
		pw.print("echo Patching completed successfully'!' Result folder is patched_ftc-robotcontroller\n"); 
	}
	
	private static void parseLine(String line) {
		String[] parts = line.split(" ");
		if(parts[0].equals("Only")) {
			String dirPath = parts[2].substring(0, parts[2].length()-1);
			String[] sections = dirPath.split("/");
			if(sections[0].equals(ourName)) {
				onlyInOurs.add(dirPath + "/" + parts[3]);
			}
		} else if(parts[0].equals("Files")) {
			String[] differs = new String[2];
			differs[0] = parts[1];
			differs[1] = parts[3];
			differ.add(differs);
		} else {
			System.out.println("Fatal error: unable to parse");
			System.exit(1);
		}
	}
}
