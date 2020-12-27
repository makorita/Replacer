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

public class Jikkou01_MakeXML{
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
		Document doc =null;
		Element rootElement=null;
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			//root elements
			doc=docBuilder.newDocument();
			rootElement = doc.createElement("data");
			doc.appendChild(rootElement);

		}
		
		//インプットパスの読み込み
		LinkedList<LinkedList<String>> inputList=new LinkedList<LinkedList<String>>();
		{
			String word[]=clipBoardStr.split("\n");
			for(String tmpStr:word){
				LinkedList<String> curList=new LinkedList<String>();
				String[] word2=tmpStr.split("\t");
				for(String curStr:word2){
					if(curStr.length()==0)continue;
					curList.add(curStr);
					//System.out.println(curStr);
				}
				
				if(curList.size()>=2)inputList.add(curList);
			}
		}
		
		//xmlへのデータ登録
		{
			for(LinkedList<String> curList:inputList){
				Element parentElement=rootElement;
				for(int i=0;i<curList.size();i++){
					String tmpStr=curList.get(i);
					tmpStr=normalize(tmpStr);
					
					if(i==curList.size()-1){
						parentElement.appendChild(doc.createTextNode(tmpStr));
					}else if(tmpStr.matches(".*:(.+=.+,)*.+=.+")){	//attributeの取得
						
						String[] word1=tmpStr.split(":");
						String[] attributeList=word1[1].split(",");
						//System.out.println(parentElement.getTagName()+","+word1[0]);
						
						boolean existFlag=false;
						NodeList childList=parentElement.getElementsByTagName(word1[0]);
						//System.out.println(childList.getLength());
						if(childList.getLength()>0){
							for(int j = 0; j < childList.getLength(); j++) {
								Node node = childList.item(j);
								Element childElement = (Element)node;
								//System.out.println(childElement.getTagName());
								existFlag=true;
								for(String curStr:attributeList){
									String[] word2=curStr.split("=");
									if(!childElement.getAttribute(word2[0]).equals(word2[1])){
										existFlag=false;
										break;
									}
								}
								
								if(existFlag){
									parentElement=childElement;
									break;
								}
							}
						}
						
						if(!existFlag){
							Element curElement = doc.createElement(word1[0]);
							for(String curStr:attributeList){
								String[] word2=curStr.split("=");
								curElement.setAttribute(word2[0],word2[1]);
							}
							parentElement.appendChild(curElement);
							parentElement=curElement;
						}
					}else{
						boolean existFlag=false;
						NodeList childList=parentElement.getElementsByTagName(tmpStr);
						if(childList.getLength()>0){
							for(int j = 0; j < childList.getLength(); j++) {
								Node node = childList.item(j);
								Element childElement = (Element)node;
								if(childElement.getAttributes().getLength()==0){
									existFlag=true;
									parentElement=childElement;
									break;
								}
							}
						}
						if(!existFlag){
							Element curElement=doc.createElement(tmpStr);
							parentElement.appendChild(curElement);
							parentElement=curElement;
						}
					}
				}
			}
		}
		
		//XMLファイルの保存
		{
			TransformerFactory tfFactory = TransformerFactory.newInstance();
			Transformer tf = tfFactory.newTransformer();

			tf.setOutputProperty("indent", "yes");
			tf.setOutputProperty("encoding", "UTF-8");

			tf.transform(new DOMSource(doc), new StreamResult("tempDB.xml"));
		}
	}
	
	static String normalize(String originalStr){
		String returnStr=originalStr;
		returnStr=returnStr.replace("①","1");
		returnStr=returnStr.replace("②","2");
		returnStr=returnStr.replace("③","3");
		returnStr=returnStr.replace("④","4");
		returnStr=returnStr.replace("⑤","5");
		returnStr=returnStr.replace("⑥","6");
		returnStr=returnStr.replace("⑦","7");
		returnStr=returnStr.replace("⑧","8");
		returnStr=returnStr.replace("⑨","9");
		returnStr=returnStr.replace("⑩","10");
		
		return returnStr;
	}
}