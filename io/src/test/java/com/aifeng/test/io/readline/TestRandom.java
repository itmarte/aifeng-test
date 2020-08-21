package com.aifeng.test.io.readline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/6/1 17:54
 */
public class TestRandom {
    public static final String FILE_PATH = "C:\\Users\\dengxf\\Desktop\\necessary\\交接\\商户站\\shopee\\readTest\\1.csv";
    public static void main2(String[] args) throws IOException {
        // 开始写文件
        RandomAccessFile accessFile = new RandomAccessFile(new File(FILE_PATH), "rw");
        accessFile.skipBytes(20);
        for (int i =0; i<10;i++)
        accessFile.write("hello BB ;\n".getBytes());
        accessFile.close();
    }

    public static void main(String[] args) throws IOException {
        int i = 0;
        System.out.println(i++);

        String upath = "/home/dubbo/file.name";
        System.out.println(upath.substring(upath.lastIndexOf("/")+1));

        System.out.println(Long.valueOf("201905063734534"));
        String merchantId = "201905063734534";
        int total = 2;

        System.out.println(Long.valueOf(merchantId) % total == 1);

        String batchFile = "C:\\Users\\dengxf\\Documents\\测试文档\\sftp\\201812147313047\\参考文件\\instraction\\20190814_201904027720518_1565746015TradeOrder.csv";

        String newFileFormat = "C:\\Users\\dengxf\\Documents\\测试文档\\sftp\\201812147313047\\参考文件\\20200618_201812147313047_%s.REQ";


        int j = 80;
        List<String> lines = Files.readAllLines(Paths.get(batchFile));
        while (j < 100){
            // 开始写文件
            String batchId = "1565746015TradeOrder"+ j;
            try(RandomAccessFile newFile = new RandomAccessFile(new File(String.format(newFileFormat, batchId)), "rw")) {
                final int tmp = j;
//                int tt = 0;
//                for (String line : lines) {
//                    if(tt > 10000) break;
//                    if(tt == 0){
//
//                    }
//                    if(tt > 0){
//                        try {
//                            newFile.write((tmp+line+"\n").getBytes());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    tt++;
//                }
                Count count = new Count();
                lines.forEach(line -> {
                    try {
                        if(count.i > 0){
                            newFile.write((tmp+line.replaceAll(",", "|")+ "\n").getBytes());
                        }else {
                            newFile.write((line.replaceAll(",", "|")+ "\n").getBytes());
                        }
                        count.i++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            j++;
        }
    }

    static class Count {
        int i;
    }
}
