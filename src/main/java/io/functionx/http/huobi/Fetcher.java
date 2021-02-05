package io.functionx.http.huobi;

import java.io.IOException;


public interface Fetcher<T> {
    T apiResponse() throws IOException;

    default T get() throws IOException {
        final T apiResponse = apiResponse();
        if (apiResponse == null) {
            return null;
        }
        return apiResponse;
    }
}