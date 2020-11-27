package io.functionx.http.cmc.cryptocurrency;

import com.alibaba.fastjson.TypeReference;
import io.functionx.http.cmc.ApiResponse;
import io.functionx.http.cmc.CMC;
import io.functionx.http.cmc.Fetcher;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OhlcvLatestFetch implements Fetcher<Map<String, OhlcvLatestFetch.OhlcvLatestData>> {

    private final CMC cmc;

    private final Map<String, String> params = new HashMap<>(5);

    public OhlcvLatestFetch(CMC cmc) {
        this.cmc = cmc;
    }


    public OhlcvLatestFetch id(String... id) {
        return addWithStrings("id", id);
    }

    public OhlcvLatestFetch symbol(String... symbol) {
        return addWithStrings("symbol", symbol);
    }

    public OhlcvLatestFetch convert(String... convert) {
        return addWithStrings("convert", convert);
    }

    public OhlcvLatestFetch convertId(String... convertId) {
        return addWithStrings("convert_id", convertId);
    }

    public OhlcvLatestFetch skipInvalid() {
        params.put("skip_invalid", String.valueOf(true));
        return this;
    }


    private OhlcvLatestFetch addWithStrings(String name, String... value) {
        final String values = String.join(",", value);
        if (StringUtils.isEmpty(values)) {
            params.remove(name);
        } else {
            params.put(name, values);
        }
        return this;
    }

    @Override
    public ApiResponse<Map<String, OhlcvLatestData>> apiResponse() throws IOException {
        return cmc.get("v1/cryptocurrency/ohlcv/latest", params, new TypeReference<ApiResponse<Map<String, OhlcvLatestData>>>() {
        });
    }


    @Data
    public static class OhlcvLatestData {
        private int id;
        private String name;
        private String symbol;
        private String lastUpdated;
        private String timeOpen;
        private String timeClose;
        private Map<String, Quote> quote;
    }

    @Data
    public static class Quote {
        private BigDecimal open;
        private BigDecimal high;
        private BigDecimal low;
        private BigDecimal close;
        private BigDecimal volume;
        private String lastUpdated;
    }
}
