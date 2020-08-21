//import okhttp3.*;
//
//import javax.net.ssl.*;
//import java.net.*;
//import java.security.cert.X509Certificate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Logger;
//
//class Worker implements Runnable {
//    private static Logger logger;
//    private static final OkHttpClient client;
//    private static final OkHttpClient unsafeClient;
//    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
//    private static final String SIGNATURE_HEADER = "LLPAY-Signature";
//    private static final String EVENT_ID_HEADER = "Event-Id";
//    private static final IDObfuscation ID_OBFUSCATION = new IDObfuscation(0xB69E74);
//    private static final ZoneId ZONE_ID = ZoneId.systemDefault();
//
//    static {
//        ProxySelector proxySelector = generateProxySelector();
//        client = generateHttpClientBuilder(new OkHttpClient.Builder(), proxySelector).build();
//        unsafeClient = ignoreCertificate(generateHttpClientBuilder(new OkHttpClient.Builder(), proxySelector)).build();
//    }
//
//    private final Scheduler scheduler;
//    private final LetterChannel channel;
//    private int lastStatus;
//    private int time;
//
//    Worker(Scheduler scheduler, LetterChannel channel) {
//        this(scheduler, channel, 0);
//    }
//
//    Worker(Scheduler scheduler, LetterChannel channel, int time) {
//        this.scheduler = scheduler;
//        this.channel = channel;
//        this.time = time;
//    }
//
//    private static ProxySelector generateProxySelector() {
//        String httpProxy = System.getenv("http_proxy");
//        String httpsProxy = System.getenv("https_proxy");
//        if (httpProxy == null && httpsProxy == null) return null;
//        String noProxyHosts = System.getenv("no_proxy");
//        HTTPProxySelector selector = new HTTPProxySelector().addHTTPProxy(generateProxy(httpProxy)).addHTTPSProxy(generateProxy(httpsProxy));
//        return noProxyHosts == null ? selector : selector.addNoProxyHosts(noProxyHosts.split(","));
//    }
//
//    private static Proxy generateProxy(String s) {
//        if (s == null) return null;
//        int i = s.lastIndexOf(":");
//        int j = s.indexOf("://");
//        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(s.substring(j + 3, i), Integer.parseInt(s.substring(i + 1))));
//    }
//
//    private static OkHttpClient.Builder generateHttpClientBuilder(OkHttpClient.Builder builder, ProxySelector proxySelector) {
//        return builder.connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(45, TimeUnit.SECONDS)
//                .hostnameVerifier((hostname, session) -> true)
//                .proxySelector(proxySelector == null ? ProxySelector.getDefault() : proxySelector);
//    }
//
//    private static OkHttpClient.Builder ignoreCertificate(OkHttpClient.Builder builder) {
//        try {
//            X509TrustManager trustManager = new X509TrustManager() {
//                final X509Certificate[] acceptedIssuers = new X509Certificate[]{};
//
//                @Override
//                public void checkClientTrusted(X509Certificate[] chain, String authType) {
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] chain, String authType) {
//                }
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return acceptedIssuers;
//                }
//            };
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, new TrustManager[]{trustManager}, null);
//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            builder.sslSocketFactory(sslSocketFactory, trustManager);
//            return builder.hostnameVerifier((hostname, session) -> true);
//        } catch (Exception e) {
////            logger.warn("http client setup ignore ssl certificate exception", e);
//            return builder;
//        }
//    }
//
//    @Override
//    public void run() {
////        logger.debug("{} POST {}", channel.getId(), channel.getDest());
//        LocalDateTime now = LocalDateTime.now();
//        long time = System.currentTimeMillis();
//        try {
//            RequestBody body = RequestBody.create(MEDIA_TYPE, channel.getLetter().getContent());
//            Request.Builder builder = new Request.Builder().url(channel.getDest()).post(body);
//            builder.addHeader(EVENT_ID_HEADER, ID_OBFUSCATION.obfuscate(channel.getLetter().getId()));
//            builder.addHeader(SIGNATURE_HEADER, generateSign(now, channel.getLetter().getContent(), channel.getLLPPrivateKey()));
//            Request request = builder.build();
//            time = System.currentTimeMillis();
//            try (Response response = getClient(channel.getDest()).newCall(request).execute()) {
//                scheduler.insertLog(channel, channel.getDest(), now, response.code(), (int) (System.currentTimeMillis() - time));
//                if (response.code() == 200) {
////                    logger.debug("{} POST {} {}", channel.getId(), channel.getDest(), response.code());
//                    scheduler.updateLetter(channel, SUCCESS);
//                } else {
////                    logger.warn("{} POST {} {}", channel.getId(), channel.getDest(), response.code());
//                    scheduleIfNecessary();
//                }
//            }
//        } catch (Exception e) {
//            scheduler.insertLog(channel, channel.getDest(), now, lastStatus = getStatus(e), (int) (System.currentTimeMillis() - time));
////            logger.error("{} POST {} exception", channel.getId(), channel.getDest(), e);
//            scheduleIfNecessary();
//        }
//    }
//
//    private String generateSign(LocalDateTime time, String content, String privateKey) throws CipherException {
//        long epochSecond = time.atZone(ZONE_ID).toEpochSecond();
//        String sign = RSA.sign(SHA256withRSA, epochSecond + "&" + content, privateKey);
//        return "t=" + epochSecond + ",v=" + sign;
//    }
//
//    private void scheduleIfNecessary() {
//        if (time++ >= scheduler.getRetryTime()) {
//            logger.warn("{} POST {} fail {} times, deliver over", channel.getId(), channel.getDest(), time);
//            scheduler.updateLetter(channel, FAIL);
//        } else {
//            logger.warn("{} POST {} fail {} times, reschedule {}s later", channel.getId(), channel.getDest(), time, scheduler.getItvlTime());
//            scheduler.schedule(this, channel);
//        }
//    }
//
//    private OkHttpClient getClient(String dest) {
//        if (lastStatus == 525) {
//            logger.warn("last call found the ssl certification of [{}] was invalid, ignore this time", dest);
//            return unsafeClient;
//        } else {
//            return client;
//        }
//    }
//
//    // https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
//    private int getStatus(Exception e) {
//        if (e instanceof SSLHandshakeException) {
////            logger.warn("{} POST {} met a SSLHandshakeException, using keytool to install certification first", channel.getId(), channel.getDest());
//            return 525;// SSL Handshake Failed
//        } else if (e instanceof ConnectException) {
//            return 522;// Connection Timed Out
//        } else if (e instanceof SocketTimeoutException) {
//            return 598;// Network read timeout error
//        } else if (e instanceof CipherException) {
//            return 1;// generate sign error
//        } else {
//            return 0;
//        }
//    }
//}
