package io.functionx.websocket;


import com.alibaba.fastjson.JSON;
import io.functionx.websocket.binance.BinanceWebSocketClient;
import io.functionx.websocket.binance.vo.BinanceMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class BinanceWebSocketTest implements IExchangeWebSocketService {


    private static BinanceWebSocketClient binanceClient;


    @Override
    public void onStart() {

        binanceClient = new BinanceWebSocketClient(this);

        binanceClient.onConnect();

    }


    @Override
    public void onSubTopics() {

        binanceClient.onSubChannl(BinanceWebSocketClient.MARKET_SYMBOL_DEPTH_SUB, "ethusdt");
    }


    @Override
    public void onReceive(String msg) {

        BinanceMessage binanceMessage = JSON.parseObject(msg, BinanceMessage.class);

        Assert.assertNotNull(binanceMessage);
    }

    @Override
    public void online() {

        onSubTopics();

        // other

    }

    @Override
    public void offline() {

        //  step1

    }


    @Test
    public void test() {

        // start up
        onStart();

    }


}

