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
	�w��t�H���_���t�@�C���̒��g��bef.txt�̓��e����aft.txt�̓��e�ɕϊ�����
	*/
	public static void main(String args[]) throws Exception{
		if(args.length!=3)System.exit(0);
		String srcFileDir=args[0];
		String befFilePath=args[1];
		String aftFilePath=args[2];
		
		ArrayList<File> srcFileList=new ArrayList<File>();
		{
			File rootDir=new File(srcFileDir);
			recursiveCheck(rootDir,srcFileList);
		}
		
		File befFile=new File(befFilePath);
		File aftFile=new File(aftFilePath);
		String befStr=fileToString(befFile);
		String aftStr=fileToString(aftFile);
		//System.out.println(befStr);
		//System.out.println(aftStr);
		
		for(File curFile:srcFileList){
			
			String srcStr=fileToString(curFile);
			
			PrintWriter wr=new PrintWriter(new FileWriter(curFile.getName()));
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