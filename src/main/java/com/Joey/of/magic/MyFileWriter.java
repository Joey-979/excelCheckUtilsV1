package com.Joey.of.magic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyFileWriter {

    public static void writeFile(String basePath, String filePName, List<String> content, String pre) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd[HH_mm_ss]");
        String fileName = pre + "-" + sdf.format(new Date()) + "-" + filePName + ".txt";
        fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
        fileName = basePath + File.separator + fileName;

        // 创建一个 File 对象，表示文件
        File file = new File(fileName);

        // 如果文件不存在，则创建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("文件创建异常");
                throw new RuntimeException(e);
            }
        }

        try (FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            // 将控制台内容写入文件
            for (String s : content) {
                bufferedWriter.write(s);
                bufferedWriter.newLine(); // 添加换行符
            }
            // 关闭 BufferedWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
