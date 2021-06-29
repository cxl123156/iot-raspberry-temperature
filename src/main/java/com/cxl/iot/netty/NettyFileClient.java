package com.cxl.iot.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NettyFileClient {

    @Value("${raspberryPi.ip}")
    private String host;

    @Value("${raspberryPi.port}")
    private String port;

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final NettyFileClientHandler CLIENT_HANDLER = new NettyFileClientHandler();
    private static final long FIVE_MINUTES = 5 * 60 * 1000;

    @Scheduled(fixedDelay = FIVE_MINUTES)
    public void collectTemperature() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();

                    // Add the text line codec combination first,
                    pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    pipeline.addLast(DECODER);
                    pipeline.addLast(ENCODER);

                    // and then business logic.
                    pipeline.addLast(CLIENT_HANDLER);
                }
            });
            Channel channel = bootstrap.connect(host, Integer.parseInt(port)).sync().channel();
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
