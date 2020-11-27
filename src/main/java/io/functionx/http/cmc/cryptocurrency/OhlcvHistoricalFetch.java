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
import java.util.List;
import java.util.Map;

public class OhlcvHistoricalFetch implements Fetcher<Map<String, OhlcvHistoricalFetch.OhlcvHistoricalData>> {

    private final CMC cmc;

    private final Map<String, String> params = new HashMap<>(11);

    public OhlcvHistoricalFetch(CMC cmc) {
        this.cmc = cmc;
    }


    public OhlcvHistoricalFetch id(String... id) {
        return addWithStrings("id", id);
    }

    public OhlcvHistoricalFetch slug(String... slug) {
        return addWithStrings("slug", slug);
    }

    public OhlcvHistoricalFetch symbol(String... symbol) {
        return addWithStrings("symbol", symbol);
    }

    public OhlcvHistoricalFetch timePeriod(TimePeriod timePeriod) {
        return addWithStrings("time_period", timePeriod.value);
    }

    public OhlcvHistoricalFetch timeStart(String timeStart) {
        return addWithStrings("time_start", timeStart);
    }

    public OhlcvHistoricalFetch timeEnd(String timeEnd) {
        return addWithStrings("time_end", timeEnd);
    }


    public OhlcvHistoricalFetch count(int count) {
        return addWithStrings("count", String.valueOf(count));
    }


    public OhlcvHistoricalFetch interval(Interval interval) {
        return addWithStrings("interval", interval.value);
    }

    public OhlcvHistoricalFetch convert(String... convert) {
        return addWithStrings("convert", convert);
    }

    public OhlcvHistoricalFetch convertId(String... convertId) {
        return addWithStrings("convert_id", convertId);
    }

    public OhlcvHistoricalFetch skipInvalid() {
        params.put("skip_invalid", String.valueOf(true));
        return this;
    }


    public enum TimePeriod {
        DAILY("daily"),
        HOURLY("hourly"),
        ;

        private final String value;

        TimePeriod(String value) {
            this.value = value;
        }
    }

    public enum Interval {
        HOURLY("hourly"),
        DAILY("daily"),
        WEEKLY("weekly"),
        MONTHLY("monthly"),
        YEARLY("yearly"),
        HOUR_ONE("1h"),
        HOUR_TWO("2h"),
        HOUR_THREE("3h"),
        HOUR_FOUR("4h"),
        HOUR_SIX("6h"),
        HOUR_TWELVE("12h"),
        DAY_ONE("1d"),
        DAY_TWO("2d"),
        DAY_THREE("3d"),
        DAY_SEVEN("7d"),
        DAY_FOURTEEN("14d"),
        DAY_FIFTEEN("15d"),
        DAY_THIRTY("30d"),
        DAY_SIXTY("60d"),
        DAY_NINETY("90d"),
        YEAR_ONE("365d"),
        ;

        private final String value;

        Interval(String value) {
            this.value = value;
        }
    }


    private OhlcvHistoricalFetch addWithStrings(String name, String... value) {
        final String values = String.join(",", value);
        if (StringUtils.isEmpty(values)) {
            params.remove(name);
        } else {
            params.put(name, values);
        }
        return this;
    }

    @Override
    public ApiResponse<Map<String, OhlcvHistoricalData>> apiResponse() throws IOException {
        return cmc.get("v1/cryptocurrency/ohlcv/historical", params, new TypeReference<ApiResponse<Map<String, OhlcvHistoricalData>>>() {
        });
    }

    public ApiResponse<OhlcvHistoricalData> apiOneResponse() throws IOException {
        return cmc.get("v1/cryptocurrency/ohlcv/historical", params, new TypeReference<ApiResponse<OhlcvHistoricalData>>() {
        });
    }


    @Data
    public static class OhlcvHistoricalData {
        private int id;
        private String name;
        private String symbol;
        private List<QuoteData> quotes;
    }

    @Data
    public static class QuoteData {
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
        private BigDecimal marketCap;
        private String timestamp;
    }
}
