package com.aifeng.test.io.readline;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @Description: 文件行读
 * @author: imart·deng
 * @date: 2020/5/20 11:04
 */
public class TestFileLineRead {
    public static final String FILE_PATH = "C:\\Users\\dengxf\\Desktop\\necessary\\交接\\商户站\\shopee\\readTest\\12.csv";

    public static void main(String[] args) throws IOException {
        File file = new File(FILE_PATH);

        // scanner
        System.out.println("------------************************-------------");
        System.out.println(">>>>>>scanner 开始 >>>>>>>");
        Long cur = System.currentTimeMillis();
//        scanner(file);
        System.out.println("scanner 耗时[" + (System.currentTimeMillis() - cur) + "]ms");
        System.out.println("<<<<<<<scanner 结束 <<<<<<<");
        System.out.println("------------************************-------------");
        System.out.println();

        // buffer reader
        System.out.println("------------************************-------------");
        System.out.println(">>>>>>buffer reader 开始 >>>>>>>");
        cur = System.currentTimeMillis();
        bufferReader(file);
        System.out.println("buffer reader 耗时[" + (System.currentTimeMillis() - cur) + "]ms");
        System.out.println("<<<<<<<buffer reader 结束 <<<<<<<");
        System.out.println("------------************************-------------");
        System.out.println();

        // JDK8
        System.out.println("------------************************-------------");
        System.out.println(">>>>>>JDK8 stream开始 >>>>>>>");
        cur = System.currentTimeMillis();
        jdk8Reader(FILE_PATH);
        System.out.println("JDK8 stream 耗时[" + (System.currentTimeMillis() - cur) + "]ms");
        System.out.println("<<<<<<<JDK8 stream 结束 <<<<<<<");
        System.out.println("------------************************-------------");
        System.out.println();

        // apache common io
        System.out.println("------------************************-------------");
        System.out.println(">>>>>>apache common io >>>>>>>");
        cur = System.currentTimeMillis();
        commonIo(file);
        System.out.println("apache common io 耗时[" + (System.currentTimeMillis() - cur) + "]ms");
        System.out.println("<<<<<<<apache common io 结束 <<<<<<<");
        System.out.println("------------************************-------------");

        // line number reader
        System.out.println("------------************************-------------");
        System.out.println(">>>>>>lineNumberReader >>>>>>>");
        cur = System.currentTimeMillis();
        readLine(file);
        System.out.println("lineNumberReader 耗时[" + (System.currentTimeMillis() - cur) + "]ms");
        System.out.println("<<<<<<<lineNumberReader 结束 <<<<<<<");
        System.out.println("------------************************-------------");

    }

    private static void readLine(File file) throws IOException {
        int total = 0;
        try(LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file))){
            lineNumberReader.setLineNumber(1000);
            while (lineNumberReader.readLine() != null){
                total++;
            }
            System.out.println("[总行数]:" + lineNumberReader.getLineNumber());
        }finally {
            System.out.println("[总行数]:" + total);
        }
    }

    private static void commonIo(File file) throws IOException {
        int total = 0;
        try (LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8")) {
            while (lineIterator.hasNext()) {
                lineIterator.next();
                total += 1;
            }
        } finally {
            System.out.println("[总行数]:" + total);
        }
    }

    /**
     * JDK8
     *
     * @param filePath
     * @throws IOException
     */
    private static void jdk8Reader(String filePath) throws IOException {
        // Files.readAllLine 内部使用的是buffer reader
//        Files.readAllLines(file.toPath());
        int total = 0;
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            total = stream.reduce(0, (cur, op) -> cur + 1, (a, b) -> a + b);
        } finally {
            System.out.println("[总行数]:" + total);
        }
    }

    /**
     * buffer reader
     *
     * @param file
     * @throws IOException
     */
    private static void bufferReader(File file) throws IOException {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null; total++);
            // line is not visible here.
        } finally {
            System.out.println("[总行数]:" + total);
        }
    }

    private static void scanner(File file) throws FileNotFoundException {
        int total = 0;
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                total+=1;
            }
        } finally {
            System.out.println("[总行数]:" + total);
        }
    }
}
