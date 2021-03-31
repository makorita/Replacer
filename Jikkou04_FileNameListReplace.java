import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.file.*;

public class Jikkou04_FileNameListReplace{
	/*
	ファイル名の<list>をリストで置換していく
	*/
	public static void main(String args[]) throws Exception{
		if(args.length<1)System.exit(0);
		if(!args[0].matches(".*[list].*"))System.exit(0);
		String befFilename=args[0];
		
		LinkedList<String> replaceList=new LinkedList<String>();
		BufferedReader br = new BufferedReader(new FileReader("ReplaceList.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "EUC-JP"));
		String line;
		while ((line = br.readLine()) != null) {
			//System.out.println(line);
			replaceList.add(line);
		}
		br.close();
		
		for(String curStr:replaceList){
			String aftFilename=befFilename.replaceAll("\\[list\\]",curStr);
			
			Path sourcePath = Paths.get(befFilename);
			Path targetPath = Paths.get(aftFilename);
			Files.copy(sourcePath, targetPath);
		}
	}
}