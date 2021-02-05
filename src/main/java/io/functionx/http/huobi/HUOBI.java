package io.functionx.http.huobi;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class HUOBI {
    private final boolean isAws;
    private OkHttpClient okHttpClient;

    private final CryptoCurrency CRYPTO_CURRENCY = new CryptoCurrency(this);

    public HUOBI(boolean isAws, OkHttpClient okHttpClient) {
        this.isAws = isAws;
        this.okHttpClient = okHttpClient.newBuilder()
                .addNetworkInterceptor(chain -> {
                    final Request request = chain.request();
                    final Request newRequest = request.newBuilder()
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();
    }

    public HUOBI() {
        this(false, defaultClient());
    }

    public HUOBI(boolean isAws) {
        this(isAws, defaultClient());
    }


    private static OkHttpClient defaultClient() {
        return new OkHttpClient().newBuilder().addNetworkInterceptor(logging()).build();
    }

    private static Interceptor logging() {
        return chain -> {
            final Request request = chain.request();
            log.debug("method: {} url: {}", request.method(), request.url());
            final Response response = chain.proceed(request);
            log.debug("resp: {}, headers: {}", response.code(), response.headers());
            return response;
        };
    }


    public CryptoCurrency cryptoCurrency() {
        return CRYPTO_CURRENCY;
    }

    private String endpoint() {
        if (isAws) {
            return "https://api-aws.huobi.pro";
        } else {
            return "https://api.huobi.pro";
        }
    }

    public <T> T post(String path, Map<String, String> params, TypeReference<T> reference) throws IOException {
        final FormBody.Builder builder = new FormBody.Builder();
        params.forEach(builder::add);
        RequestBody body = builder.build();
        return callToJson(path, body, null, c -> JSON.parseObject(c, reference));
    }

    public <T> T get(String path, Map<String, String> params, TypeReference<T> reference) throws IOException {
        return callToJson(path, null, params, c -> JSON.parseObject(c, reference));
    }


    private <T> T callToJson(String path, RequestBody body, Map<String, String> getParams, Function<String, T> convert) throws IOException {
        final HttpUrl.Builder url = HttpUrl.parse(endpoint()).newBuilder().addPathSegments(path);
        if (getParams != null && !getParams.isEmpty()) {
            getParams.forEach(url::addQueryParameter);
        }
        final Request.Builder builder = new Request.Builder().url(url.build());
        if (body != null) {
            builder.post(body);
        }
        final Response response = okHttpClient.newCall(builder.build()).execute();
        try (ResponseBody responseBody = response.body()) {
            final String responseStr = responseBody.string();
            if (log.isDebugEnabled()) {
                log.debug("response str: {}", responseStr.replaceAll("\\s+", ""));
            }
            return convert.apply(responseStr);
        }
    }


}
