import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class Jikkou01_MakeCSV{
	/*
	�u���}�b�v�����ɃN���b�v�{�[�h��u������
	*/
	public static void main(String args[]) throws Exception{
		//�N���b�v�{�[�h�̓ǂݍ���
		String clipBoardStr=null;
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
		{
			Transferable object = clipboard.getContents(null);
			clipBoardStr = (String)object.getTransferData(DataFlavor.stringFlavor);
		}
		
		//�N���b�v�{�[�h�̏�������
		{
			PrintWriter wr=new PrintWriter(new FileWriter("tempDB.csv"));
			String word[]=clipBoardStr.split("\n");
			for(String tmpStr:word){
				//System.out.println(tmpStr);
				clipBoardStr=clipBoardStr.replaceAll("\t",",");
				wr.println(clipBoardStr);
			}
			wr.close();
		}
	}
}