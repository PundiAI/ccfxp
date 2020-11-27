package io.functionx.websocket.huobi;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.functionx.websocket.AbstractWebSocketClient;
import io.functionx.websocket.CommonWebSocketHandler;
import io.functionx.websocket.IExchangeWebSocketService;
import io.functionx.websocket.huobi.vo.HuobiSubChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;
import java.util.UUID;


public class HuobiWebSocketClient extends AbstractWebSocketClient {


    public HuobiWebSocketClient(IExchangeWebSocketService service) {
        super.service = service;
    }


    private static final URI uri = URI.create("wss://api-aws.huobi.pro/feed/ws");


    private static final String MARKET_MBP_SUB = "market.%s.mbp.150";


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
        HuobiSubChannel huobiSubChannel = new HuobiSubChannel();
        huobiSubChannel.setId(UUID.randomUUID().toString());
        huobiSubChannel.setSub(String.format(MARKET_MBP_SUB, symbol));
        this.addSub(JSON.toJSONString(huobiSubChannel));
    }


    private static class Codes {

        private static final String PING_CODE = "ping";

        private static final String PONG_CODE = "pong";

    }


}
