package com.ynthm.tools.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

class HttpClientUtilTest {

    @Test
    void httpGet() {
    }

    @Test
    void httpPostRaw() {


    }

    /**
     * 在下面的例子中，我们对资源 http://httpbin.org/redirect/3 进行 HTTP GET。
     * 此资源重新导向到另一个资源x次。可以使用URIUtils.resolve(httpGet.getURI(), target, redirectLocations)来获取最终的Http位置。
     */
    @Test
    void redirect() {
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        try {
            HttpClientContext context = HttpClientContext.create();
            HttpGet httpGet = new HttpGet("http://httpbin.org/redirect/3");
            System.out.println("Executing request " + httpGet.getRequestLine());
            System.out.println("----------------------------------------");

            httpclient.execute(httpGet, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);
            System.out.println("Final HTTP location: " + location.toASCIIString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            HttpClientUtils.closeQuietly(httpclient);
        }
    }

    @Test
    void headers() throws IOException {
        // create custom http headers for httpclient
        List<Header> defaultHeaders = Arrays.asList(
                new BasicHeader("X-Default-Header", "default header httpclient"));

        // setting custom http headers on the httpclient
        CloseableHttpClient httpclient = HttpClients
                .custom()
                .setDefaultHeaders(defaultHeaders)
                .build();

        try {

            // setting custom http headers on the http request
            HttpUriRequest request = RequestBuilder.get()
                    .setUri("http://httpbin.org/headers")
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    .setHeader(HttpHeaders.FROM, "https://ynthm.com")
                    .setHeader("X-Custom-Header", "custom header http request")
                    .build();

            System.out.println("Executing request " + request.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(request, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            HttpClientUtils.closeQuietly(httpclient);
        }
    }


    @Test
    void upload(){
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            File file = new File(HttpClientUtilTest.class.getResource("/java-duke.png").getFile());
            String message = "This is a multipart post";

            // build multipart upload request
            HttpEntity data = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("upfile", file, ContentType.DEFAULT_BINARY, file.getName())
                    .addTextBody("text", message, ContentType.DEFAULT_BINARY)
                    .build();

            // build http request and assign multipart upload data
            HttpUriRequest request = RequestBuilder
                    .post("http://httpbin.org/post")
                    .setEntity(data)
                    .build();

            System.out.println("Executing request " + request.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(request, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}