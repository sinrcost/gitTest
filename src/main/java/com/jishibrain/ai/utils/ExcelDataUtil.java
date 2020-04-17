package com.jishibrain.ai.utils;


/**
 * 操作Excel文件的常用方法，读取单元格数据、单元格写入数据、总列数、读取指定行列的数据、读取整个表数据
 * Created by sunxiufang on 2020/4/1 17:21
 */

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelDataUtil {

    private static Logger log = Logger.getLogger(ExcelDataUtil.class);
    private XSSFSheet ExcelWSheet;//sheet工作表对象
    private XSSFWorkbook ExcelWBook;//整个excel对象
    private XSSFCell Cell;//单元格
    private XSSFRow Row;//行对象
    private String filePath;//文件路径

    // 设定要操作的 Excel 的文件路径和 Excel 文件中的 sheet 名称
    // 在读写excel的时候，均需要先调用此方法，设定要操作的 excel 文件路径和要操作的 sheet 名称
    public ExcelDataUtil(String filepath) throws Exception {
        FileInputStream ExcelFile;
        try {
            // 实例化 excel 文件的 FileInputStream 对象
            ExcelFile = new FileInputStream(filepath);
            // 实例化 excel 文件的 XSSFWorkbook 对象
            ExcelWBook = new XSSFWorkbook(ExcelFile);

        } catch (Exception e) {
            throw (e);
        }
        this.filePath = filepath;
    }

    // 读取 excel 文件指定单元格的函数，此函数只支持后缀为xlsx的 excel 文件
    public String getCellData(String sheetName, int RowNum, int ColNum) throws Exception {
        ExcelWSheet = ExcelWBook.getSheet(sheetName);
        try {
            // 通过函数参数指定单元格的行号和列号，获取指定的单元格对象
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            //	System.out.println(Cell.getCellType());
            // 如果单元格的内容为字符串类型，则使用 getStringCellValue 方法获取单元格的内容
            // 如果单元格的内容为数字类型，则使用 getNumericCellValue() 方法获取单元格的内容
            String CellData = "";
            if (Cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                CellData = Cell.getStringCellValue();
            } else if (Cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                short format = Cell.getCellStyle().getDataFormat();
                if (HSSFDateUtil.isCellDateFormatted(Cell)) {
                    Date d = Cell.getDateCellValue();
                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                    CellData = formater.format(d);
                } else if (format == 14 || format == 31 || format == 57 || format == 58) {
                    // 日期
                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = DateUtil.getJavaDate(Cell.getNumericCellValue());
                    CellData = formater.format(date);
                } else if (format == 20 || format == 32) {
                    // 时间
                    DateFormat formater = new SimpleDateFormat("HH:mm");
                    Date date = DateUtil.getJavaDate(Cell.getNumericCellValue());
                    CellData = formater.format(date);
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    CellData = df.format(Cell.getNumericCellValue());
                }
            } else if (Cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
                CellData = "";
            } else {
                throw new RuntimeException("no support data type");
            }
            log.info("读取sheet工作表【" + sheetName + "】的第" + RowNum + "行第" + ColNum + "列的值为：" + CellData);
            return CellData;

        } catch (Exception e) {
            log.debug("读取sheet工作表【" + sheetName + "】的第" + RowNum + "行第" + ColNum + "列的值时异常：" + e);
            throw new RuntimeException("get excel data error-->" + e);
        }
    }

    // 在 excel 文件的执行单元格中写入数据，此函数只支持后缀为xlsx的 excel 文件写入
    public void setCellData(int RowNum, int ColNum, String sheetName, boolean testFlag) throws Exception {
        ExcelWSheet = ExcelWBook.getSheet(sheetName);
        try {
            // 获取 excel文件中的行对象
            Row = ExcelWSheet.getRow(RowNum);
            // 如果单元格为空，则返回 Null
            Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
            XSSFCellStyle style = ExcelWBook.createCellStyle();
            String resultValue = "";
            if (testFlag) {
                style.setFillForegroundColor(IndexedColors.GREEN.index);
                resultValue = "测试执行成功";

            } else {
                style.setFillForegroundColor(IndexedColors.RED.index);
                resultValue = "测试执行失败";
            }
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            if (Cell == null) {
                // 当单元格对象是 null 的时候，则创建单元格
                // 如果单元格为空，无法直接调用单元格对象的 setCellValue 方法设定单元格的值
                Cell = Row.createCell(ColNum);
                // 创建单元格后可以调用单元格对象的 setCellValue 方法设定单元格的值
                Cell.setCellValue(resultValue);

            } else {
                // 单元格中有内容，则可以直接调用单元格对象的 setCellValue 方法设定单元格的值
                Cell.setCellValue(resultValue);
            }

            Cell.setCellStyle(style);
            // 实例化写入 excel 文件的文件输出流对象
            FileOutputStream fileOut = new FileOutputStream(filePath);
            // 将内容写入 excel 文件中
            ExcelWBook.write(fileOut);
            // 调用flush 方法强制刷新写入文件
            fileOut.flush();
            // 关闭文件输出流对象
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("set excel data error-->" + e);
        }
    }

    public int getLastColIndex(String sheetName) {
        Sheet sheet = ExcelWBook.getSheet(sheetName);
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();// 总列数
        return colCount - 1;
    }


    /**
     * 读取知道区域的数据
     * @param sheetName
     * @param startRow
     * @param endRow
     * @param startCol
     * @param endCol
     * @return
     * @throws Exception
     */
    public Object[][] getTestData(String sheetName, int startRow, int endRow, int startCol, int endCol)
            throws Exception {
        // 通过 sheetName 参数,生成 sheet 对象
        Sheet sheet = ExcelWBook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();// 总行数
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();// 总列数
        if (endRow >= rowCount) {
            endRow = rowCount;
        }
        if (endCol >= colCount) {
            endCol = colCount - 1;
        }
        List<Object[]> records = new ArrayList<Object[]>();
        for (int i = startRow; i <= endRow; i++) {
            String fields[] = new String[endCol - startCol + 1];
            for (int j = startCol; j <= endCol; j++) {
                fields[j] = getCellData(sheetName, i, j);
            }
            records.add(fields);
        }

        // 定义函数返回值，即 Object[][]
        // 将存储测试数据的 list 转换为一个 Object 的二维数组
        // {[1,2,3,4,5]，[1,23,4,5,6]，[1,2,3,4,5]，[11,34,45,6,5]}
        Object[][] results = new Object[records.size()][];
        // 设置二维数组每行的值，每行是个object对象
        for (int i = 0; i < records.size(); i++) {
            results[i] = records.get(i);
        }
        // 关闭 excel 文件
        if (!(results.length > 0)) {
            log.error("数据参数文件读取为空，无法执行测试，请检查");
            throw new RuntimeException("dataprovider data is empty");
        }
        //ExcelWBook.close();
        return results;
    }

    public void close() throws Exception {
        ExcelWBook.close();
    }

    /**
     * 读取整个表的数据
     * @param sheetName
     * @return
     * @throws Exception
     */
    public Object[][] getTestData(String sheetName) throws Exception {
        // 通过 sheetName 参数,生成 sheet 对象
        Sheet Sheet = ExcelWBook.getSheet(sheetName);
        int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();// 总行数
        int colCount = Sheet.getRow(0).getPhysicalNumberOfCells();// 总列数
        return getTestData(sheetName, 1, rowCount, 0, colCount - 1);
    }

    /**
     * 读取关键词框架的表数据
     * @param sheetName
     * @return
     * @throws Exception
     */
    public Object[][] getKeywordData(String sheetName) throws Exception {
        // 通过 sheetName 参数,生成 sheet 对象
        Sheet sheet = ExcelWBook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();// 总行数
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();// 总列数
        List<Object[]> records = new ArrayList<Object[]>();
        for (int i = 1; i <= rowCount; i++) {
            if (getCellData(sheetName, i, 2).equalsIgnoreCase("y")) {
                String fields[] = new String[colCount - 1];
                for (int j = 0; j < colCount - 1; j++) {
                    fields[j] = getCellData(sheetName, i, j);
                }
                records.add(fields);
            }
        }
        // 定义函数返回值，即 Object[][]
        // 将存储测试数据的 list 转换为一个 Object 的二维数组
        // {{“”，“”，“”}，{“”，“”，“”}，{“”，“”，“”}，{“”，“”，“”}}
        Object[][] results = new Object[records.size()][];
        // 设置二维数组每行的值，每行是个object对象
        for (int i = 0; i < records.size(); i++) {
            results[i] = records.get(i);
        }
        // 关闭 excel 文件
        if (!(results.length > 0)) {
            log.error("数据参数文件读取为空，无法执行测试，请检查");
            throw new RuntimeException("dataprovider data is empty");
        }
        //ExcelWBook.close();
        return results;

    }



    public static void main(String[] args) throws Exception {

        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("登录");

        for (int i = 0; i < ob.length; i++) {
            Object[] obl = ob[i];
            System.out.println("");
            for (int j = 0; j < obl.length; j++) {
                System.out.print(obl[j] + "\t");
            }
        }
        excel.close();
    }



}

