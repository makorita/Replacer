import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class Jikkou06_XMLReplace{
	/*
	XML�p�X��u������
	*/
	
	static HashMap<String,String> replaceMap;
	static String inputXML="ReplaceXML.xml";
	static String sepStr="##";
	
	public static void main(String args[]) throws Exception{
		//�N���b�v�{�[�h�̓ǂݍ���
		String clipBoardStr=null;
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
		{
			Transferable object = clipboard.getContents(null);
			clipBoardStr = (String)object.getTransferData(DataFlavor.stringFlavor);
		}
		
		//doc,root�̐���
		Document doc =null;
		Element rootElement=null;
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			doc=docBuilder.parse(new File(inputXML));
			rootElement=doc.getDocumentElement();
		}
		
		//�u���}�b�v�쐻
		replaceMap=new HashMap<String,String>();
		{
			String pathStr=null;
			recursiveCheck(rootElement,pathStr);
		}
		
		//�u�����s
		for(Map.Entry<String, String> e : replaceMap.entrySet()) {
			//System.out.println(e.getKey() + " : " + e.getValue());
			String befStr=e.getKey();
			String aftStr=e.getValue();
			
			Pattern p=Pattern.compile(befStr,Pattern.MULTILINE);
			Matcher m=p.matcher(clipBoardStr);
			clipBoardStr=m.replaceAll(aftStr);
		}
		//System.out.println(clipBoardStr);
		
		//�N���b�v�{�[�h�̃Z�b�g
		//clipBoardStr,clipboard�˃N���b�v�{�[�h
		{
			StringSelection selection = new StringSelection(clipBoardStr);
			clipboard.setContents(selection, null);
		}
	}
	
	public static void recursiveCheck(Element curElement,String pathStr){
		NodeList dataList = curElement.getChildNodes();
		for(int i = 0; i < dataList.getLength(); i++) {
			Node node=dataList.item(i);
			if(node.getNodeType()==Node.TEXT_NODE)continue;
			
			//System.out.println(node.getNodeType());
			Element childElement=(Element)node;
			String curPath=null;
			if(pathStr==null)curPath=childElement.getTagName();
			else curPath=pathStr+sepStr+childElement.getTagName();
			
			if(childElement.hasAttribute("type") && childElement.getAttribute("type").equals("value")){
				//System.out.println("bef:"+childElement.getTextContent()+":aft");
				replaceMap.put(pathStr,childElement.getTextContent());
			}else recursiveCheck(childElement,curPath);
		}
	}
}