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
        // Boss线程：由这个线程池提供的线程是boss种类的，用于创建、连接、绑定socket， （有点像门卫）然后把这些socket传给worker线程池。
        // 在服务器端每个监听的socket都有一个boss线程来处理。在客户端，只有一个boss线程来处理所有的socket。
        EventLoopGroup bossFroup = new NioEventLoopGroup();
        // Worker线程：Worker线程执行所有的异步I/O，即处理操作
        EventLoopGroup wokerGroup  = new NioEventLoopGroup();
        try{
            // ServerBootstrap 启动NIO服务的辅助启动类,负责初始话netty服务器，并且开始监听端口的socket请求
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossFroup,wokerGroup);
            // 设置非阻塞,用它来建立新accept的连接,用于构造serversocketchannel的工厂类
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new WebSocketChannelInitializer());
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
