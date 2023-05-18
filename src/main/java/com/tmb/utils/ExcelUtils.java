package com.tmb.utils;

import com.tmb.constants.FrameworkConstants;
import com.tmb.exceptions.FrameworkException;
import com.tmb.exceptions.InvalidPathForExcelException;
import com.tmb.reports.ExtentManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

import static com.tmb.enums.LogType.*;
import static com.tmb.enums.LogType.PASS;
import static com.tmb.reports.FrameworkLogger.log;

/**
 * Utility class to read or write to excel.
 *
 * <pre>
 * <b>
 * <a href="https://www.youtube.com/channel/UC6PTXUHb6j4Oxf0ccdRI11A">Testing Mini Bytes Youtube channel</a>
 * </b>
 * </pre>
 * <p>
 * Jan 22, 2021
 *
 * @author Amuthan Sakthivel
 * @version 1.0
 * @see com.tmb.listeners.MethodInterceptor
 * @see DataProviderUtils
 * @since 1.0
 */
public final class ExcelUtils {

    /**
     * Private constructor to avoid external instantiation
     */
    private ExcelUtils() {
    }


    /**
     * Encapsulates all the value from the mentioned excel sheet and store it in
     * List holding HashMaps. Key for the map in the column header in the excel sheet.
     *
     * @param sheetname Excel sheetname to read the value from
     * @return List where each index holds a map and Each map holds the details about the test
     * TODO create reusable methods
     * @author Amuthan Sakthivel
     * Jan 22, 2021
     */
    public static List<Map<String, String>> getTestDetails(String sheetname) {
        List<Map<String, String>> list = null;

        try (FileInputStream fs = new FileInputStream(FrameworkConstants.getExcelpath())) {

            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheet(sheetname);

            int lastrownum = sheet.getLastRowNum();
            int lastcolnum = sheet.getRow(0).getLastCellNum();

            Map<String, String> map;
            list = new ArrayList<>();
            boolean isTestCaseCellEmpty = false;
            for (int i = 1; i <= lastrownum; i++) {
                map = new HashMap<>();
                for (int j = 0; j < lastcolnum; j++) {
                    String key = "";
                    String value = "";
                    try {
                        key = Objects.toString(new DataFormatter().formatCellValue(sheet.getRow(0).getCell(j)), "");
                        value = Objects.toString(new DataFormatter().formatCellValue(sheet.getRow(i).getCell(j)), "");
                        //System.out.println("Excel Data fetch : Key '" + key + "' , Value '" + value + "'");
                        if (value.equalsIgnoreCase(FrameworkConstants.getEndOfTestString())) {
                            isTestCaseCellEmpty = true;
                            break;
                        }
                        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                            map.put(key, value);
                        }
                    } catch (Exception e) {
                        if (key.equals("testname")) {
                            e.printStackTrace();
                            isTestCaseCellEmpty = true;
                            break;
                        }
                    }
                }
                if (!isTestCaseCellEmpty) {
                    list.add(map);
                }
                if (isTestCaseCellEmpty) {
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            throw new InvalidPathForExcelException("Excel File you trying to read is not found");
        } catch (IOException e) {
            throw new FrameworkException("Some io exception happened  while reading excel file");
        }
        System.out.println("Sheet name : " + sheetname);
        list.forEach(System.out::println);
        return list;
    }


    /*private static void writeTestData(String columnHeader, String valueToSave) {
        try (FileInputStream fsio = new FileInputStream(FrameworkConstants.getExcelpath())) {
            XSSFWorkbook workbook = new XSSFWorkbook(fsio);
            XSSFSheet sheet = workbook.getSheet(FrameworkConstants.getIterationDatasheet());

            int lastrownum = sheet.getLastRowNum();
            int lastcolnum = sheet.getRow(0).getLastCellNum();
            boolean isColumnHeaderExists = false;
            for (int i = 0; i < lastcolnum; i++) {
                if (columnHeader.trim().equals(Objects.toString(new DataFormatter().formatCellValue(sheet.getRow(0).getCell(i))))) {
                    isColumnHeaderExists = true;
                }
            }
            if (!isColumnHeaderExists) {
                try {
                    Cell cell = sheet.getRow(0).createCell(lastcolnum);
                    cell.setCellValue(columnHeader);
                    FileOutputStream outputStream = new FileOutputStream(FrameworkConstants.getExcelpath());
                    workbook.write(outputStream);
                    outputStream.close();
                    log(INFO, "Excel : New column inserted : " + columnHeader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <= lastrownum; i++) {
                Cell cell = sheet.getRow(i).getCell(0);
                String value = Objects.toString(new DataFormatter().formatCellValue(cell), "");
                if (FrameworkConstants.getCurrentTestName().equalsIgnoreCase(value.trim())) {
                    log(INFO, "Excel : New data '" + valueToSave + "' inserted in column '" + columnHeader + "'");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
