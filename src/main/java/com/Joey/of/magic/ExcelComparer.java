package com.Joey.of.magic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.Joey.of.magic.model.Record;

import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelComparer {

    public static void main(String[] args) throws Exception {
        // 文件路径
        String currentDirectory = System.getProperty("user.dir");  // 获取当前工作目录
        Map<String, String> map = XmlReader.readXml(currentDirectory);
        String filePath1 = map.get("f1");
        String filePath2 = map.get("f2");
        String file1Name = map.get("f1Name");
        String file2Name = map.get("f2Name");
        filePath1 = filePath1 + file1Name;
        filePath2 = filePath2 + file2Name;

        String key = map.get("k");
        Integer kI = Integer.valueOf(key);
        kI -= 1;
        String l = map.get("l");
        Integer lI = Integer.valueOf(l);

        // 读取 Excel 并构建 Map
        Map<String, Record> map1 = readExcel(filePath1, lI, kI, file1Name);
        Map<String, Record> map2 = readExcel(filePath2, lI, kI, file2Name);
//        01949 02336 06949
        // 对比两个 Map
        List<String> resultMessage = compareMaps(map1, map2, kI);

//        for (String s : resultMessage) {
//            System.out.println(s);
//        }

        String logName = "对比" + file1Name + "和" + file2Name;
        FileWriter.writeFile(currentDirectory, logName, resultMessage);
    }

    // 读取 Excel 文件并返回 Map
    public static Map<String, Record> readExcel(String filePath, Integer l, Integer kI, String tableName) throws IOException {

        Map<String, Record> recordMap = new HashMap<>();
        FileInputStream fis = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            HashMap<Integer, String> excelKVMap = new HashMap<>();
            for (Integer i = 0; i < l; i++) {

//            }
                String stringCellValue = getStringCellValue(row, i);
                excelKVMap.put(i, stringCellValue);
            }
            String recordKeyValue = excelKVMap.get(kI);

            recordMap.put(recordKeyValue, new Record(excelKVMap, tableName));
        }
        fis.close();
        return recordMap;
    }

    // getStringCellValue 方法，增加判空逻辑
    private static String getStringCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return ""; // 如果单元格为空，返回空字符串
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return ""; // 对于其他类型（如公式、空值等），返回空字符串
        }
    }

    // 对比两个 Map
    public static List<String> compareMaps(Map<String, Record> map1, Map<String, Record> map2, Integer kI) {
        Set<String> allKeys = new HashSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());

        List<String> resultMessageList = new ArrayList<>();
//        System.out.println("总数" + allKeys.size());
        resultMessageList.add("总数" + allKeys.size());

        List<String> collect = allKeys.stream().sorted().collect(Collectors.toList());
        // 对比两个 Excel 文件中的数据
        for (String key : collect) {
            Record record1 = map1.get(key);
            Record record2 = map2.get(key);

            if (record1 != null && record2 != null) {

                String message = record1.compareNew(record2);
                if (StringUtil.isBlank(message)) {
                    resultMessageList.add(
                        "表" + record1.getRecordName() + "表" + record2.getRecordName() + "的-列" + Record.getExcelColumnName(kI) + " = " + key + "的数据是相同的");
                } else {
                    resultMessageList.add("表" + record1.getRecordName() + "表" + record2.getRecordName() + "的-列" + Record.getExcelColumnName(kI) + " = " + key + "存在差异:");
                    resultMessageList.add(message);
                }

            } else if (record1 != null) {
                resultMessageList.add("数据：" + key + "仅出现在"
//                    + record1.getRecordName() + "中,数据"
                    + record1);
            } else {
                resultMessageList.add("数据：" + key + "仅出现在"
//                    + record2.getRecordName() + "中,数据"
                    + record2);
            }
        }

        return resultMessageList;
    }

//    private String getAlpByNum(Integer num) {
//        if (num > 26) {
//
//        } else {
//            String s = "a" + num;
//        }
//
//    }
//

}
