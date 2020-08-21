package com.aifeng.test.io.readline;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestFileMD5 {
    public static final String FILE_PATH = "E:\\备份\\20200605\\desktop\\necessary\\交接\\商户站\\shopee\\readTest\\";

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static char          hexDigits[]   = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f' };

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        long start = System.currentTimeMillis();

        System.out.println("10.csv");
        System.out.println(fileMD5(FILE_PATH + "10.csv"));
        long cur = System.currentTimeMillis();
        System.out.println("耗时：>>>> " + (cur - start));


        start = cur;
        System.out.println("11.csv");
        System.out.println(fileMD5(FILE_PATH + "11.csv"));
        cur = System.currentTimeMillis();
        System.out.println("耗时：>>>> " + (cur - start));


        start = cur;
        System.out.println("12.csv");
        System.out.println(fileMD5(FILE_PATH + "12.csv"));
        cur = System.currentTimeMillis();
        System.out.println("耗时：>>>> " + (cur - start));


        start = cur;
        System.out.println("13.csv");
        System.out.println(fileMD5(FILE_PATH + "13.csv"));
        cur = System.currentTimeMillis();
        System.out.println("耗时：>>>> " + (cur - start));

    }

    public static String fileMD52(String file) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest =MessageDigest.getInstance("MD5");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch =in.getChannel();
        MappedByteBuffer byteBuffer =ch.map(FileChannel.MapMode.READ_ONLY, 0,file.length());
        messageDigest.update(byteBuffer);
        return bufferToHex(messageDigest.digest());
    }

    public static String fileMD5(String inputFile) throws IOException {
        // 缓冲区大小（这个可以抽出一个参数）
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            // 拿到一个MD5转换器（同样，这里可以换成SHA1）
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            // 使用DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer =new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0);
            // 获取最终的MessageDigest
            messageDigest= digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            return bufferToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } finally {
            try {
                digestInputStream.close();
            } catch (Exception e) {
            }
            try {
                fileInputStream.close();
            } catch (Exception e) {
            }
        }
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    //下面这个函数用于将字节数组换成成16进制的字符串
    public static String byteArrayToHex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + "";
            }
        }
        // return hs.toUpperCase();
        return hs;

        // 首先初始化一个字符数组，用来存放每个16进制字符

      /*char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };



      // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

      char[] resultCharArray =new char[byteArray.length * 2];

      // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

      int index = 0;

      for (byte b : byteArray) {

         resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];

         resultCharArray[index++] = hexDigits[b& 0xf];

      }

      // 字符数组组合成字符串返回

      return new String(resultCharArray);*/

    }
}
