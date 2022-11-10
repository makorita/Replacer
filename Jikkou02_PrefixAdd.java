import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class Jikkou02_PrefixAdd{
	public static void main(String args[]) throws Exception{
		String mode=args[0];
	
		//クリップボードの読み込み
		String clipBoardStr=null;
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
		{
			Transferable object = clipboard.getContents(null);
			clipBoardStr = (String)object.getTransferData(DataFlavor.stringFlavor);
		}
		
		if(mode.equals("ADD")){
			BufferedReader br = new BufferedReader(new FileReader("prefix.txt"));
			String prefix=br.readLine();
			br.close();
			
			Pattern p=Pattern.compile("\n",Pattern.MULTILINE);
			Matcher m=p.matcher(clipBoardStr);
			clipBoardStr=m.replaceAll("\n"+prefix);
			clipBoardStr=prefix+clipBoardStr;
		}else if(mode.equals("DEL")){
			BufferedReader br = new BufferedReader(new FileReader("prefix.txt"));
			String prefix=br.readLine();
			br.close();
			
			Pattern p=Pattern.compile("\n"+prefix,Pattern.MULTILINE);
			Matcher m=p.matcher(clipBoardStr);
			clipBoardStr=m.replaceAll("\n");
			clipBoardStr=clipBoardStr.replaceFirst("^"+prefix,"");
		}else if(mode.equals("SET")){
			PrintWriter wr=new PrintWriter(new FileWriter("prefix.txt"));
			wr.println(clipBoardStr);
			wr.close();
		}
		
		//クリップボードのセット
		//clipBoardStr,clipboard⇒クリップボード
		{
			StringSelection selection = new StringSelection(clipBoardStr);
			clipboard.setContents(selection, null);
		}
	}
}