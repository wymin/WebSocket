package demo_1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class WebSocketServer {

    public static void main(String[]args){
        EventLoopGroup bossFroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup  = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossFroup,wokerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketChannelInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8877)).sync();
            channelFuture.channel().closeFuture().sync();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            bossFroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }

}
