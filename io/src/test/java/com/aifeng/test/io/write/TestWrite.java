package com.aifeng.test.io.write;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestWrite {
    public static final String FILE_PATH = "C:\\Users\\dengxf\\Documents\\测试文档\\sftp\\201812147313047\\参考文件\\instraction\\";
    public static final String CARD_PATH ="C:\\Users\\dengxf\\Documents\\测试文档\\sftp\\201812147313047\\参考文件\\instraction\\cards.info";
    public static final String BATCH_LINE = "{batchId}|USD|{totalAmount}|USD||{total}|0|0|";
    public static final String TITLE_LINE = "orderId|fundsType|sourceCurrency|sourceAmount|targetCurrency|targetAmount|payeeId|payeeBankName|payeeBankBranchName|payeeBankAccount|tradeCode|memo";
    public static final String LINE = "{orderId}|0|USD|{sourceAmount}|USD|{targetAmount}|{payeeId}|XXX|CITIHK KingLoad Branch|{payeeBankAccount}|122030|test";
    private static final BigDecimal THOUSAND = BigDecimal.valueOf(1000L);

    public static final String MERCHANT_ID = "201812147313047";
    public static void main(String[] args) throws IOException {
        String date = getDate();
        String batchPrefix = date + "_" + MERCHANT_ID + "_instraction";
        // 获取需要加款的虚拟卡号
        List<Card> cardNos = getCardNos();
        Random random = new Random();
        int index = 1000000;

        for (int i= 0; i< 20;i++){
            int total = random.nextInt(100000);
            long totalAmount = 0L;

            String batchLine = BATCH_LINE;
            batchLine = batchLine.replace("{batchId}", "batch_" + index);
            batchLine = batchLine.replace("{total}", "" + total);
            List<String> lines = new ArrayList<>(total+2);
            lines.add(TITLE_LINE);
            for(int j=0;j<total;j++){
                Card card = cardNos.get(j%(cardNos.size()));
                String line = LINE;
                line = line.replace("{orderId}", index++ + "");
                int amount = random.nextInt(100000);

                // 统计
                totalAmount+=amount;

                // 生成yuan
                BigDecimal bAmount = new BigDecimal(amount);
                bAmount = bAmount.divide(THOUSAND, 2, 4);
                line = line.replace("{sourceAmount}", bAmount.toString());
                line = line.replace("{targetAmount}", bAmount.toString());


                line = line.replace("{payeeId}", card.sellerId);
                line = line.replace("{payeeBankAccount}", card.cardNo);
                lines.add(line);
            }

            BigDecimal bAmount = new BigDecimal(totalAmount);
            bAmount = bAmount.divide(THOUSAND, 2, 4);
            batchLine = batchLine.replace("{totalAmount}", bAmount.toString());
            lines.add(0, batchLine);

            // 生成批次文件
            String fileName = batchPrefix + index + ".REQ";
            try(RandomAccessFile rmFile = new RandomAccessFile(new File(FILE_PATH + fileName), "rw");) {
                lines.forEach(line -> {
                    try {
                        rmFile.write(line.getBytes());
                        rmFile.write("\n".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        }
    }

    private static String getDate() {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    private static List<Card> getCardNos() throws IOException {
        Stream<String> stream = Files.lines(Paths.get(CARD_PATH) , StandardCharsets.UTF_8);
        return stream.filter(TestWrite::notBlank).map(s -> {
            String[] arr = s.split(",");
            if(arr.length == 3){
                return new Card(arr[0],arr[1],arr[2]);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static boolean notBlank(String s) {
        return !(null == s || "".equals(s));
    }

    public static class Card{
        String cardNo;
        String currency;
        String sellerId;

        public Card(String cardNo, String currency, String sellerId) {
            this.cardNo = cardNo;
            this.currency = currency;
            this.sellerId = sellerId;
        }
    }
}
