package io.github.johnshen1990.games.drawsomething.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class DrawSomethingClientHandler extends ChannelHandlerAdapter {
    private boolean started = false;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(!started) {
            String resMsg = "你好!";
            byte[] bytes = resMsg.getBytes("UTF-8");
            ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
            ctx.writeAndFlush(byteBuf);
            started = true;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String reqMsg = new String(req, "UTF-8");
        System.out.println("---->Server: " + reqMsg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String resMsg = "你好呀!";
        byte[] bytes = resMsg.getBytes("UTF-8");
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        ctx.writeAndFlush(byteBuf);
        System.out.println("---->Client: " + resMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
