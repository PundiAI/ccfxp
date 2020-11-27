package io.functionx.http.cmc;


import io.functionx.http.cmc.cryptocurrency.MapFetcher;
import io.functionx.http.cmc.cryptocurrency.OhlcvHistoricalFetch;
import io.functionx.http.cmc.cryptocurrency.OhlcvLatestFetch;
import io.functionx.http.cmc.cryptocurrency.QuotesLatestFetch;


public class CryptoCurrency {

    private final CMC cmc;

    CryptoCurrency(CMC cmc) {
        this.cmc = cmc;
    }


    public MapFetcher map() {
        return new MapFetcher(cmc);
    }

    public Quotes quotes() {
        return new Quotes(cmc);
    }

    public Ohlcv ohlcv() {
        return new Ohlcv(cmc);
    }

    public static class Quotes {
        private final CMC cmc;

        public Quotes(CMC cmc) {
            this.cmc = cmc;
        }

        public QuotesLatestFetch latest() {
            return new QuotesLatestFetch(this.cmc);
        }
    }

    public static class Ohlcv {
        private final CMC cmc;

        public Ohlcv(CMC cmc) {
            this.cmc = cmc;
        }

        public OhlcvHistoricalFetch historical() {
            return new OhlcvHistoricalFetch(this.cmc);
        }

        public OhlcvLatestFetch latest() {
            return new OhlcvLatestFetch(this.cmc);
        }
    }


}
