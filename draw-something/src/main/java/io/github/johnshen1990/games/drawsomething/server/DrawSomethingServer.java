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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DrawSomethingServer implements ServletContextListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            logger.info("Socket is starting ...");
            future = bootstrap.bind().sync();
            logger.info("Socket is listening ...");
        } catch (InterruptedException e) {
            logger.error("contextInitialized error", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            logger.info("Destroying the channel ...");
            if(future != null) {
                future.channel().close().sync();
            }
            logger.info("Shutting down the worker group ...");
            workerGroup.shutdownGracefully().sync();
            logger.info("Shutting down the boss group ...");
            bossGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            logger.error("contextDestroyed error", e);
        }
    }
}
