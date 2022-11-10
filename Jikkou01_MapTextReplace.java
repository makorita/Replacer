import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.datatransfer.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.*;

public class Jikkou01_MapTextReplace{
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
			//行末の改行削除
			clipBoardStr=clipBoardStr.replaceAll("\n","");
		}
		
		//ReplaceMap.txtの更新
		PrintWriter wr = new PrintWriter("ReplaceMap.txt", Charset.forName("UTF-8"));
		{
			
			Workbook wb = WorkbookFactory.create(new FileInputStream("ReplaceTable.xlsx"));
			Sheet sheet=wb.getSheet("変換テーブル");
			//変換列インデックスを取得
			int aftColNum=-1;
			Row headerRow=sheet.getRow(0);
			for(int cellIndex=0;cellIndex<headerRow.getLastCellNum();cellIndex++){
				Cell cell=headerRow.getCell(cellIndex);
				String cellStr=cell.getStringCellValue();
				if(cellStr.equals(clipBoardStr)){
					aftColNum=cellIndex;
					break;
				}
			}
			if(aftColNum==-1)System.exit(0);
			
			//ReplaceMap.txtの更新
			for(int rowIndex=1;rowIndex<=sheet.getLastRowNum();rowIndex++){
				//System.out.println(rowIndex);
				Row row=sheet.getRow(rowIndex);
				if(row==null)continue;
				
				Cell befCell=row.getCell(0);
				String befCellStr=null;
				if(befCell==null)continue;
				else if(befCell.getCellType()==CellType.STRING)befCellStr=befCell.getStringCellValue();
				else if(befCell.getCellType()==CellType.NUMERIC)befCellStr=String.valueOf((int)befCell.getNumericCellValue());
				else if(befCell.getCellType()==CellType.BLANK)continue;
				else continue;
				
				Cell aftCell=row.getCell(aftColNum);
				String aftCellStr=null;
				if(aftCell==null)aftCellStr="";
				else if(aftCell.getCellType()==CellType.STRING)aftCellStr=aftCell.getStringCellValue();
				else if(aftCell.getCellType()==CellType.NUMERIC)aftCellStr=String.valueOf((int)aftCell.getNumericCellValue());
				else if(aftCell.getCellType()==CellType.BLANK)aftCellStr="";
				else continue;
				
				wr.println(befCellStr+"\t"+aftCellStr);
			}
		}
		wr.close();
	}
}