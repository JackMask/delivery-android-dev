package com.yuxiaolong.yuxiande;

import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpHost;

/**
 * Created by jackmask on 2018/3/12.
 */

public class HttpAsyncClient {/*


    private static int socketTimeout = 1000;// 设置等待数据超时时间5秒钟 根据业务调整

    private static int connectTimeout = 2000;// 连接超时

    private static int poolSize = 3000;// 连接池最大连接数

    private static int maxPerRoute = 1500;// 每个主机的并发最多只有1500

    // http代理相关参数
    private String host = "";
    private int port = 0;
    private String username = "";
    private String password = "";

    // 异步httpclient
    private CloseableHttpAsyncClient asyncHttpClient;

    // 异步加代理的httpclient
    private CloseableHttpAsyncClient proxyAsyncHttpClient;

    public HttpAsyncClient() {
        try {
            this.asyncHttpClient = createAsyncClient(false);
            this.proxyAsyncHttpClient = createAsyncClient(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public CloseableHttpAsyncClient createAsyncClient(boolean proxy)
            throws KeyManagementException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException,
            MalformedChallengeException, IOReactorException {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).build();

        SSLContext sslcontext = SSLContexts.createDefault();

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                username, password);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder
                .<SchemeIOSessionStrategy> create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                .register("https", new SSLIOSessionStrategy(sslcontext))
                .build();

        // 配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .build();
        // 设置连接池大小
        ConnectingIOReactor ioReactor;
        ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(
                ioReactor, null, sessionStrategyRegistry, null);

        if (poolSize > 0) {
            conMgr.setMaxTotal(poolSize);
        }

        if (maxPerRoute > 0) {
            conMgr.setDefaultMaxPerRoute(maxPerRoute);
        } else {
            conMgr.setDefaultMaxPerRoute(10);
        }

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8).build();

        Lookup<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder
                .<AuthSchemeProvider> create()
                .register(AuthSchemes.BASIC, new BasicSchemeFactory())
                .register(AuthSchemes.DIGEST, new DigestSchemeFactory())
                .register(AuthSchemes.NTLM, new NTLMSchemeFactory())
                .register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
                .register(AuthSchemes.KERBEROS, new KerberosSchemeFactory())
                .build();
        conMgr.setDefaultConnectionConfig(connectionConfig);

        if (proxy) {
            return HttpAsyncClients.custom().setConnectionManager(conMgr)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                    .setProxy(new HttpHost(host, port))
                    .setDefaultCookieStore(new BasicCookieStore())
                    .setDefaultRequestConfig(requestConfig).build();
        } else {
            return HttpAsyncClients.custom().setConnectionManager(conMgr)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                    .setDefaultCookieStore(new BasicCookieStore()).build();
        }

    }

    public CloseableHttpAsyncClient getAsyncHttpClient() {
        return asyncHttpClient;
    }

    public CloseableHttpAsyncClient getProxyAsyncHttpClient() {
        return proxyAsyncHttpClient;
    }
*/

}
