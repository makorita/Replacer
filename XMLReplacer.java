import java.io.*;
import java.util.*;

import java.util.regex.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.w3c.dom.traversal.NodeIterator;

import org.apache.xpath.XPathAPI;

public class XMLReplacer{
	Document doc;
	public XMLReplacer() throws Exception{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		doc = docBuilder.parse(new File("tempDB.xml"));
	}
	
	public String replace(String originStr) throws Exception{
		String[] word=originStr.split("\n");
		Pattern pattern = Pattern.compile("<xml:.+?>");
		Matcher matcher = null;

		String returnStr=null;
		for(String curStr:word){	//çsâÒÇµ
			boolean writeFlag=false;
			
			//System.out.println(curStr);
			LinkedList<String> befList=new LinkedList<String>();
			LinkedList<LinkedList<String>> aftListList=new LinkedList<LinkedList<String>>();
			
			matcher = pattern.matcher(curStr);
			while (matcher.find()) {
				LinkedList<String> curAftList=new LinkedList<String>();
				aftListList.add(curAftList);
				
				String matched=matcher.group();
				befList.add(matched);
				matched=matched.replaceAll("^<xml:","");
				matched=matched.replaceAll(">$","");
				//System.out.println(matched);
				NodeIterator it = XPathAPI.selectNodeIterator(doc,matched);
				Node node;
				while( (node = it.nextNode()) != null ) {
					if(node.getTextContent()!=null){
						curAftList.add(node.getTextContent());
					}
				}
			}
			
			int index=0;
			while(true){
				//èIóπèåè
				//System.out.println(index);
				boolean breakFlag=true;
				for(LinkedList<String> curList:aftListList){
					if(curList.size()>index){
						breakFlag=false;
						break;
					}
				}
				if(breakFlag)break;
				
				String editStr=curStr;
				for(int i=0;i<befList.size();i++){
					LinkedList<String> curList=aftListList.get(i);
					if(curList.size()>index){
						//System.out.println(befList.get(i)+","+curList.get(index));
						editStr=editStr.replace(befList.get(i),curList.get(index));
					}
				}
				if(returnStr==null)returnStr=editStr+"\n";
				else returnStr+=editStr+"\n";
				writeFlag=true;
				
				index++;
			}
			
			if(!writeFlag){
				if(returnStr==null)returnStr=curStr+"\n";
				else returnStr+=curStr+"\n";
				writeFlag=true;
			}
		}
		
		return returnStr;
	}
}
