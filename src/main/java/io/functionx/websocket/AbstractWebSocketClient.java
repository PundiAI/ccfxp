package io.functionx.websocket;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;


@Slf4j
public abstract class AbstractWebSocketClient {


    protected IExchangeWebSocketService service;


    protected EventLoopGroup worker = null;


    private Channel channel;


    public abstract void onConnect();


    public abstract void onHeartBeat();


    public abstract void onSubChannl(String topic, Object... args);


    protected void bootstrap(URI uri, final SimpleChannelInboundHandler handler) {
        try {

            final String host = uri.getHost();
            final int port = uri.getPort() == -1 ? 443 : uri.getPort();
            final SslContext sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

            worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();

            //构建客户端Bootstrap
            bootstrap.group(worker);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                    pipeline.addLast(new HttpClientCodec(), new HttpObjectAggregator(8192), handler);
                }
            });
            channel = bootstrap.connect(host, port).sync().channel();


        } catch (Exception e) {
            log.error(" webSocketClient start error ", e);

            worker.shutdownGracefully();
        }
    }


    protected boolean isAlive() {
        return channel != null && channel.isActive();
    }


    protected void addSub(String channel) {
        if (!isAlive()) {
            return;
        }
        this.sendMessage(channel);

    }


    protected void sendMessage(String msg) {
        if (!isAlive()) {
            return;
        }
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }


}