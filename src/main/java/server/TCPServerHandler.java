package server;

import config.GlobalConfig;
import handler.DatagramHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;


@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private DatagramHandler messageHandler;
    private Charset charset = GlobalConfig.CHARSET;

    public void setMessageHandler(DatagramHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String datagram = in.toString(charset);
        System.out.println(datagram);
        if (messageHandler != null) {
            messageHandler.handleMsg(ctx, datagram);
        } else {
            throw new Exception("EchoServerHandler need a messageHandler");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
