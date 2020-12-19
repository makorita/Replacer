import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class Jikkou01_MakeList{
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
		
		//クリップボードの書き込み
		{
			PrintWriter wr=new PrintWriter(new FileWriter("tempList.txt"));
			String word[]=clipBoardStr.split("\n");
			for(String tmpStr:word){
				wr.println(tmpStr);
			}
			wr.close();
		}
	}
}