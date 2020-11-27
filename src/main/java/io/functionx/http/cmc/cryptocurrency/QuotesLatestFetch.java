package io.functionx.http.cmc.cryptocurrency;


import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import io.functionx.http.cmc.ApiResponse;
import io.functionx.http.cmc.CMC;
import io.functionx.http.cmc.Fetcher;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class QuotesLatestFetch implements Fetcher<Map<String, QuotesLatestFetch.QuoteLatestData>> {

    private final CMC cmc;
    private final Map<String, String> params = new HashMap<>(7);

    public QuotesLatestFetch(CMC cmc) {
        this.cmc = cmc;
    }

    public QuotesLatestFetch id(String... id) {
        return addWithStrings("id", id);
    }

    public QuotesLatestFetch slug(String... slug) {
        return addWithStrings("slug", slug);
    }

    public QuotesLatestFetch symbol(String... symbol) {
        return addWithStrings("symbol", symbol);
    }

    public QuotesLatestFetch convert(String... convert) {
        return addWithStrings("convert", convert);
    }

    public QuotesLatestFetch convertId(String... convertId) {
        return addWithStrings("convert_id", convertId);
    }

    public QuotesLatestFetch aux(Aux... aux) {
        final String[] auxs = Stream.of(aux).map(Aux::getValue).toArray(String[]::new);
        return addWithStrings("aux", auxs);
    }

    public QuotesLatestFetch skipInvalid() {
        params.put("skip_invalid", String.valueOf(true));
        return this;
    }

    private QuotesLatestFetch addWithStrings(String name, String... value) {
        final String values = String.join(",", value);
        if (StringUtils.isEmpty(values)) {
            params.remove(name);
        } else {
            params.put(name, values);
        }
        return this;
    }


    @Override
    public ApiResponse<Map<String, QuoteLatestData>> apiResponse() throws IOException {
        return cmc.get("v1/cryptocurrency/quotes/latest", params, new TypeReference<ApiResponse<Map<String, QuoteLatestData>>>() {
        });
    }

    @Data
    public static class QuoteLatestData {
        private int id;
        private String name;
        private String symbol;
        private String slug;
        @JSONField(name = "cmc_rank")
        private int cmcRank;
        @JSONField(name = "num_market_pairs")
        private int numMarketPairs;
        @JSONField(name = "circulating_supply")
        private BigDecimal circulatingSupply;
        @JSONField(name = "total_supply")
        private BigDecimal totalSupply;
        @JSONField(name = "market_cap_by_total_supply")
        private BigDecimal marketCapByTotalSupply;
        @JSONField(name = "max_supply")
        private BigDecimal maxSupply;
        @JSONField(name = "date_added")
        private String dateAdded;
        private String[] tags;
        private Platform platform;
        @JSONField(name = "last_updated")
        private String lastUpdated;

        private Map<String, Quote> quote;
    }

    @Data
    public static class Quote {
        private BigDecimal price;
        @JSONField(name = "volume_24h")
        private BigDecimal volume24h;
        @JSONField(name = "volume_24h_reported")
        private BigDecimal volume24hReported;
        @JSONField(name = "volume_7d")
        private BigDecimal volume7d;
        @JSONField(name = "volume_7d_reported")
        private BigDecimal volume7dReported;
        @JSONField(name = "volume_30d")
        private BigDecimal volume30d;
        @JSONField(name = "volume_30d_reported")
        private BigDecimal volume30dReported;
        @JSONField(name = "market_cap")
        private BigDecimal marketCap;
        @JSONField(name = "percent_change_1h")
        private BigDecimal percentChange1h;
        @JSONField(name = "percent_change_24h")
        private BigDecimal percentChange24h;
        @JSONField(name = "percent_change_7d")
        private BigDecimal percentChange7d;
        @JSONField(name = "last_updated")
        private String lastUpdated;
    }

    public enum Aux {
        NUM_MARKET_PAIRS("num_market_pairs"),
        CMC_RANK("cmc_rank"),
        DATE_ADDED("date_added"),
        TAGS("tags"),
        PLATFORM("platform"),
        MAX_SUPPLY("max_supply"),
        CIRCULATING_SUPPLY("circulating_supply"),
        TOTAL_SUPPLY("total_supply"),
        MARKET_CAP_BY_TOTAL_SUPPLY("market_cap_by_total_supply"),
        VOLUME_24H_REPORTED("volume_24h_reported"),
        VOLUME_7D("volume_7d"),
        VOLUME_7D_REPORTED("volume_7d_reported"),
        VOLUME_30D("volume_30d"),
        VOLUME_30D_REPORTED("volume_30d_reported");

        Aux(String value) {
            this.value = value;
        }

        @Getter
        public final String value;
    }
}
