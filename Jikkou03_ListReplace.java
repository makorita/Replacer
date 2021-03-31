import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class Jikkou03_ListReplace{
	/*
	<list>の内容をリストで置換していく
	*/
	public static void main(String args[]) throws Exception{
		//クリップボードの読み込み
		String clipBoardStr=null;
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
		{
			Transferable object = clipboard.getContents(null);
			clipBoardStr = (String)object.getTransferData(DataFlavor.stringFlavor);
		}
		
		LinkedList<String> replaceList=new LinkedList<String>();
		{
			BufferedReader br = new BufferedReader(new FileReader("ReplaceList.txt"));
			//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "EUC-JP"));
			String line;
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				replaceList.add(line);
			}
			br.close();
		}
		
		{
			Pattern p1=Pattern.compile("<list>",Pattern.MULTILINE);
			int index=0;
			while(true){
				Matcher m1=p1.matcher(clipBoardStr);
				if(!m1.find())break;
				
				String aftStr=replaceList.get(index);
				clipBoardStr=m1.replaceFirst(aftStr);
				if(index==replaceList.size()-1)index=0;
				else index++;
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