package io.functionx.http.cmc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;


@Slf4j
public class CMC {
    private final String apiKey;
    private final boolean sandbox;
    private OkHttpClient okHttpClient;

    private final CryptoCurrency CRYPTO_CURRENCY = new CryptoCurrency(this);

    public CMC(String apiKey, boolean sandbox, OkHttpClient okHttpClient) {
        this.apiKey = apiKey;
        this.sandbox = sandbox;
        this.okHttpClient = okHttpClient.newBuilder()
                .addNetworkInterceptor(chain -> {
                    final Request request = chain.request();
                    final Request newRequest = request.newBuilder().header("X-CMC_PRO_API_KEY", apiKey)
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();
    }

    public CMC(String apiKey, boolean sandbox) {
        this(apiKey, sandbox, defaultClient());
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


    public CMC(String apiKey) {
        this(apiKey, false);
    }

    public CryptoCurrency cryptoCurrency() {
        return CRYPTO_CURRENCY;
    }

    private String endpoint() {
        if (sandbox) {
            return "https://sandbox-api.coinmarketcap.com";
        } else {
            return "https://pro-api.coinmarketcap.com";
        }
    }

    public <T> ApiResponse<T> post(String path, Map<String, String> params, TypeReference<ApiResponse<T>> reference) throws IOException {
        final FormBody.Builder builder = new FormBody.Builder();
        params.forEach(builder::add);
        RequestBody body = builder.build();
        return callToJson(path, body, null, c -> JSON.parseObject(c, reference));
    }

    public <T> ApiResponse<T> get(String path, Map<String, String> params, TypeReference<ApiResponse<T>> reference) throws IOException {
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
