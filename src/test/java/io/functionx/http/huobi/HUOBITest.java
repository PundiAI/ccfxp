package io.functionx.http.huobi;

import io.functionx.http.huobi.cryptocurrency.OhlcvHistoricalFetch;
import io.functionx.http.huobi.cryptocurrency.SymbolFetch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

@Ignore
@Slf4j
public class HUOBITest {

    @Test
    public void SymbolDataTest() throws IOException {

        final HUOBI huobi = new HUOBI();

        final SymbolFetch.SymbolData response = huobi
                .cryptoCurrency()
                .market()
                .symbol()
                .get();

        Assert.assertEquals("ok", response.getStatus());

    }


    @Test
    public void OhlcvHistoricalTest() throws IOException {

        final HUOBI huobi = new HUOBI();

        final OhlcvHistoricalFetch.OhlcvHistoricalData response = huobi
                .cryptoCurrency()
                .market()
                .historical()
                .period(OhlcvHistoricalFetch.Period.DAY_ONE)
                .symbol("btcusdt")
                .get();

        Assert.assertEquals("ok", response.getStatus());

    }

}