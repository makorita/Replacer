import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.*;

public class Jikkou03_ListExcelReplace{
	/*
	<list>の内容をリストで置換していく
	*/
	public static void main(String args[]) throws Exception{
		//クリップボードの読み込み
		String clipBoardStr=null;
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
		{
			Transferable object = clipboard.getContents(null);
			clipBoardStr = (String)object.getTransferData(DataFlavor.stringFlavor);
		}
		
		LinkedList<String> replaceList=new LinkedList<String>();
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream("ReplaceList.xlsx"));
			Sheet sheet=wb.getSheetAt(0);
			for(int rowIndex=0;rowIndex<=sheet.getLastRowNum();rowIndex++){
				//System.out.println(rowIndex);
				Row row=sheet.getRow(rowIndex);
				if(row==null)continue;
				
				Cell cell=row.getCell(0);
				if(cell==null)continue;
				else if(cell.getCellType()==CellType.STRING)replaceList.add(cell.getStringCellValue());
				else if(cell.getCellType()==CellType.NUMERIC)replaceList.add(String.valueOf(cell.getNumericCellValue()));
			}
		}
		
		{
			Pattern p1=Pattern.compile("<list>",Pattern.MULTILINE);
			int index=0;
			while(true){
				Matcher m1=p1.matcher(clipBoardStr);
				if(!m1.find())break;
				
				String aftStr=replaceList.get(index);
				clipBoardStr=m1.replaceFirst(aftStr);
				if(index==replaceList.size()-1)index=0;
				else index++;
			}
		}
		
		//クリップボードのセット
		//clipBoardStr,clipboard⇒クリップボード
		{
			StringSelection selection = new StringSelection(clipBoardStr);
			clipboard.setContents(selection, null);
		}
	}
}