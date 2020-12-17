import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class Jikkou01_ClipboardReplacer{
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
		
		CSVReplacer csvr=new CSVReplacer();
		clipBoardStr=csvr.replace(clipBoardStr);
		
		//クリップボードのセット
		//clipBoardStr,clipboard⇒クリップボード
		{
			StringSelection selection = new StringSelection(clipBoardStr);
			clipboard.setContents(selection, null);
		}
	}
}