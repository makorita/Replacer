import java.io.*;
import java.util.*;

import java.util.regex.*;

public class MultiReplacer{
	LinkedList<String> replaceList;
	public MultiReplacer() throws Exception{
		replaceList=new LinkedList<String>();
		BufferedReader br = new BufferedReader(new FileReader("ReplaceMap.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "EUC-JP"));
		String line;
		while ((line = br.readLine()) != null) {
			//System.out.println(line);
			replaceList.add(line);
		}
		br.close();
	}
	
	public String replace(String originStr) throws Exception{
		String returnStr=originStr;
		for(String curStr:replaceList){
			if(!curStr.matches("^.+\t.+(\t.+)*\t*"))continue;
			String[] word=curStr.split("\t");
			if(word.length==3 && word[2].equals("regular")){
				Pattern p=Pattern.compile(word[0],Pattern.MULTILINE);
				Matcher m=p.matcher(returnStr);
				returnStr=m.replaceAll(word[1]);
			}else{
				Pattern p=Pattern.compile(word[0]);
				Matcher m=p.matcher(returnStr);
				while (m.find()) {
					String matched = m.group();
					returnStr=returnStr.replace(matched,word[1]);
				}
			}
		}
		
		return returnStr;
	}
}
