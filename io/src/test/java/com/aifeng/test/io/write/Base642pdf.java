package com.aifeng.test.io.write;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/7/9 17:21
 */
public class Base642pdf {
    public static void main(String[] args) throws IOException {
        List<String> lis = Files.readAllLines(Paths.get("D:\\dd.txt"));

        File file = new File("D:\\test.pdf");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(Base64.getDecoder().decode(lis.get(0)));

        fos.flush();
        fos.close();
    }
}
