package com.Joey.of.magic;

import java.io.File;

public class PathFetcher {

    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");  // 获取当前工作目录
        String configFilePath = currentDirectory + File.separator + "config.xml";


        System.out.println(configFilePath);

    }


}
