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
				//System.out.println("regular");
				Pattern p=Pattern.compile(word[0],Pattern.MULTILINE);
				Matcher m=p.matcher(returnStr);
				returnStr=m.replaceAll(word[1]);
			}else{
				//System.out.println("normal");
				String editStr=null;
				String[] word2=returnStr.split("\n");
				for(String curStr2:word2){
					curStr2=curStr2.replace(word[0],word[1]);
					if(editStr==null)editStr=curStr2+"\n";
					else editStr+=curStr2+"\n";
				}
				
				returnStr=editStr;
			}
		}
		
		return returnStr;
	}
}
