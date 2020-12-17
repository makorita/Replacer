import java.io.*;
import java.util.*;

import java.util.regex.*;

public class CSVReplacer{
	String[] koumokuList;
	LinkedList<String[]> dataTable;
	
	public CSVReplacer() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("tempDB.csv"));
		String koumokuStr=br.readLine();
		koumokuList=koumokuStr.split(",");
		
		String line;
		dataTable=new LinkedList<String[]>();
		while ((line = br.readLine()) != null) {
			//System.out.println(line);
			String[] curDataList=line.split(",");
			dataTable.add(curDataList);
		}
		br.close();
	}
	
	public String replace(String originStr){
		//System.out.println("orgin:"+originStr);
		
		String returnStr=originStr;
		Pattern pattern = Pattern.compile("<rdb:.+?>",Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(originStr);
		while (matcher.find()) {
			//System.out.println("INN");
			String koumokuStr=null;
			int koumokuIndex=-1;
			String idKey=null;
			int idIndex=-1;
			String idValue=null;
			
			String matched = matcher.group();
			String editStr=matched;
			editStr=editStr.replace("<rdb:","");
			editStr=editStr.replace(">","");
			String[] word1=editStr.split("@");
			koumokuStr=word1[0];
			String[] word2=word1[1].split("=");
			idKey=word2[0];
			idValue=word2[1];
			
			//項目とIDのインデックス取得
			for(int i=0;i<koumokuList.length;i++){
				if(koumokuStr.equals(koumokuList[i])){
					koumokuIndex=i;
				}
				if(idKey.equals(koumokuList[i])){
					idIndex=i;
				}
			}
			System.out.println(koumokuStr+","+koumokuIndex+","+idKey+","+idIndex+","+idValue);
			
			for(String[] curList:dataTable){
				if(curList.length-1<koumokuIndex)continue;
				if(curList[koumokuIndex].length()==0)continue;
				if(curList.length-1<idIndex)continue;
				if(curList[idIndex].length()==0)continue;
				//System.out.println(curList[idIndex]+","+idValue);
				if(!curList[idIndex].equals(idValue))continue;
				
				//System.out.println(matched+","+curList[koumokuIndex]);
				returnStr=returnStr.replaceAll(matched,curList[koumokuIndex]);
				break;
			}
		}

		return returnStr;
	}
}
