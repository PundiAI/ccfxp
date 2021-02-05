package io.functionx.http.huobi;


import io.functionx.http.huobi.cryptocurrency.OhlcvHistoricalFetch;
import io.functionx.http.huobi.cryptocurrency.SymbolFetch;

public class CryptoCurrency {

    private final HUOBI huobi;

    CryptoCurrency(HUOBI huobi) {
        this.huobi = huobi;
    }


    public Market market() {
        return new Market(huobi);
    }

    public static class Market {
        private final HUOBI huobi;

        public Market(HUOBI huobi) {
            this.huobi = huobi;
        }

        public SymbolFetch symbol() {
            return new SymbolFetch(this.huobi);
        }

        public OhlcvHistoricalFetch historical() {
            return new OhlcvHistoricalFetch(this.huobi);
        }

    }


}
