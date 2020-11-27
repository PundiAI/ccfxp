package io.functionx.websocket.bithumb;

import com.alibaba.fastjson.JSON;
import io.functionx.websocket.AbstractWebSocketClient;
import io.functionx.websocket.CommonWebSocketHandler;
import io.functionx.websocket.IExchangeWebSocketService;
import io.functionx.websocket.bithumb.vo.BithumbSubChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Collections;


@Slf4j
public class BithumbWebSocketClient extends AbstractWebSocketClient {

    public BithumbWebSocketClient(IExchangeWebSocketService service) {
        super.service = service;
    }

    private static final URI uri = URI.create("wss://global-api.bithumb.pro/message/realtime/ws");

    private static final String MARKET_SYMBOL_ORDERBOOK_SUB = "ORDERBOOK10:%s";


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
        BithumbSubChannel bithumbSubChannel = new BithumbSubChannel();
        bithumbSubChannel.setCmd("ping");
        this.sendMessage(JSON.toJSONString(bithumbSubChannel));
    }


    @Override
    public void onSubChannl(String symbol) {
        BithumbSubChannel bithumbSubChannel = new BithumbSubChannel();
        bithumbSubChannel.setArgs(Collections.singletonList(String.format(MARKET_SYMBOL_ORDERBOOK_SUB, symbol)));
        this.addSub(JSON.toJSONString(bithumbSubChannel));
    }


    private static class Codes {

        private static final int PING_CODE = 0;

        private static final String PONG_CODE = "Pong";

        private static final int NORMAL_CODE = 00007;

    }


}
