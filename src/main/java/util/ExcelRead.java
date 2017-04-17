package util;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelRead
{
    public List<String> getValues(String filePath )
    {

        List<String> excelinfo = new ArrayList<String>();

        try{

            InputStream is = new FileInputStream(filePath);

            // 构造 XSSFWorkbook 对象，strPath 传入文件路径
            @SuppressWarnings("resource")
            XSSFWorkbook xwb = new XSSFWorkbook(is);

            // 读取第一章表格内容
            XSSFSheet sheet = xwb.getSheetAt(0);

            // 定义 row、cell
            XSSFRow row;

            // 循环输出表格中的内容
            for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {

                row = sheet.getRow(i);

                String lineinfo = null;

                for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {

                    // 通过 row.getCell(j).toString() 获取单元格内容，
                    String cell = row.getCell(j).toString();

                    if(lineinfo == null){

                        lineinfo = cell;

                    }else{

                        lineinfo = lineinfo +"_"+ cell;



                    }

                }
                excelinfo.add(lineinfo);

            }

        }catch(Exception e) {

            System.out.println("已运行xlRead() : " + e );

        }

        return excelinfo;

    }

}