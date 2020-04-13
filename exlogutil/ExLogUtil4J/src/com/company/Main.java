package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder sbuilder = new StringBuilder();
        try {
            System.out.println("开始读取日志文件...");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String logLevel = tempString.length() > 26 ? tempString.substring(21, 26).trim() : "";
                if ("WARN".equals(logLevel) || "ERROR".equals(logLevel)){
                    if(sbuilder.length() > 0){
                        System.out.println(sbuilder.toString());
                        sbuilder = new StringBuilder();
                    }
                    // 显示行号
                    System.out.println(line + ": " + tempString);
                } else if ("DEBUG".equals(logLevel) || "INFO".equals(logLevel)){
                    if(sbuilder.length() > 0){
                        System.out.println(sbuilder.toString());
                        sbuilder = new StringBuilder();
                    }
                } else {
                    sbuilder.append(line + ": " + tempString).append("\n");
                }
                line++;
            }
            if(sbuilder.length() > 0){
                System.out.println(sbuilder.toString());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "./resource/test.log";
        readFileByLines(filePath);
//            String log = "2017-12-17 16:01:15 [INFO ] [HTTP-8088-exec-340] [PublishMngMgrImpl::findPublishMng] storeId355741,ssidqingyang-EDU";
//            System.out.println(log.substring(21, 27));

    }
}
