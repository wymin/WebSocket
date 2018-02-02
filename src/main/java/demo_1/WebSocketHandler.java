package demo_1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Date;
//处理文本协议数据，处理TextWebSocketFrame类型的数据，websocket专门处理文本的frame就是TextWebSocketFrame
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

    //读取客户端请求，并响应
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("客户端消息："+msg.text());
        //ctx.channel().writeAndFlush("success");
        ctx.channel().writeAndFlush(new TextWebSocketFrame("success time :"+ new Date()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //打印出channel唯一值，asLongText方法是channel的id的全名
        System.out.println("handlerAdded："+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved：" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        ctx.close();
    }
}
