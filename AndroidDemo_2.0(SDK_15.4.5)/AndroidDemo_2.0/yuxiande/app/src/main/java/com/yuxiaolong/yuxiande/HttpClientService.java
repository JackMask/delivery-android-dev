package com.yuxiaolong.yuxiande;

import java.io.IOException;
import java.net.URI;
import java.util.List;


/**
 * Created by jackmask on 2018/3/12.
 */

public class HttpClientService {/*



    private static Logger LOG = LoggerFactory
            .getLogger(HttpClientService.class);

    protected void exeAsyncReq(String baseUrl, boolean isPost,
                               List<BasicNameValuePair> urlParams,
                               List<BasicNameValuePair> postBody, FutureCallback callback)
            throws Exception {

        if (baseUrl == null) {
            LOG.warn("we don't have base url, check config");
            throw new Exception("missing base url");
        }

        HttpRequestBase httpMethod;
        CloseableHttpAsyncClient hc = null;

        try {
            hc = HttpClientFactory.getInstance().getHttpAsyncClientPool()
                    .getAsyncHttpClient();

            hc.start();

            HttpClientContext localContext = HttpClientContext.create();
            BasicCookieStore cookieStore = new BasicCookieStore();

            if (isPost) {
                httpMethod = new HttpPost(baseUrl);

                if (null != postBody) {
                    LOG.debug("exeAsyncReq post postBody={}", postBody);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                            postBody, "UTF-8");
                    ((HttpPost) httpMethod).setEntity(entity);
                }

                if (null != urlParams) {

                    String getUrl = EntityUtils
                            .toString(new UrlEncodedFormEntity(urlParams));

                    httpMethod.setURI(new URI(httpMethod.getURI().toString()
                            + "?" + getUrl));
                }

            } else {

                httpMethod = new HttpGet(baseUrl);

                if (null != urlParams) {

                    String getUrl = EntityUtils
                            .toString(new UrlEncodedFormEntity(urlParams));

                    httpMethod.setURI(new URI(httpMethod.getURI().toString()
                            + "?" + getUrl));
                }
            }

            System.out.println("exeAsyncReq getparams:" + httpMethod.getURI());

            localContext.setAttribute(HttpClientContext.COOKIE_STORE,
                    cookieStore);

            hc.execute(httpMethod, localContext, callback);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected String getHttpContent(HttpResponse response) {

        HttpEntity entity = response.getEntity();
        String body = null;

        if (entity == null) {
            return null;
        }

        try {

            body = EntityUtils.toString(entity, "utf-8");

        } catch (ParseException e) {

            LOG.warn("the response's content inputstream is corrupt", e);
        } catch (IOException e) {

            LOG.warn("the response's content inputstream is corrupt", e);
        }
        return body;
    }*/
}
