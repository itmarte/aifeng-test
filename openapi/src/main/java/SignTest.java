import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/7/1 15:08
 */
public class SignTest {
    public static void main2(String[] args) throws CipherException {
        String sign = "BROSKwtLjXgwr6WOaRZMHAa9ZZ9yuNdP1VVRqc3SUUUT5RiaheVo3FXgICLZp5F0v4v99DoqhvIx2Gdbaq2GuFohVcUHrxMY7R4CU27F/33kiRLikF9G++Q0aj90CR27cKp8piKGdYL90q6RrFdCrrRqhcj9a3Xq+HJkQUNH+y5SgrCX8jLOXeRypdFQHeQEGUuywJgzabQ75QjEHV53UEp5kY95kd6XgubigvGgUPwLUhlFKCfj9oczJoWJ6KBZK51rgrpKpu560gxQhV/YfhlVFDikEUs40JvY/zMJmj4icv8g+7eGq4tWpD3fBAZ6GoHNAP80dqnro8OTye3TFQ==";
        sign = "HTqndI2J+yv2KYIxI9NmJjPtOY2N7O1jtjIYxY36JTCpmdLwCPth4qyrkN8j03EcJVHIm03F/cQWNf96feb1mdB/29UoLmY3jtfQGai4REUmGtaj9khpsLhj2wXy2Zx+YFhStq7YvN5ZAYjNcLiGFpeLqRQCs8g4GK+NX/T/8aQVr0pZgHEVJ0k0CM2u7LBG/gmXlVrWIV7Ont/NLwja4bLKToRf+9fZLv1q8lptF0H7q7M65BcXhrfQ6NP4OTdcwcBiXKL/ThBK0yW3PtHy84fMdDR/XozOeK3xJfIhPgFgFXyyM2eDc4zYSTmnp0iguJfSi4E37avu+PCcvkBCeA==";
        sign = "f47XEqhJNfu2oOnsBosX04xVvvufgXZJN/SHsViIZ/VQXMS8lJVqvzA5SbksoOSDMrah4XR3GUGjxqeeojG9rYLBz/FN1ebv1Lh3HkmTXn/ROHcLL4oqmAIow6bsSOdYTgoACZPjjVbs3WSTxUuoqY0q0nFLhgROWMdejdYtt0gEqF6RCeFov6Jl8SqaYJWQMwsxQ5FGp/gUSRERC/po2Q83X5xJ1gVzl3S1FtlSOAZfCY9SR3s6ZhuH0AtZCGuceoQjKvZX6AFFatw6uR/3nTb7CXNlGg1ygb8kPts2lGqhXJO2hoqr9F6BssNWV2uM1ZqSZq8of7jwdQdDwL98Bg==";
        String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAouaSv1UXKIondxrkmX9I\n" +
                "bGQiY3Xc2wryX+jQRpVUUKit4M9kwP/Za89Vklb7FDEzvmNp3ZXSLzZR1LgXHzsl\n" +
                "5HA2iPN3oqTIApbLQEvhsihAGCyUZmaM/Af1ORhVwndU7zvqGI6i94Zl88ayAUJO\n" +
                "trWiIYalUmqp5UzKys1N8t8CZSmQUh9mq8ib3qRimU/Zn+Xx5MUIXZpiaiccBeTO\n" +
                "4p7y+p6xrJmb+cB8c8oOLshGFbfqfYhP5HwkNSQHOP18iUEo51B3hZ5mZvJMTh1u\n" +
                "pf1uy8Bn/kg5GHpnKw1aj4/eEQ1WyY8DaLpFMfcM6uUiGZE3mkLnsCKQnGYw4r/n\n" +
                "qwIDAQAB";
        String payload = "POST&/payments/v1/payments&1593582257&{\"amount\":\"0.01\",\"amount\":\"0.01\",\"merchant_client_id\":\"zxd0609001\",\"merchant_client_id\":\"zxd0609001\",\"payment_client_id\":\"ZXD2020070113441730934246064\",\"note\":\"performanceTestNote\",\"note\":\"performanceTestNote\",\"currency\":\"USD\",\"currency\":\"USD\"}";

        payload = "POST&/payments/v1/payments&1593582255&{\"amount\":\"0.01\",\"merchant_client_id\":\"zxd0609001\",\"merchant_client_id\":\"zxd0609001\",\"payment_client_id\":\"ZXD2020070113441569528064886\",\"note\":\"performanceTestNote\",\"currency\":\"USD\"}";

        payload = "POST&/payments/v1/payments&1593582255&{\"amount\":\"0.01\",\"merchant_client_id\":\"zxd0609001\",\"merchant_client_id\":\"zxd0609001\",\"payment_client_id\":\"ZXD2020070113441569573759047\",\"note\":\"performanceTestNote\",\"currency\":\"USD\"}";

        System.out.println(RSA.verify(RSA.Mode.SHA256withRSA, payload, sign, pubKey));
    }

    public static final SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        Date date = new Date(1594345677000L);
        Date date2 = new Date(1594381677000L);

        System.out.println(sp.format(date));
        System.out.println(sp.format(date2));

        System.out.println(1594296458-1594260458);
        System.out.println((1594296458-1594260458)/60);

    }
}
