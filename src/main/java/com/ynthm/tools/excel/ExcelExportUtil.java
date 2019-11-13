package com.ynthm.tools.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

/**
 * 导出
 *
 * @param <T>
 */
public class ExcelExportUtil<T> {


    public void export() throws IOException {
        String fileName = "sxssf.xlsx";
        OutputStream os = new FileOutputStream(fileName);
    }

    public void export(String title, String[] headers, Collection<T> dataset, OutputStream os) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 每次写100行数据，就刷新数据出缓存
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        Sheet sh = wb.createSheet(title);
        sh.setDefaultColumnWidth(20);

        Cell cell = null;
        Row row1 = sh.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            cell = row1.createCell(i);
            cell.setCellValue(new XSSFRichTextString(headers[i]));
        }

        Iterator<T> iterator = dataset.iterator();
        int index = 0;
        T rowData;
        Row row;
        Field[] declaredFields;
        String getMethodName;
        Object value;

        while (iterator.hasNext()) {
            index++;
            rowData = iterator.next();
            row = sh.createRow(index);

            declaredFields = rowData.getClass().getDeclaredFields();

            for (int i = 0; i < declaredFields.length; i++) {
                String filedName = declaredFields[0].getName();
                cell = row.createCell(i);
                getMethodName = "get" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
                value = rowData.getClass().getMethod(getMethodName, new Class[]{}).invoke(rowData, new Object[]{});
                if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Float) {
                    cell.setCellValue((Float) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } else if (value instanceof Long) {
                    cell.setCellValue((Long) value);
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else {
                    cell.setCellValue(value.toString());
                }

            }

        }

        try {
            wb.write(os);
        } catch (IOException e) {

        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(wb);
        }

    }
}
