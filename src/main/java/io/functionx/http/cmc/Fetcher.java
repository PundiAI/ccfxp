package io.functionx.http.cmc;

import java.io.IOException;


public interface Fetcher<T> {
    ApiResponse<T> apiResponse() throws IOException;

    default T get() throws IOException {
        final ApiResponse<T> apiResponse = apiResponse();
        if (apiResponse == null) {
            return null;
        }
        return apiResponse.getData();
    }
}