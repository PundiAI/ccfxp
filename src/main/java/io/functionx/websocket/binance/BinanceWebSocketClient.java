package io.functionx.websocket.binance;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.functionx.websocket.AbstractWebSocketClient;
import io.functionx.websocket.CommonWebSocketHandler;
import io.functionx.websocket.IExchangeWebSocketService;
import io.functionx.websocket.binance.vo.SubChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Collections;
import java.util.Random;


@Slf4j
public class BinanceWebSocketClient extends AbstractWebSocketClient {

    public BinanceWebSocketClient(IExchangeWebSocketService service) {
        super.service = service;
    }

    private static final URI uri = URI.create("wss://stream.binance.com:9443/ws");

    private static final String MARKET_SYMBOL_DEPTH_SUB = "%s@depth@1000ms";


    @Override
    public void onConnect() {
        try {
            CommonWebSocketHandler handler = new CommonWebSocketHandler(WebSocketClientHandshakerFactory
                    .newHandshaker(uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders()), service);
            bootstrap(uri, handler);
            if (isAlive()) {
                handler.handshakeFuture().sync();
            }
        } catch (Exception e) {
            if (worker != null) {
                worker.shutdownGracefully();
            }
        }
    }


    @Override
    public void onHeartBeat() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Codes.PING_CODE, System.currentTimeMillis());
        this.sendMessage(jsonObject.toString());
    }


    @Override
    public void onSubChannl(String symbol) {
        SubChannel subChannel = new SubChannel();
        subChannel.setId(new Random().nextInt(Integer.MAX_VALUE));
        subChannel.setParams(Collections.singletonList(String.format(MARKET_SYMBOL_DEPTH_SUB, symbol)));
        this.addSub(JSON.toJSONString(subChannel));
    }


    private static class Codes {

        private static final String PING_CODE = "ping";

        private static final String PONG_CODE = "pong";

    }


}
