package io.functionx.http.huobi.cryptocurrency;


import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import io.functionx.http.huobi.Fetcher;
import io.functionx.http.huobi.HUOBI;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolFetch implements Fetcher<SymbolFetch.SymbolData> {

    private final HUOBI huobi;

    private final Map<String, String> params = new HashMap<>(11);

    public SymbolFetch(HUOBI huobi) {
        this.huobi = huobi;
    }

    @Override
    public SymbolData apiResponse() throws IOException {
        return huobi.get("v1/common/symbols", params, new TypeReference<SymbolData>() {
        });
    }


    @lombok.Data
    public static class SymbolData {
        private String status;
        private List<Data> data;
    }

    @lombok.Data
    public static class Data {

        @JSONField(name = "base-currency")
        private String baseCurrency;
        @JSONField(name = "quote-currency")
        private String quoteCurrency;
        @JSONField(name = "price-precision")
        private Integer pricePrecision;
        @JSONField(name = "amount-precision")
        private Integer amountPrecision;
        @JSONField(name = "symbol-partition")
        private String symbolPartition;
        @JSONField(name = "symbol")
        private String symbol;
        @JSONField(name = "state")
        private String state;
        @JSONField(name = "value-precision")
        private Integer valuePrecision;
        @JSONField(name = "min-order-amt")
        private BigDecimal minOrderAmt;
        @JSONField(name = "max-order-amt")
        private BigDecimal maxOrderAmt;
        @JSONField(name = "min-order-value")
        private BigDecimal minOrderValue;
        @JSONField(name = "limit-order-min-order-amt")
        private BigDecimal limitOrderMinOrderAmt;
        @JSONField(name = "limit-order-max-order-amt")
        private BigDecimal limitOrderMaxOrderAmt;
        @JSONField(name = "sell-market-min-order-amt")
        private BigDecimal sellMarketMinOrderAmt;
        @JSONField(name = "sell-market-max-order-amt")
        private BigDecimal sellMarketMaxOrderAmt;
        @JSONField(name = "buy-market-max-order-value")
        private BigDecimal buyMarketMaxOrderValue;
        @JSONField(name = "leverage-ratio")
        private BigDecimal leverageRatio;
        @JSONField(name = "super-margin-leverage-ratio")
        private BigDecimal superMarginLeverageRatio;
        @JSONField(name = "funding-leverage-ratio")
        private BigDecimal fundingLeverageRatio;
        @JSONField(name = "api-trading")
        private String apiTrading;
    }
}
