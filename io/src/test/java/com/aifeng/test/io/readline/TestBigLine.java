package com.aifeng.test.io.readline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/7/1 10:16
 */
public class TestBigLine {
    public static String splitPattern = ",";
    public static final String ANDROIDELLIPSIZE = " ...";
    public static final int MAX_LENGTH = 1024;
    public static final int TRADE_NAME_INDEX = 8;

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\dengxf\\Documents\\测试文档\\sftp\\201812147313047\\参考文件\\20200630\\20200626_201812147313047_1593163619TradeOrder.REQ";

        Count count = new Count();

        Files.readAllLines(Paths.get(path)).stream().filter(line -> line.length()>1024).forEach(line -> {
            System.out.println(getSafeLine(line));
        });
    }

    public static void main2(String[] args) {
        String s = "STS_ESCROW_200604TAYNSG2T|0|20200604003912|THB|65|224657380036|june_xx.|THA|INS  ไดโนเสาร์เกาหลี Super น่ารักน่ารักยืดหยุ่น Hairline อุปกรณ์ผมสาว Wholesale xwINS  ไดโนเสาร์เกาหลี Super น่ารักน่ารักยืดหยุ่น Hairline อุปกรณ์ผมสาว Wholesale xwINS  ไดโนเสาร์เกาหลี Super น่ารักน่ารักยืดหยุ่น Hairline อุปกรณ์ผมสาว Wholesale xwINS  ไดโนเสาร์เกาหลี Super น่ารักน่ารักยืดหยุ่น Hairline อุปกรณ์ผมสาว Wholesale xwINS  ไดโนเสาร์เกาหลี Super น่ารักน่ารักยืดหยุ่น Hairline อุปกรณ์ผมสาว Wholesale xwINS ผู้ใหญ่เพศหญิงยางรัดผูกผม Tousheng เครือข่ายแหวนผมอะโวคาโดสีแดงเชือกผ้าโพกศีรษะผมเกาหลี 2019 ใหม่ Wholesale xwINS ผู้ใหญ่เพศหญิงยางรัดผูกผม Tousheng เครือข่ายแหวนผมอะโวคาโดสีแดงเชือกผ้าโพกศีรษะผมเกาหลี 2019 ใหม่ Wholesale xwINS ผู้ใหญ่เพศหญิงยางรัดผูกผม Tousheng เครือข่ายแหวนผมอะโวคาโดสีแดงเชือกผ้าโพกศีรษะผมเกาหลี 2019 ใหม่ Wholesale xwINS ผู้ใหญ่เพศหญิงยางรัดผูกผม Tousheng เครือข่ายแหวนผมอะโวคาโดสีแดงเชือกผ้าโพกศีรษะผมเกาหลี 2019 ใหม่ Wholesale xwINS ผู้ใหญ่เพศหญิงยางรัดผูกผม Tousheng เครือข่ายแหวนผมอะโวคาโดสีแดงเชือกผ้าโพกศีรษะผมเกาหลี 2019 ใหม่ Wholesale xwINS  Strings Purse เงินพร้อมหัวไข่, ความยืดหยุ่นสูงและสด, อุปกรณ์ผมหนังน่ารัก Wholesale xwINS  Strings Purse เงินพร้อมหัวไข่, ความยืดหยุ่นสูงและสด, อุปกรณ์ผมหนังน่ารัก Wholesale xwINS  Strings Purse เงินพร้อมหัวไข่, ความยืดหยุ่นสูงและสด, อุปกรณ์ผมหนังน่ารัก Wholesale xwINS  Strings Purse เงินพร้อมหัวไข่, ความยืดหยุ่นสูงและสด, อุปกรณ์ผมหนังน่ารัก Wholesale xwINS  Strings Purse เงินพร้อมหัวไข่, ความยืดหยุ่นสูงและสด, อุปกรณ์ผมหนังน่ารัก Wholesale xwINS  ยางรัดผม ยางรัดผมช้างน้อยน่ารัก สไตล์เกาหลี Wholesale xwINS  ยางรัดผม ยางรัดผมช้างน้อยน่ารัก สไตล์เกาหลี Wholesale xwINS  ยางรัดผม ยางรัดผมช้างน้อยน่ารัก สไตล์เกาหลี Wholesale xwINS  ยางรัดผม ยางรัดผมช้างน้อยน่ารัก สไตล์เกาหลี Wholesale xwINS  ยางรัดผม ยางรัดผมช้างน้อยน่ารัก สไตล์เกาหลี Wholesale xw|20|Standard Express|0|0";


        int j =0;
        int i = 0;
        while (j < 8){
            if(s.charAt(i++) == '|') j++;
        }

        int end = s.indexOf("|", i);

        String tradeName = s.substring(i,end);

        System.out.println(tradeName);

        for (int i1=0;i1<20;i1++){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            long start = System.currentTimeMillis();
            String newS = s.substring(0,i) + s.substring(end);
            System.out.println("substring 耗时：" + (System.currentTimeMillis()-start));
            System.out.println(newS);

            start = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder(s.substring(0,i));
            sb.append(s.substring(end));
            System.out.println("StringBuilder 耗时：" + (System.currentTimeMillis()-start));
            System.out.println(sb.toString());

            start = System.currentTimeMillis();
            String ns = s.replace(tradeName, "");
            System.out.println("replace 耗时：" + (System.currentTimeMillis()-start));
            System.out.println(ns);

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    private static String getSafeLine(String line) {

        if (line.length() < MAX_LENGTH) return line;


        char splitChar = splitPattern.replaceAll("\\\\", "").charAt(0);

        // 定位tradeName的起止index
        int j = 0;
        int i = 0;
        while (j < TRADE_NAME_INDEX && i<line.length()) {
            if (line.charAt(i++) == splitChar) j++;
        }
        if (j != TRADE_NAME_INDEX) {
            return line.substring(0, MAX_LENGTH) + ANDROIDELLIPSIZE;
        }
        int end = line.indexOf(splitChar, i);
        int extra = line.length() - MAX_LENGTH - ANDROIDELLIPSIZE.length();
        if (extra < 0) return line;

        String tradeName = line.substring(i, end);
        if(tradeName.length()>extra){
            tradeName = tradeName.substring(0, tradeName.length() - extra) + ANDROIDELLIPSIZE;
        }


        return line.substring(0,i) + tradeName + line.substring(end);
    }

    public static class Count{
        int i;
    }
}
