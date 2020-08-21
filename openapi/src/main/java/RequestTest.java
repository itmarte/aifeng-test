import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: imart·deng
 * @date: 2020/6/30 11:19
 */
public class RequestTest {
    /**
     * 沙盒环境host
     */
    public static final String SAND_BOX_HOST = "https://api.XXX.com";

    /**
     * 线上环境host
     */
    public static final String ONLINE_HOST = "https://api.XXX.com";


    /**
     * 全局编码格式
     */
    public static final String ENCODE = "UTF-8";

    /**
     * 连接字符串
     */
    public static final String CONCAT_STR = "&";

    /**
     * 授权头
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 签名头
     */
    public static final String SIGNATURE_HEADER = "XXX-Signature";

    /**
     * 商户自己的RSA private key , 长度应该在1024位以上，编码格式为#PKCS8
     */
    public static final String MERCHANT_RSA_PRIVATE_KEY = "<<merchant self RSA private key>>";

    /**
     * xx提供的RSA public key 用于验签
     */
    public static final String LLP_RSA_PUBLIC_KEY = "<<rsa public key>>";

    /**
     * Basic 认证
     */
    public static final String BASIC_TOKEN = "Basic <<token>>";

    /**
     * Bearer 认证
     */
    public static final String BEARER_TOKEN = "Bearer <<accessToken>>";

    /**
     * okhttp 客户端
     */
    public static final OkHttpClient client = new OkHttpClient.Builder().writeTimeout(180, TimeUnit.SECONDS).build();

    /**
     * 签名内容格式: {method}&{URI}&{epoch}&{requestBody}[&{queryString}]
     * 例如：
     * <ul>
     *  <li>
     *      GET请求查询余额：https://api.XXX.com/collections/account/balance?currency=USD
     *      其中：
     *          method: GET(大写)
     *          URI: /collections/account/balance
     *          epoch(秒): epoch = System.currentTimeMillis() / 1000
     *          requestBody:无
     *          queryString: URLEncoder.encode(“currency=USD”, "UTF-8")
     *      签名结果：
     *          String sign = RSA.sign(Mode.SHA256withRSA, "GET&/collections/account/balance&1593497065&&currency%3dUSD")
     *      http签名头:
     *          LLPAY-Signature: t=1593497065,v={sign}
     *      http认证头：
     *      ①.用户basic认证模式:
     *          Authorization: Basic {token}
     *      ②.Auth2.0授权认证模式:
     *          Authorization: Bearer {access token}
     *  </li>
     *  <li>
     *      POST店铺卡申请：https://api.XXX.com/collections/accounts
     *      其中：
     *          method: POST(大写)
     *          URI: /collections/accounts
     *          epoch(秒): epoch = System.currentTimeMillis() / 1000
     *          requestBody:{"currency":"USD","platform":"Amazon"}
     *          queryString: 无
     *      签名结果：
     *          String sign = RSA.sign(Mode.SHA256withRSA, "POST&/collections/account/balance&1593497065&{\"currency\":\"USD\",\"platform\":\"Amazon\"}")
     *      http签名头:
     *          LLPAY-Signature: t=1593497065,v={sign}
     *      http认证头：
     *      ①.用户basic认证模式:
     *          Authorization: Basic {token}
     *      ②.Auth2.0授权认证模式:
     *          Authorization: Bearer {access token}
     *  </li>
     *  <li>
     *      其他格式PUT、DELETE等类推...
     *  </li>
     * </ul>
     *
     * @param args
     */
    public static void main(String[] args) throws UnsupportedEncodingException, CipherException {
        // 1.查询店铺资源
        String uri = "/collection/v1/shops";
        String method = "GET";
        String requestBody = "";
        String queryString = "pageNum=1&pageSize=10";
        String epoch = System.currentTimeMillis() / 1000 + "";

        // 2.拼装签名格式内容：{method}&{URI}&{epoch}&{requestBody}[&{queryString}]
        StringBuilder sBuilder = new StringBuilder("");
        sBuilder
                .append(method)
                .append(CONCAT_STR)
                .append(uri)
                .append(CONCAT_STR)
                .append(epoch)
                .append(CONCAT_STR)
                .append(requestBody)
                .append(CONCAT_STR)
                .append(URLEncoder.encode(queryString, ENCODE));

        // 3.准备签名
        String sign = RSA.sign(RSA.Mode.SHA256withRSA, sBuilder.toString(), MERCHANT_RSA_PRIVATE_KEY);
        String signHeader = String.format("t=%s,v=%s", epoch, sign);

        // 4.构建http请求实例
        Request.Builder builder = new Request.Builder()
                .url(SAND_BOX_HOST + uri + "?" + queryString)
                .get()
                // 添加身份认证头
                .addHeader(AUTHORIZATION, BASIC_TOKEN)
                // 添加签名头
                .addHeader(SIGNATURE_HEADER, signHeader);

        // 5.处理请求
        try (Response response = client.newCall(builder.build()).execute()) {
            // 5.1得到响应码
            int responseCode = response.code();

            // 5.2 得到响应包体
            String responseBody = response.body().string();

            System.out.println(String.format("[%s]请求返回结果[%s]", SAND_BOX_HOST + uri + "?" + queryString, responseBody));
            switch (responseCode) {
                case ResponseHttpCode.OK:

                    // 5.3 得到响应签名头，安全性考虑，建议您每次得到响应时验证一下响应头，响应头签名内容为 LLPAY-Signature: t=epoch,v=RSA.sign(RSA.Mode.SHA256withRSA, epoch+"&"+responseBody, MERCHANT_PRIVATE_KEY)
                    if (verifyResponse(response, responseBody)) {
                        //正常业务逻辑
                    } else {
                        // 验证不通过,do something
                    }
                    break;
                case ResponseHttpCode.BAD_REQUEST:
                    // 业务逻辑失败,do something
                    break;
                case ResponseHttpCode.NOT_FOUND:
                    // 资源不存在,do something
                    break;
                case ResponseHttpCode.UNAUTHORIZED:
                    // 客户端未授权或验签失败,do something
                    break;
                case ResponseHttpCode.METHOD_NOT_ALLOWED:
                    // 方法不支持,do something
                    break;
                case ResponseHttpCode.INTERNAL_SERVER_ERROR:
                    // 内部服务器异常，联系xx以获得支持
                    break;
                default:
                    break;

            }
        } catch (IOException e) {
            // do something
        }
    }

