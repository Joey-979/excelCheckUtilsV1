package com.Joey.of.magic.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.MapUtils;


public class Record {


    private String recordName;

    private Map<Integer, String> valueMap;

    public Record(Map<Integer, String> valueMap, String recordName) {
        this.valueMap = valueMap;
        this.recordName = recordName;
    }

    public Map<Integer, String> getValueMap() {
        return valueMap;
    }


    public String getRecordName() {
        return recordName;
    }

    @Override
    public String toString() {
        List<Integer> collect = MapUtils.emptyIfNull(this.valueMap).keySet().stream().sorted().collect(Collectors.toList());
        StringBuilder result = new StringBuilder(recordName + "\n");
        for (Integer i : collect) {
            result.append("【").append(getExcelColumnName(i)).append(": ").append(valueMap.get(i)).append("】").append(" ");
        }
        result.append("\n");
        return result.toString();

    }


    public String compareNew(Record record) {
        Map<Integer, String> valueMapP = record.getValueMap();
        Map<Integer, String> valueMapT = this.getValueMap();
        HashSet<Integer> keySet = new HashSet<>(valueMapP.keySet());
        List<Integer> collect = keySet.stream().sorted().collect(Collectors.toList());
        keySet.addAll(valueMapT.keySet());

        StringBuilder diffMessage = new StringBuilder();
        for (Integer key : collect) {
            if (!StringUtils.equals(valueMapP.get(key), valueMapT.get(key))) {
                diffMessage.append("差异列:").append(getExcelColumnName(key)).append("-差异值：").append(recordName).append("：【").append(valueMapT.get(key)).append("】-compareTo：")
                    .append(record.getRecordName()).append("：【").append(valueMapP.get(key)).append("】；").append("\n");
            }
        }

        return diffMessage.toString();
    }


    public static String getExcelColumnName(int columnNumber) {
        columnNumber += 1;
        StringBuilder columnName = new StringBuilder();

        while (columnNumber > 0) {
            columnNumber--;  // Adjust for 1-indexed system
            columnName.insert(0, (char) ('A' + columnNumber % 26));
            columnNumber /= 26;
        }

        return columnName.toString();
    }

}
