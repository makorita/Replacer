import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class Jikkou01_MapReplace{
	/*
	置換マップを元にクリップボードを置換する
	*/
	public static void main(String args[]) throws Exception{
		//クリップボードの読み込み
		String clipBoardStr=null;
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
		{
			Transferable object = clipboard.getContents(null);
			clipBoardStr = (String)object.getTransferData(DataFlavor.stringFlavor);
		}
		
		//エントリの降順ソート
		ArrayList<String> entryList=new ArrayList<String>();
		{
			BufferedReader br = new BufferedReader(new FileReader("ReplaceMap.txt"));
			//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "EUC-JP"));
			String line;
			while ((line = br.readLine()) != null) {
				if(!line.matches("^.+\t.+(\t.+)*\t*"))continue;
				if(line.matches("^//.+"))continue;
				if(line.matches("^;.+"))continue;
				
				entryList.add(line);
			}
			br.close();
			Collections.sort(entryList, Collections.reverseOrder()); 
		}
		
		{
			for(String line:entryList) {
				//System.out.println(line);
				
				String[] word=line.split("\t");
				if(word[1].equals("<del>") || word[1].equals("<delete>"))word[1]="";
				
				if(word.length>=3 && word[2].equals("regular")){
					//System.out.println("regular");
					Pattern p=Pattern.compile(word[0],Pattern.MULTILINE);
					Matcher m=p.matcher(clipBoardStr);
					clipBoardStr=m.replaceAll(word[1]);
				}else{
					//System.out.println("normal");
					boolean returnFlag=false;
					if(clipBoardStr.matches("[\\s\\S]*\n"))returnFlag=true;
					//System.out.println(returnFlag);
					
					String editStr=null;
					String[] word2=clipBoardStr.split("\n");
					for(String curStr2:word2){
						curStr2=curStr2.replace(word[0],word[1]);
						if(editStr==null)editStr=curStr2;
						else editStr+="\n"+curStr2;
					}
					if(returnFlag)editStr+="\n";
					clipBoardStr=editStr;
				}
			}
		}
		
		//クリップボードのセット
		//clipBoardStr,clipboard⇒クリップボード
		{
			StringSelection selection = new StringSelection(clipBoardStr);
			clipboard.setContents(selection, null);
		}
	}
}