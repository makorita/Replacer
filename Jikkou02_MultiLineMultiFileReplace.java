import java.io.*;
import java.util.*;
import java.util.regex.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.w3c.dom.*;

public class Jikkou02_MultiLineMultiFileReplace{
	/*
	work�t�H���_���t�@�C����bef.txt����aft.txt�ɕϊ�����
	*/
	public static void main(String args[]) throws Exception{
		ArrayList<File> srcFileList=new ArrayList<File>();
		{
			File rootDir=new File("work");
			recursiveCheck(rootDir,srcFileList);
		}
		
		File befFile=new File("bef.txt");
		File aftFile=new File("aft.txt");
		if(args.length==2){
			befFile=new File(args[0]);
			aftFile=new File(args[1]);
		}
		
		String befStr=fileToString(befFile);
		String aftStr=fileToString(aftFile);
		//System.out.println(befStr);
		//System.out.println(aftStr);
		
		for(File curFile:srcFileList){
			
			String srcStr=fileToString(curFile);
			
			PrintWriter wr=new PrintWriter(new FileWriter("bak/"+curFile.getName().replace(".txt","_bak.txt")));
			wr.print(srcStr);
			wr.close();
			
			wr=new PrintWriter(new FileWriter(curFile));
			Pattern pattern = Pattern.compile(befStr,Pattern.MULTILINE);
			Matcher matcher = pattern.matcher(srcStr);
			srcStr=matcher.replaceAll(aftStr);
			//System.out.println(srcStr);
			wr.print(srcStr);
			wr.close();
		}
	}
	
	public static void recursiveCheck(File curDir,ArrayList<File> srcFileList){
		File[] childList=curDir.listFiles();
		for(File curFile:childList){
			if(curFile.isDirectory())recursiveCheck(curFile,srcFileList);
			else if(curFile.isFile())srcFileList.add(curFile);
		}
	}
	
  public static String fileToString(File file) throws IOException {
    BufferedReader br = null;
    try {
      // �t�@�C����ǂݍ��ރo�b�t�@�h���[�_���쐬���܂��B
      br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      // �ǂݍ��񂾕������ێ�����X�g�����O�o�b�t�@��p�ӂ��܂��B
      StringBuffer sb = new StringBuffer();
      // �t�@�C������ǂݍ��񂾈ꕶ����ۑ�����ϐ��ł��B
      int c;
      // �t�@�C������P�������ǂݍ��݁A�o�b�t�@�֒ǉ����܂��B
      while ((c = br.read()) != -1) {
        sb.append((char) c);
      }
      // �o�b�t�@�̓��e�𕶎��񉻂��ĕԂ��܂��B
      return sb.toString();
    } finally {
      // ���[�_����܂��B
      br.close();
    }
  }
}