package com.cxl.iot.netty;

import com.cxl.iot.collector.TemperatureCollector;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class NettyFileClientHandler extends SimpleChannelInboundHandler<String> {

    @Autowired
    private TemperatureCollector temperatureCollector;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("temperature\r\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        float templateValue;
        String[] strArray = msg.split("t=");
        if (strArray.length == 2) {
            templateValue = Float.parseFloat(strArray[1].trim());
            templateValue /= 1000;
            TemperatureCollector.recentTemperature = templateValue;
        }
        ctx.writeAndFlush("got file context client closing ... \r\n")
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
