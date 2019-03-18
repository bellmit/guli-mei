package com.guli.common.util;

import lombok.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：mei
 * @date ：Created in 2019/3/1 0024 下午 18:20
 * @description：Excel导入工具类
 * @modified By：
 * @version: $
 */
@Data
public class ExcelImportUtil {

    private HSSFFormulaEvaluator formulaEvaluator;
    private HSSFSheet sheet;
    /**
     * 日期格式
     */
    private String pattern;

    public ExcelImportUtil() {
        super();
    }

    public ExcelImportUtil(InputStream is) throws IOException {
        this(is, 0, true);
    }

    public ExcelImportUtil(InputStream is, int sheetIndex) throws IOException {
        this(is, sheetIndex, true);
    }

    public ExcelImportUtil(InputStream is, int sheetIndex, boolean evaluateFormular) throws IOException {
        super();
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        this.sheet = workbook.getSheetAt(sheetIndex);
        if (evaluateFormular) {
            this.formulaEvaluator = new HSSFFormulaEvaluator(workbook);
        }
    }

    /**
     * 获取表格Cell中的内容
     * @param cell
     * @return  String
     * @throws Exception
     */
    public String getCellValue(Cell cell) throws Exception {

        int cellType = cell.getCellType();
        switch (cellType) {
            //0:数值
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //日期
                    Date date = cell.getDateCellValue();
                    if (pattern != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        return sdf.format(date);
                    } else {
                        return date.toString();
                    }
                } else {
                    // 不是日期格式，则防止当数字过长时以科学计数法显示
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    return cell.toString();
                }
            //1文本
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            //2函数公式
            case Cell.CELL_TYPE_FORMULA:
                if (this.formulaEvaluator == null) {
                    //得到公式
                    return cell.getCellFormula();
                } else {//计算公式
                    CellValue evaluate = this.formulaEvaluator.evaluate(cell);
                    return evaluate.formatAsString();
                }
            //3空
            case Cell.CELL_TYPE_BLANK:
                //注意空和没有值不一样，从来没有录入过内容的单元格不属于任何数据类型，不会走这个case
                return "";
            //4逻辑值
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            //5错误值
            case Cell.CELL_TYPE_ERROR:
            default:
                throw new Exception("Excel数据类型错误");
        }
    }
}
