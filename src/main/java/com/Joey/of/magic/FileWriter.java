package com.Joey.of.magic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileWriter {

  public static void writeFile(String basePath, String filePName, List<String> content) {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd[HH:mm:ss]");
    String fileName = basePath + File.separator + sdf.format(new Date()) + "-" + filePName + ".txt";

    try {
      // 创建一个 File 对象，表示文件
      File file = new File(fileName);

      // 如果文件不存在，则创建文件
      if (!file.exists()) {
        file.createNewFile();
      }

      // 创建 FileWriter 对象，传递文件路径
      java.io.FileWriter fileWriter = new java.io.FileWriter(file, true); // 第二个参数 true 表示追加内容
      // 使用 BufferedWriter 来提高写入效率
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      // 将控制台内容写入文件
      for (String s : content) {
        bufferedWriter.write(s);
        bufferedWriter.newLine(); // 添加换行符
      }

      // 关闭 BufferedWriter
      bufferedWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
