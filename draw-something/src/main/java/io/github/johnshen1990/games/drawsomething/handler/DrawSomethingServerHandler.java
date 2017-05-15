package io.github.johnshen1990.games.drawsomething.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

@Component
public class DrawSomethingServerHandler extends ChannelHandlerAdapter {

    private WebSocketServerHandshaker handshaker;
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }else if(msg instanceof TextWebSocketFrame){
            TextWebSocketFrame tmp = (TextWebSocketFrame)msg;
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                channel.writeAndFlush(new TextWebSocketFrame("来自[" + incoming.remoteAddress() + "]的逗比说：" + tmp.text()));
            }
        }

    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception{
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://127.0.0.1:8087/", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if(handshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else{
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame){
        if(frame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }

        if(frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if(!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException();
        }

        String request = ((TextWebSocketFrame)frame).text();
        System.out.println("request = " + request);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(request + ",呵呵哒..."));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("来自[" + incoming.remoteAddress()+ "]的逗比" + " 加入"));
        }
        channels.add(ctx.channel());
        System.out.println("Client:" + incoming.remoteAddress() + "加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("来自[" + incoming.remoteAddress()+ "]的逗比" + " 离开"));
        }
        System.out.println("Client:"+incoming.remoteAddress() +"离开");
        channels.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
