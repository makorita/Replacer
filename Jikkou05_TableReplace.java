import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.*;

public class Jikkou05_TableReplace{
	/*
	連番置換の複数行対応版
	*/
	public static void main(String args[]) throws Exception{
		//クリップボードの読み込み
		String clipBoardStr=null;
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
		{
			Transferable object = clipboard.getContents(null);
			clipBoardStr = (String)object.getTransferData(DataFlavor.stringFlavor);
		}
		
		LinkedList<LinkedList<String>> replaceTable=new LinkedList<LinkedList<String>>();
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream("ReplaceTable.xlsx"));
			Sheet sheet=wb.getSheetAt(0);
			for(int rowIndex=0;rowIndex<=sheet.getLastRowNum();rowIndex++){
				//System.out.println(rowIndex);
				Row row=sheet.getRow(rowIndex);
				if(row==null)continue;
				
				LinkedList<String> curList=new LinkedList<String>();
				for(int cellIndex=0;cellIndex<row.getLastCellNum();cellIndex++){
					//System.out.println(cellIndex);
					Cell cell=row.getCell(cellIndex);
					if(cell==null)curList.add(null);
					else if(cell.getCellType()==CellType.STRING)curList.add(cell.getStringCellValue());
					else if(cell.getCellType()==CellType.NUMERIC)curList.add(String.valueOf(cell.getNumericCellValue()));
					else curList.add(null);
				}
				replaceTable.add(curList);
			}
		}
		
		{
			String editStr=null;
			for(LinkedList<String> curList:replaceTable){
				String curEditStr=clipBoardStr;
				int index=1;
				for(String curStr:curList){
					//System.out.println(curStr);
					if(curStr!=null)curEditStr=curEditStr.replaceAll("<"+index+">",curStr);
					index++;
				}
				
				if(editStr==null)editStr=curEditStr;
				else editStr+=curEditStr;
			}
			
			clipBoardStr=editStr;
		}
		
		//クリップボードのセット
		//clipBoardStr,clipboard⇒クリップボード
		{
			StringSelection selection = new StringSelection(clipBoardStr);
			clipboard.setContents(selection, null);
		}
	}
}