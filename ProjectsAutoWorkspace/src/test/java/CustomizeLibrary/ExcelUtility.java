package CustomizeLibrary;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class ExcelUtility {
    XSSFWorkbook wb;
    XSSFSheet sheet;

    public ExcelUtility(){

    }

    public ExcelUtility(String fileNameAndPath,String sheetName) throws IOException {
        File fileExcel=new File(fileNameAndPath);
        FileInputStream inputStream= new FileInputStream(fileExcel);
        wb=new XSSFWorkbook(inputStream);
        sheet=wb.getSheet(sheetName);
    }


    /**
     * This function is used for getting the value in the excel at the row index and the column index
     * @param row the index of row (the first row should be 0 not 1)
     * @param column the index of column (the first column should be 0 not 1)
     * @return the value at row index and column column which are provided. in case there is no value in the cell, then empty string value will return.
     */
    public String getValueAt(int row, int column){
        String value="";
        try{
            /*value=sheet.getRow(row).getCell(column, Row.RETURN_BLANK_AS_NULL).getStringCellValue();
            if(value.length()==0){
                value=" ";
            }
            return value;
            */

            Row prow=sheet.getRow(row);
            Cell pcell=prow.getCell(column);
            FormulaEvaluator evaluator=wb.getCreationHelper().createFormulaEvaluator();
            CellValue cellValue=evaluator.evaluate(pcell);

            switch (cellValue.getCellType()){
                case Cell.CELL_TYPE_STRING:
                    value=cellValue.getStringValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value=" ";
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value= Double.toString(cellValue.getNumberValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value=Boolean.toString(pcell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    break;
            }

            return value;
        }
        catch(Exception ex){
            return ex.getMessage();
        }
    }


    /**
     * This function is used for getting the total row in the sheet of file excel
     * @return the total row of a sheet in file excel
     */
    public int GetTotalRows(){
        try{
            return  sheet.getLastRowNum()-sheet.getFirstRowNum();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return -1;
        }
    }
}