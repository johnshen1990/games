package io.github.johnshen1990.games.drawsomething.handler;

import com.alibaba.fastjson.JSON;
import io.github.johnshen1990.games.drawsomething.entity.RoomEntity;
import io.github.johnshen1990.games.drawsomething.entity.UserEntity;
import io.github.johnshen1990.games.drawsomething.message.RoomInfoMessage;
import io.github.johnshen1990.games.drawsomething.shared.AesKeyInstance;
import io.github.johnshen1990.games.drawsomething.utils.AesUtil;
import io.github.johnshen1990.games.drawsomething.utils.Base64Util;
import io.github.johnshen1990.games.drawsomething.utils.XssUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class DrawSomethingServerHandler extends ChannelHandlerAdapter {

    private WebSocketServerHandshaker handshaker;
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static Map<String, Channel> userId2ChannelMap = new HashMap<>();
    public static Map<String, UserEntity> userId2UserEntityMap = new HashMap<>();
    public RoomInfoMessage roomInfoMessage;

    public DrawSomethingServerHandler() {
        RoomEntity roomEntity = new RoomEntity();
        roomInfoMessage = new RoomInfoMessage(roomEntity);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if(msg instanceof TextWebSocketFrame){
            TextWebSocketFrame tmp = (TextWebSocketFrame)msg;
            Channel incoming = ctx.channel();
            if(tmp.text().equals("REQ:KEY")) {
                String key = AesKeyInstance.INSTANCE.get("1");
                incoming.writeAndFlush(new TextWebSocketFrame("RES:KEY:" + key));
                return;
            }

            for (Channel channel : channels) {
                byte[] incomingEncryptedBytes = Base64Util.convertBase64String2Bytes(tmp.text());
                String decryptedString = AesUtil.decryptFromAES(incomingEncryptedBytes, AesKeyInstance.INSTANCE.get("1"));
                decryptedString = XssUtil.xssEncode(decryptedString);
                byte[] outcomingEncryptedBytes = AesUtil.encryptToAES("来自[" + incoming.remoteAddress() + "]的逗比说：" + decryptedString, AesKeyInstance.INSTANCE.get("1"));
                channel.writeAndFlush(new TextWebSocketFrame(Base64Util.convertBytes2Base64String(outcomingEncryptedBytes)));
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

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        // 获取当前的channel
        Channel incoming = ctx.channel();
        // 注册当前的channel到channels
        channels.add(incoming);

        // 随机生成userId
        String userId = UUID.randomUUID().toString();
        // 保存userId与channel的映射
        userId2ChannelMap.put(userId, incoming);

        // 生成userEntity
        String userName = "来自[" + incoming.remoteAddress()+ "]的逗比";
        UserEntity userEntity = new UserEntity(userId, userName);
        // 保存userId与userEntity的映射
        userId2UserEntityMap.put(userId, userEntity);

        // 将用户加入该房间
        roomInfoMessage.getRoomEntity().getUserEntityList().add(userEntity);

        // 通知所有channel最新的roomInfo
        String roomInfoMessageJSONString = JSON.toJSONString(roomInfoMessage);
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(roomInfoMessageJSONString));
        }
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
