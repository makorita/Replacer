import java.io.*;
import java.util.*;

import java.util.regex.*;

public class ListReplacer{
	public ListReplacer() throws Exception{
	}
	
	public String replace(String originStr) throws Exception{
		String returnStr=originStr;
		
		LinkedList<String> replaceList=new LinkedList<String>();
		BufferedReader br = new BufferedReader(new FileReader("tempList.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "EUC-JP"));
		String line;
		while ((line = br.readLine()) != null) {
			//System.out.println(line);
			replaceList.add(line);
		}
		br.close();
		
		Pattern p1=Pattern.compile("<list>",Pattern.MULTILINE);
		int index=0;
		while(true){
			Matcher m1=p1.matcher(returnStr);
			if(!m1.find())break;
			//System.out.println(index);
			//System.out.println(returnStr);
			
			String aftStr=replaceList.get(index);
			returnStr=m1.replaceFirst(aftStr);
			if(index==replaceList.size()-1)index=0;
			else index++;
		}
		
		return returnStr;
	}
}
