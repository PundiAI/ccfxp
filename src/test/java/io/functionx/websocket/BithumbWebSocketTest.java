package io.functionx.websocket;

import com.alibaba.fastjson.JSON;
import io.functionx.websocket.bithumb.BithumbWebSocketClient;
import io.functionx.websocket.bithumb.vo.BithumbMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;


@Slf4j
public class BithumbWebSocketTest implements IExchangeWebSocketService {


    private static BithumbWebSocketClient bithumbClient;


    @Override
    public void onStart() {

        bithumbClient = new BithumbWebSocketClient(this);

        bithumbClient.onConnect();

    }


    @Override
    public void onSubTopics() {

        bithumbClient.onSubChannl(BithumbWebSocketClient.MARKET_SYMBOL_ORDERBOOK_SUB, "ethusdt");
    }


    @Override
    public void onReceive(String msg) {

        BithumbMessage bithumbMessage = JSON.parseObject(msg, BithumbMessage.class);

        Assert.assertNotNull(bithumbMessage);
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

