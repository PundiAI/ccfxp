package io.functionx.http.cmc.cryptocurrency;


import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import io.functionx.http.cmc.ApiResponse;
import io.functionx.http.cmc.CMC;
import io.functionx.http.cmc.Fetcher;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MapFetcher implements Fetcher<List<MapFetcher.MapData>> {
    private final CMC cmc;

    private Map<String, String> params = new HashMap<>();

    public MapFetcher(CMC cmc) {
        this.cmc = cmc;
    }

    public MapFetcher listingActive() {
        params.put("listing_status", "active");
        return this;
    }

    public MapFetcher listingInactive() {
        params.put("listing_status", "inactive");
        return this;
    }

    public MapFetcher listingUnTracked() {
        params.put("listing_status", "untracked");
        return this;
    }

    public MapFetcher start(int start) {
        params.put("start", String.valueOf(start));
        return this;
    }

    public MapFetcher limit(int limit) {
        params.put("limit", String.valueOf(limit));
        return this;
    }

    public MapFetcher symbols(String... symbols) {
        final String symbol = String.join(",", symbols);
        if (StringUtils.isNotEmpty(symbol)) {
            params.put("symbol", symbol);
        } else {
            params.remove("symbol");
        }
        return this;
    }

    public enum Aux {
        PLATFORM("platform"),
        FIRST_HISTORICAL_DATA("first_historical_data"),
        LAST_HISTORICAL_DATA("last_historical_data"),
        IS_ACTIVE("is_active"),
        STATUS("status"),
        ;

        private final String value;

        Aux(String value) {
            this.value = value;
        }
    }

    public MapFetcher aux(Aux... aux) {
        final String auxStr = Arrays.stream(aux).map(s -> s.value).collect(Collectors.joining(","));
        if (StringUtils.isNotBlank(auxStr)) {
            params.put("aux", auxStr);
        }
        return this;
    }

    @Override
    public ApiResponse<List<MapData>> apiResponse() throws IOException {
        return cmc.get("v1/cryptocurrency/map", params, new TypeReference<ApiResponse<List<MapData>>>() {
        });
    }

    @Data
    public static class MapData {
        private int id;
        private String name;
        private String symbol;
        private String slug;
        @JSONField(name = "is_active")
        private boolean active;
        @JSONField(name = "first_historical_data")
        private Date firstHistoricalData;
        @JSONField(name = "last_historical_data")
        private Date lastHistoricalData;
        private Platform platform;
    }
}