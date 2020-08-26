package com.ynthm.tools.util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 编码
     */
    private static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();

    /**
     * 默认超时10s
     */
    private static final int DEFAULT_TIMEOUT = 10000;

    /**
     * 连接池
     */
    private PoolingHttpClientConnectionManager cm = null;

    private HttpClientUtil() {

        Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
        try {
            socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslSocketFactory()).register("http", new PlainConnectionSocketFactory()).build();
            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(200);
            cm.setDefaultMaxPerRoute(20);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            LOGGER.error("socket factory registry init error.", e);
        }
    }

    private SSLConnectionSocketFactory sslSocketFactory() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        // new SSLConnectionSocketFactory(SSLContext.getDefault());
        return new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);
    }

    private static class SingletonHolder {
        private static final HttpClientUtil UTIL = new HttpClientUtil();
    }

    public static HttpClientUtil instance() {
        return SingletonHolder.UTIL;
    }

    public CloseableHttpClient getHttpClient(Integer timeout) {

        // 配置请求参数
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout).setSocketTimeout(timeout).build();

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig).setRetryHandler(retryHandler()).build();
        return httpClient;
    }

    /**
     * Http Client Keep Alive Strategy
     *
     * @return
     */
    private ConnectionKeepAliveStrategy keepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    // 如果keep-alive值没有由服务器明确设置，那么保持连接持续5秒。
                    keepAlive = 5000;
                }
                return keepAlive;
            }
        };
    }

    /**
     * Http Client Retry Handler
     *
     * @return
     */
    private HttpRequestRetryHandler retryHandler() {
        // 配置超时回调机制
        return new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return true;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试  Retry if the request is considered idempotent
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 获取SSL上下文对象,用来构建SSL Socket连接
     *
     * @param isDeceive 是否绕过SSL
     * @param creFile   整数文件,isDeceive为true 可传null
     * @param crePwd    整数密码,isDeceive为true 可传null, 空字符为没有密码
     * @return SSL上下文对象
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws CertificateException
     */
    public SSLContext getSSLContext(boolean isDeceive, File creFile, String crePwd) throws KeyManagementException,
            NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException {

        SSLContext sslContext = null;

        if (isDeceive) {
            sslContext = SSLContext.getInstance("SSLv3");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            };
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
        } else {
            if (null != creFile && creFile.length() > 0) {
                if (null != crePwd) {
                    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    keyStore.load(new FileInputStream(creFile), crePwd.toCharArray());
                    sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
                            .build();
                } else {
                    throw new SSLHandshakeException("整数密码为空");
                }
            }
        }

        return sslContext;

    }

    public String post(String url, Map<String, String> map) {
        String result = null;
        CloseableHttpClient httpclient = getHttpClient(DEFAULT_TIMEOUT);
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> formParams = new ArrayList<>();

        for (Entry<String, String> nameValuePair : map.entrySet()) {
            formParams.add(new BasicNameValuePair(nameValuePair.getKey(), nameValuePair.getValue()));
        }

        UrlEncodedFormEntity uefEntity = null;
        CloseableHttpResponse response = null;
        try {
            uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httppost.setEntity(uefEntity);

            LOGGER.info("executing request " + httppost.getURI());

            response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
                LOGGER.info("Response content: " + result);

            }

        } catch (ClientProtocolException e) {
            LOGGER.error("Client Protocol Exception", e);
        } catch (UnsupportedEncodingException e1) {
            LOGGER.error("Unsupported Encoding Exception", e1);
        } catch (IOException e) {
            LOGGER.error("IO Exception", e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }

        return result;
    }

    /**
     * get请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     */
    public String httpGet(String url, Map<String, String> params, int timeOut) {
        return httpGet(url, params, null, DEFAULT_CHARSET, timeOut);
    }

    public String httpGet(String url, Map<String, String> params, Map<String, String> headers, int timeOut) {
        return httpGet(url, params, headers, DEFAULT_CHARSET, timeOut);
    }

    /**
     * 发送http get请求
     */
    public String httpGet(String url, Map<String, String> params, Map<String, String> headers, String encode,
                          int timeOut) {
        if (encode == null) {
            encode = "UTF-8";
        }
        String content = null;

        try {
            // 添加请求参数信息
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != params) {
                uriBuilder.setParameters(covertParams(params));
            }

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 设置header
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }

            content = getResult(httpGet, timeOut, false);

        } catch (URISyntaxException e) {
            LOGGER.error("URISyntaxException", e);
        }

        return content;
    }

    public String httpPostForm(String url, Map<String, String> params, Integer timeOut) {
        return httpPostForm(url, params, null, DEFAULT_CHARSET, timeOut);
    }

    /**
     * 发送 http post 请求，参数以form表单键值对的形式提交。
     */
    public String httpPostForm(String url, Map<String, String> params, Map<String, String> headers, String encode,
                               int timeOut) {
        if (encode == null) {
            encode = DEFAULT_CHARSET;
        }

        HttpPost httpPost = new HttpPost(url);

        // 添加请求头信息
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        if (params != null) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(covertParams(params), encode));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("UnsupportedEncodingException" + e);
            }
        }

        return getResult(httpPost, timeOut, false);
    }

    /**
     * 发送 http post 请求，参数以原生字符串进行提交
     *
     * @param url
     * @param encode
     * @return
     */
    public String httpPostRaw(String url, String stringJson, Map<String, String> headers, String encode, int timeOut) {
        if (encode == null) {
            encode = "utf-8";
        }

        HttpPost httpost = new HttpPost(url);

        // 设置header
        httpost.setHeader("Content-type", "application/json");
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpost.setEntity(stringEntity);

        return getResult(httpost, timeOut, false);
    }

    /**
     * 发送 http put 请求，参数以原生字符串进行提交
     *
     * @param url
     * @param encode
     * @return
     */
    public String httpPutRaw(String url, String stringJson, Map<String, String> headers, String encode, int timeOut) {

        if (encode == null) {
            encode = "utf-8";
        }

        HttpPut httpput = new HttpPut(url);

        // 设置header
        httpput.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpput.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpput.setEntity(stringEntity);

        return getResult(httpput, timeOut, false);
    }

    /**
     * 发送http delete请求
     */
    public String httpDelete(String url, Map<String, String> headers, String encode, int timeout) {

        if (encode == null) {
            encode = DEFAULT_CHARSET;
        }

        HttpDelete httpDelete = new HttpDelete(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpDelete.setHeader(entry.getKey(), entry.getValue());
            }
        }

        return getResult(httpDelete, timeout, false);
    }

    /**
     * 发送 http post 请求，支持文件上传
     */
    public String httpPostMultipart(String url, Map<String, String> params, final Map<String, File> files) {
        List<NameValuePair> paramList = null;
        if (params != null) {
            paramList = covertParams(params);
        }

        HttpEntity httpEntity = makeMultipartEntity(paramList, files);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(httpEntity.getContentType());

        httpPost.setEntity(httpEntity);

        return getResult(httpPost, DEFAULT_TIMEOUT, false);
    }

    public void doDownload(String url, String targetPath, Map<String, String> header) {
        HttpPost post = new HttpPost(url);
        post.addHeader(HTTP.CONTENT_ENCODING, "UTF-8");
        CloseableHttpResponse response = null;
        try {
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            CloseableHttpClient httpclient = getHttpClient(DEFAULT_TIMEOUT);

            response = httpclient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                Path path = Paths.get(targetPath);
                Files.copy(response.getEntity().getContent(), path);
            } else {
                throw new RuntimeException(
                        "System level error, Code=[" + response.getStatusLine().getStatusCode() + "].");
            }
        } catch (IOException e) {
            LOGGER.error("IO Exception---" + e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }

    private String getResult(HttpRequestBase httpRequest, Integer timeout, boolean isStream) {
        return getResult(httpRequest, timeout, isStream, null);
    }

    private String getResult(HttpRequestBase httpRequest, Integer timeout, boolean isStream,
                             HttpContext clientContext) {
        // 响应结果
        StringBuilder sb = new StringBuilder();

        String responseBody = null;
        BufferedReader br = null;

        try (CloseableHttpClient httpClient = getHttpClient(timeout)) {
            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {

                    HttpEntity entity = response.getEntity();
                    // MimeType
                    ContentType contentType = ContentType.getOrDefault(entity);
                    LOGGER.info("MineType：" + contentType.getMimeType());

                    return entity != null ? EntityUtils.toString(entity, DEFAULT_CHARSET) : null;
                } // 如果是重定向
                else if (HttpStatus.SC_MOVED_TEMPORARILY == statusCode) {
                    String locationUrl = response.getLastHeader("Location").getValue();
                    return getResult(new HttpPost(locationUrl), timeout, isStream);
                } else {
                    // 响应信息
                    String reasonPhrase = response.getStatusLine().getReasonPhrase();
                    sb.append("code[").append(statusCode).append("],desc[").append(reasonPhrase).append("]");
                    LOGGER.warn(sb.toString());
                    throw new ClientProtocolException("Unexpected response status: " + statusCode);
                }
            };

            // 发起请求
            if (null != clientContext) {
                responseBody = httpClient.execute(httpRequest, responseHandler, clientContext);
            } else {
                responseBody = httpClient.execute(httpRequest, responseHandler);
            }

        } catch (ConnectionPoolTimeoutException e) {
            LOGGER.error("从连接池获取连接超时!!!", e);
        } catch (SocketTimeoutException e) {
            LOGGER.error("响应超时", e);
        } catch (ConnectTimeoutException e) {
            LOGGER.error("请求超时", e);
        } catch (ClientProtocolException e) {
            LOGGER.error("http协议错误", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持的字符编码", e);
        } catch (UnsupportedOperationException e) {
            LOGGER.error("不支持的请求操作", e);
        } catch (ParseException e) {
            LOGGER.error("解析错误", e);
        } catch (IOException e) {
            LOGGER.error("IO错误", e);

        }

        return responseBody;
    }

    /**
     * Map转换成NameValuePair List集合
     *
     * @param params map
     * @return NameValuePair List集合
     */
    private List<NameValuePair> covertParams(Map<String, String> params) {

        List<NameValuePair> paramList = new LinkedList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return paramList;
    }

    private HttpEntity makeMultipartEntity(List<NameValuePair> params, final Map<String, File> files) {

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // 如果有SocketTimeoutException等情况，可修改这个枚举
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if (params != null) {
            for (NameValuePair p : params) {
                builder.addTextBody(p.getName(), p.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
            }

        }

        if (files != null) {
            Set<Entry<String, File>> entries = files.entrySet();
            for (Entry<String, File> entry : entries) {
                builder.addPart(entry.getKey(), new FileBody(entry.getValue()));
            }
        }

        return builder.setCharset(Charset.forName(DEFAULT_CHARSET)).build();
    }
}
