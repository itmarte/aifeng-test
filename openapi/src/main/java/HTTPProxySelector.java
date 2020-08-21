import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;

public class HTTPProxySelector extends ProxySelector {
    private final List<Proxy> httpProxies = new ArrayList<>(1);
    private final List<Proxy> httpsProxies = new ArrayList<>(1);
    private final List<Proxy> directProxies = Collections.singletonList(Proxy.NO_PROXY);
    private final List<Pattern> directPatterns = new ArrayList<>();
    private SoftReference<Map<String, Boolean>> hostCache = new SoftReference<>(new HashMap<>());

    public HTTPProxySelector addHTTPProxy(Proxy proxy) {
        if (proxy != null) httpProxies.add(proxy);
        return this;
    }

    public HTTPProxySelector addHTTPSProxy(Proxy proxy) {
        if (proxy != null) httpsProxies.add(proxy);
        return this;
    }

    public HTTPProxySelector addNoProxyHosts(String... hosts) {
        for (String host : hosts) {
            directPatterns.add(Pattern.compile(host.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*")));
        }
        return this;
    }

    @Override
    public List<Proxy> select(URI uri) {
        Boolean proxy = isLocalCached(uri.getHost());
        if (proxy == null) {
            proxy = isProxy(uri.getHost());
            cacheHost(uri.getHost(), proxy);
        }
        return proxy ? getProxies("https".equalsIgnoreCase(uri.getScheme()) ? httpsProxies : httpProxies) : directProxies;
    }

    private List<Proxy> getProxies(List<Proxy> proxies) {
        return proxies.isEmpty() ? directProxies : proxies;
    }

    private void cacheHost(String host, boolean proxy) {
        Map<String, Boolean> hosts = hostCache.get();
        if (hosts == null) {
            synchronized (this) {
                hosts = hostCache.get();
                if (hosts == null) {
                    hostCache = new SoftReference<>(hosts = new HashMap<>());
                }
            }
        }
        hosts.put(host, proxy);
    }

    private boolean isProxy(String host) {
        for (Pattern pattern : directPatterns) {
            if (pattern.matcher(host).matches()) {
                return false;
            }
        }
        return true;
    }

    private Boolean isLocalCached(String host) {
        Map<String, Boolean> hosts = hostCache.get();
        return hosts == null ? null : hosts.get(host);
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
    }
}
