package io.github.johnshen1990.games.drawsomething.server;

import io.github.johnshen1990.games.drawsomething.handler.DrawSomethingServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DrawSomethingServer implements ServletContextListener {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ChannelFuture future;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(8087)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("http-codec", new HttpServerCodec())
                                .addLast("aggregator", new HttpObjectAggregator(65536))
                                .addLast("http-chunked", new ChunkedWriteHandler())
                                .addLast("handler", new DrawSomethingServerHandler());
                    }
                });
        try {
            System.out.println("Socket is starting ...");
            future = bootstrap.bind().sync();
            System.out.println("Socket is listening ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            System.out.println("Destroying the channel ...");
            if(future != null) {
                future.channel().close().sync();
            }
            System.out.println("Shutting down the worker group ...");
            workerGroup.shutdownGracefully().sync();
            System.out.println("Shutting down the boss group ...");
            bossGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
