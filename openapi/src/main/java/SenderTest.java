import java.io.File;
import java.io.IOException;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/7/1 18:41
 */
public class SenderTest {
    /**
     * xx沙盒环境host
     */
    public static final String SAND_BOX_HOST = "https://api.xxx.com";
    public static final String LOCAL_HOST = "http://127.0.0.1:9091";

    /**
     * 商户自己的RSA private key , 长度应该在1024位以上，编码格式为#PKCS8
     */
    public static final String MERCHANT_RSA_PRIVATE_KEY = "<<rsa private key>>";

    /**
     * xx提供的RSA public key 用于验签
     */
    public static final String LLP_RSA_PUBLIC_KEY = "<<RSA public key>>";
    /**
     * Basic 认证（非三方授权）
     */
    public static final String BASIC_TOKEN = "Basic <<token>>";

    /**
     * Bearer 认证(Auth2 三方授权)
     */
    public static final String BEARER_TOKEN = "Bearer <<access token>>";


    /**
     * 签名内容格式: {method}&{URI}&{epoch}&{requestBody}[&{queryString}]
     * 例如：
     * <ul>
     *  <li>
     *      GET请求查询余额：https://api.xxx.com/collections/account/balance?currency=USD
     *      其中：
     *          method: GET(大写)
     *          URI: /collections/account/balance
     *          epoch(秒): epoch = System.currentTimeMillis() / 1000
     *          requestBody:无
     *          queryString: URLEncoder.encode(“currency=USD”, "UTF-8")
     *      签名结果：
     *          String sign = RSA.sign(Mode.SHA256withRSA, "GET&/collections/account/balance&1593497065&&currency%3dUSD")
     *      http签名头:
     *          XXX-Signature: t=1593497065,v={sign}
     *      http认证头：
     *      ①.用户basic认证模式:
     *          Authorization: Basic {token}
     *      ②.Auth2.0授权认证模式:
     *          Authorization: Bearer {access token}
     *  </li>
     *  <li>
     *      POST店铺卡申请：https://api.xxx.com/collections/accounts
     *      其中：
     *          method: POST(大写)
     *          URI: /collections/accounts
     *          epoch(秒): epoch = System.currentTimeMillis() / 1000
     *          requestBody:{"currency":"USD","platform":"Amazon"}
     *          queryString: 无
     *      签名结果：
     *          String sign = RSA.sign(Mode.SHA256withRSA, "POST&/collections/account/balance&1593497065&{\"currency\":\"USD\",\"platform\":\"Amazon\"}")
     *      http签名头:
     *          XXX-Signature: t=1593497065,v={sign}
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
    public static void main(String[] args) throws IOException, CipherException, RequestException {
        AbstractOpenApiSender sender = new AbstractOpenApiSender() {
            @Override
            public String getPrivateKey() {
                return MERCHANT_RSA_PRIVATE_KEY;
            }

            @Override
            public String getLLPPublicKey() {
                return LLP_RSA_PUBLIC_KEY;
            }

            @Override
            public String getToken() {
                return BEARER_TOKEN;
            }
        };


//        // GET request
//        OpenAPIRequest request = new OpenAPIRequest(SAND_BOX_HOST, "/collection/v1/shops", "pageNum=1&pageSize=10", null, HttpMethod.GET);
//        String responseBody = sender.call(request);
//        System.out.println(String.format("请求[%s],返回结果[%s]", request.getRequestUrl(), responseBody));
//
//
//        // POST request
//        request = new OpenAPIRequest(SAND_BOX_HOST, "/supplier/v1/batch-payments", null, "{\"batchId\":\"WITHDRAW_COMBINE\",\"verificationCode\":\"verificationCode\",\"sourceCurrency\":\"USD\",\"targetCurrency\":\"USD\",\"list\":[{\"tradeCode\":\"E_BUSINESS\",\"payeeBankAccountId\":\"payeeBankAccountId\"}]}", HttpMethod.POST);
//        responseBody = sender.call(request);
//        System.out.println(String.format("请求[%s],返回结果[%s]", request.getRequestUrl(), responseBody));
//
//
//        // file request
        OpenAPIRequest request = new OpenAPIRequest(SAND_BOX_HOST, "/common/v1/documents", new File("C:\\Users\\dengxf\\Documents\\测试文档\\sftp\\201812147313047\\参考文件\\20200618_201812147313047_1565746015TradeOrder84.png"), HttpMethod.POST);
//        String responseBody = sender.call(request);
//        System.out.println(String.format("请求[%s],返回结果[%s]", request.getRequestUrl(), responseBody));

        // POST request
        // /common/v1/dictionaries/district/CHN
//        request = new OpenAPIRequest(SAND_BOX_HOST, "/common/v1/dictionaries/district/CHN", null, "", HttpMethod.GET);
//         String responseBody = sender.call(request);
//        System.out.println(String.format("请求[%s],返回结果[%s]", request.getRequestUrl(), responseBody));

        // POST request
        // /common/v1/dictionaries/district/CHN
        request = new OpenAPIRequest(LOCAL_HOST, "/vat-e2e/v1/bills", "profileId=zxdprofilei3d03323002", "", HttpMethod.GET);
        String responseBody = sender.call(request);
        System.out.println(String.format("请求[%s],返回结果[%s]", request.getRequestUrl(), responseBody));


    }
}
