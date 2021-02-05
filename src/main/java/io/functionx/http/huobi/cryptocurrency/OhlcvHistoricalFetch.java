package io.functionx.http.huobi.cryptocurrency;


import com.alibaba.fastjson.TypeReference;
import io.functionx.http.huobi.Fetcher;
import io.functionx.http.huobi.HUOBI;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OhlcvHistoricalFetch implements Fetcher<OhlcvHistoricalFetch.OhlcvHistoricalData> {

    private final HUOBI huobi;

    private final Map<String, String> params = new HashMap<>(11);

    public OhlcvHistoricalFetch(HUOBI huobi) {
        this.huobi = huobi;
    }

    public OhlcvHistoricalFetch symbol(String... symbol) {
        return addWithStrings("symbol", symbol);
    }

    public OhlcvHistoricalFetch size(int size) {
        return addWithStrings("size", String.valueOf(size));
    }


    public OhlcvHistoricalFetch period(Period period) {
        return addWithStrings("period", period.value);
    }


    public enum Period {
        MIN_ONE("1min"),
        MIN_five("5min"),
        MIN_FIFTEEN("15min"),
        MIN_THIRTY("30min"),
        MIN_SIXTY("60min"),
        HOUR_FOUR("4hour"),
        DAY_ONE("1day"),
        MON_ONE("1mon"),
        WEEKLY_ONE("1week"),
        YEARLY_ONE("1year"),
        ;

        private final String value;

        Period(String value) {
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
    public OhlcvHistoricalData apiResponse() throws IOException {
        return huobi.get("market/history/kline", params, new TypeReference<OhlcvHistoricalData>() {
        });
    }


    @lombok.Data
    public static class OhlcvHistoricalData {
        private String ch;
        private String status;
        private Long ts;
        private List<Data> data;
    }

    @lombok.Data
    public static class Data {

        private Long id;
        private BigDecimal open;
        private BigDecimal close;
        private BigDecimal low;
        private BigDecimal high;
        private BigDecimal amount;
        private BigDecimal vol;
        private Integer count;
    }
}
