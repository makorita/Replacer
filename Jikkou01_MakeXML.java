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
					if(i==curList.size()-1){
						parentElement.appendChild(doc.createTextNode(curList.get(i)));
					}else{
						Element curElement=doc.createElement(curList.get(i));
						parentElement.appendChild(curElement);
						parentElement=curElement;
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
}