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
		
		//有効リストの読み込み
		HashSet<String> activeSet=new HashSet<String>();
		{
			BufferedReader br = new BufferedReader(new FileReader("ReplacerSetting.txt"));
			//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "EUC-JP"));
			String line;
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				activeSet.add(line);
			}
			br.close();
		}
		
		//ここから書くReplacerの動作を開始する
		if(activeSet.contains("CSVReplacer")){
			CSVReplacer csvr=new CSVReplacer();
			clipBoardStr=csvr.replace(clipBoardStr);
		}
		if(activeSet.contains("XMLReplacer")){
			XMLReplacer xmlr=new XMLReplacer();
			clipBoardStr=xmlr.replace(clipBoardStr);
		}
		
		
		//クリップボードのセット
		//clipBoardStr,clipboard⇒クリップボード
		{
			StringSelection selection = new StringSelection(clipBoardStr);
			clipboard.setContents(selection, null);
		}
	}
}