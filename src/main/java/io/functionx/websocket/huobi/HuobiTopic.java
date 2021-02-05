package io.functionx.websocket.huobi;


public class HuobiTopic {


    /**
     * This topic sends a new candlestick whenever it is available.
     * <p>
     * arg1: symbol
     * arg2: this.PERIOD
     */
    public static final String MARKET_KLINE = "market.%s.kline.%s";

    /**
     * Candlestick interval
     */
    public static final String[] PERIOD = {"1min", "5min", "15min", "30min", "60min", "1day", "1mon", "1week", "1year"};


    /**
     * This topic sends the latest market by price order book in snapshot mode at 1-second interval.
     * <p>
     * arg1: symbol
     * arg2: this.TYPE
     */
    public static final String MARKET_DEPTH = "market.%s.depth.%s";

    /**
     * Market depth aggregation level, details below
     * step0	No market depth aggregation
     * step1	Aggregation level = precision*10
     * step2	Aggregation level = precision*100
     * step3	Aggregation level = precision*1000
     * step4	Aggregation level = precision*10000
     * step5	Aggregation level = precision*100000
     */
    public static final String[] TYPE = {"step0", "step1", "step2", "step3", "step4", "step5"};


    /**
     * This topic sends the latest completed trade. It updates in tick by tick mode.
     * <p>
     * arg1: symbol
     */
    public static final String MARKET_TRADE_DETAIL = "market.%s.trade.detail";

    /**
     * This topic sends the latest market stats with 24h summary. It updates in snapshot mode, in frequency of no more than 10 times per second.
     * <p>
     * arg1: symbol
     */
    public static final String MARKET_DETAIL = "market.%s.detail";


}