    public String send(HttpMethod method, String url, Map<String, Object> params){
        String signHeader = "";

        // 4.构建http请求实例
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(method.toString(), null)
                // 添加身份认证头
                .addHeader(AUTHORIZATION, BASIC_TOKEN)
                // 添加签名头
                .addHeader(SIGNATURE_HEADER, signHeader);

        return null;
    }

    /**
     * 检验xx返回的真实性
     *
     * @param response
     * @param responseBody
     * @return
     */
    private static boolean verifyResponse(Response response, String responseBody) {
        try {
            String llpSignHeader = response.header(SIGNATURE_HEADER).trim();
            String[] arr = llpSignHeader.split(",");
            String responseEpoch = arr[0].substring("t=".length());
            String responseSign = arr[1].substring("v=".length());
            return RSA.verify(RSA.Mode.SHA256withRSA, responseEpoch + "&" + responseBody, responseSign, LLP_RSA_PUBLIC_KEY);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * xx可能返回的http状态码
     */
    public static class ResponseHttpCode {
        /**
         * 成功
         */
        public static final int OK = 200;

        /**
         * 业务失败
         */
        public static final int BAD_REQUEST = 400;

        /**
         * 客户端未授权或验签失败
         */
        public static final int UNAUTHORIZED = 401;

        /**
         * 资源不存在，在定义REST api时我们把所有的请求当作为资源
         * 例如：https://api.XXX.com/collections/accounts/account/{accountId}接口
         * 当accountId = accountA的时候正常返回200，accountId = accountB的时候可能返回404
         */
        public static final int NOT_FOUND = 404;

        /**
         * 不支持方法类型
         */
        public static final int METHOD_NOT_ALLOWED = 405;

        /**
         * 服务器内部异常
         */
        public static final int INTERNAL_SERVER_ERROR = 500;
    }

    public static void main1(String[] args) {
        String sign = "t=123,v=ddd";
        String[] arr = sign.split(",");
        System.out.println(arr[0].substring("t=".length()));
        System.out.println(arr[1].substring("v=".length()));

    }
}
