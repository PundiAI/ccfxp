package io.functionx.websocket;


import io.functionx.util.GZipUtils;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import org.apache.commons.lang3.StringUtils;


public class CommonWebSocketHandler extends SimpleChannelInboundHandler<Object> {


    private ChannelPromise handshakeFuture;

    private final WebSocketClientHandshaker handshaker;

    private final IExchangeWebSocketService exchangeWebSocketService;


    public CommonWebSocketHandler(WebSocketClientHandshaker handshaker, IExchangeWebSocketService exchangeWebSocketService) {
        this.handshaker = handshaker;
        this.exchangeWebSocketService = exchangeWebSocketService;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(channel, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            return;
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof BinaryWebSocketFrame) {

            BinaryWebSocketFrame binaryFrame = (BinaryWebSocketFrame) frame;
            String receiveMsg = GZipUtils.decodeByteBuf(binaryFrame.content());

            if (StringUtils.isNotEmpty(receiveMsg)) {
                exchangeWebSocketService.onReceive(receiveMsg);
            }
        } else if (frame instanceof TextWebSocketFrame) {

            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) frame;
            String receiveMsg = textWebSocketFrame.text();

            if (StringUtils.isNotEmpty(receiveMsg)) {
                exchangeWebSocketService.onReceive(receiveMsg);
            }
        } else if (frame instanceof CloseWebSocketFrame) {
            channel.close();

        }

    }


    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
        exchangeWebSocketService.online();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        exchangeWebSocketService.offline();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }


}
