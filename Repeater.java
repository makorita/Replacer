import java.io.*;
import java.util.*;

import java.util.regex.*;

public class Repeater{
	public Repeater() throws Exception{
	}
	
	public String replace(String originStr) throws Exception{
		String returnStr=originStr;
		Pattern p1=Pattern.compile("<repeat:#[A-Z]\\d+-\\d+>",Pattern.MULTILINE);
		
		while (true) {
			//終了条件
			Matcher m1=p1.matcher(returnStr);
			if(!m1.find())break;
			
			//パラメータセット
			String startTag=m1.group();
			String endTag=startTag.replace("<","</");
			String keyStr=startTag.substring(8,10);
			String tmpStr=startTag.replaceAll("<repeat:#[A-Z]","");
			tmpStr=tmpStr.replaceAll(">","");
			String[] word=tmpStr.split("-");
			int startNum=Integer.parseInt(word[0]);
			int endNum=Integer.parseInt(word[1]);
			//System.out.println(startTag+","+endTag+","+keyStr+","+startNum+","+endNum);
			
			//ブロック取得
			Pattern p2=Pattern.compile(startTag+".*?(\n.*?)*?"+endTag,Pattern.MULTILINE);
			Matcher m2=p2.matcher(returnStr);
			if(!m2.find()){
				//System.out.println("debug");
				returnStr=returnStr.replace(startTag,"");
				continue;
			}
			String blockStr=m2.group();
			String blockContents=blockStr.replaceFirst(startTag,"");
			blockContents=blockContents.replaceFirst(endTag,"");
			//System.out.println(blockContents);
			//System.out.println("-------------------------");
			String tmpBlockStr=null;
			for(int i=startNum;i<=endNum;i++){
				String aftStr=blockContents.replace(keyStr,String.format("%03d",i));
				if(tmpBlockStr==null)tmpBlockStr=aftStr;
				else tmpBlockStr+=aftStr;
			}
			//System.out.println(tmpBlockStr);
			//System.out.println("-------------------------");
			returnStr=returnStr.replaceFirst(blockStr,tmpBlockStr);
			//System.out.println(returnStr);
			//System.out.println("-------------------------");
			
			//if(true)break;
		}
		
		
		return returnStr;
	}
}
