import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class Jikkou01_ClipboardReplacer{
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
		
		//�L�����X�g�̓ǂݍ���
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
		
		//�������珑��Replacer�̓�����J�n����
		if(activeSet.contains("CSVReplacer")){
			CSVReplacer csvr=new CSVReplacer();
			clipBoardStr=csvr.replace(clipBoardStr);
		}
		if(activeSet.contains("XMLReplacer")){
			XMLReplacer xmlr=new XMLReplacer();
			clipBoardStr=xmlr.replace(clipBoardStr);
		}
		
		
		//�N���b�v�{�[�h�̃Z�b�g
		//clipBoardStr,clipboard�˃N���b�v�{�[�h
		{
			StringSelection selection = new StringSelection(clipBoardStr);
			clipboard.setContents(selection, null);
		}
	}
}