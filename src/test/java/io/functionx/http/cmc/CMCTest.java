package io.functionx.http.cmc;

import io.functionx.http.cmc.cryptocurrency.QuotesLatestFetch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

@Ignore
@Slf4j
public class CMCTest {

    @Test
    public void test() throws IOException {
        final CMC cmc = new CMC(" your cmc token ", true);

        final Map<String, QuotesLatestFetch.QuoteLatestData> response = cmc.cryptoCurrency()
                .quotes()
                .latest()
                .symbol("NPXS")
                .convert("USD")
                .get();
        final QuotesLatestFetch.QuoteLatestData quoteLatestData = response.get("NPXS");
        final Map<String, QuotesLatestFetch.Quote> quoteMap = quoteLatestData.getQuote();
        final QuotesLatestFetch.Quote quote = quoteMap.get("USD");
        System.out.println(quote);
    }


}