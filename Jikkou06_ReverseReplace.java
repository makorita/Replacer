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

public class Jikkou06_ReverseReplace{
	/*
	置換マップを元にクリップボードを置換する
	*/
	static String clipBoardStr=null;
	static String editStr=null;
	
	public static void main(String args[]) throws Exception{
		String inputXML="ReplaceXML.xml";
		
		//クリップボードの読み込み
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
		{
			Transferable object = clipboard.getContents(null);
			clipBoardStr = (String)object.getTransferData(DataFlavor.stringFlavor);
			//clipBoardStr="感";
		}
		
		//rootの取得
		Element rootElement=null;
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc=docBuilder.parse(new File(inputXML));
			rootElement=doc.getDocumentElement();
		}
		
		//クリップボード文字を含むノードを再起検索。
		{
			recursiveCheck(rootElement);
		}
		
		//クリップボードのセット
		//clipBoardStr,clipboard⇒クリップボード
		{
			StringSelection selection = new StringSelection(editStr);
			clipboard.setContents(selection, null);
		}
	}
	
	static void recursiveCheck(Element curElement){
		NodeList childList = curElement.getChildNodes(); 
		for(int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			//System.out.println(node);
			if(node.getNodeType()!=Node.ELEMENT_NODE)continue;
			
			Element childElement = (Element)node;
			if(childElement.hasAttribute("type") && childElement.getAttribute("type").equals("value") && childElement.getTextContent().matches(".*"+clipBoardStr+".*")){
				//System.out.println(childElement.getTextContent());
				String pathStr=childElement.getTagName();
				Element tmpElement=childElement;
				while(true){
					Element parentElement=(Element)tmpElement.getParentNode();
					if(parentElement==null || parentElement.getTagName().equals("data"))break;
					
					//System.out.println(parentElement);
					pathStr=parentElement.getTagName()+"##"+pathStr;
					
					tmpElement=parentElement;
				}
				//System.out.println(pathStr);
				if(editStr==null)editStr=pathStr;
				else editStr=editStr+"\n"+pathStr;
				
			}else if(childElement.hasChildNodes())recursiveCheck(childElement);
		}
	}
}