package com.Joey.of.magic;

import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlReader {

    public static Map<String, String> readXml(String path) throws Exception {

        String configFilePath = path + File.separator + "config.xml";

        // 创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 加载XML文件
        File xmlFile = new File(configFilePath);
        Document document = builder.parse(xmlFile);

        // 获取根元素
        Element rootElement = document.getDocumentElement();

        HashMap<String, String> result = new HashMap<>();

        // 获取file1Path元素的值
        String file1Path = getTagValue(rootElement, "file1Path");
        String file1Name = getTagValue(rootElement, "file1Name");
//        System.out.println("file1Path: " + file1Path);
        result.put("f1", file1Path);
        result.put("f1Name", file1Name);

        // 获取file2Path元素的值
        String file2Path = getTagValue(rootElement, "file2Path");
        String file2Name = getTagValue(rootElement, "file2Name");
//        System.out.println("file2Path: " + file2Path);
        result.put("f2", file2Path);
        result.put("f2Name", file2Name);

        // 获取keyWord元素的值
        String keyWord = getTagValue(rootElement, "keyWord");
//        System.out.println("keyWord: " + keyWord);
        result.put("k", keyWord);

        String length = getTagValue(rootElement, "length");
        result.put("l", length);

        String outFilePre = getTagValue(rootElement, "outFilePre");
        result.put("pre", outFilePre);

        return result;

    }

    // 获取指定标签的值
    private static String getTagValue(Element root, String tagName) {
        NodeList nodeList = root.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }


}
