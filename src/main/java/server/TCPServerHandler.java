package server;

import config.GlobalConfig;
import handler.DatagramHandler;
import handler.Machine;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;


@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private Charset charset = GlobalConfig.CHARSET;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String datagram = in.toString(charset);
        Machine machine = Machine.match(datagram);
        DatagramHandler handler = machine.getDatagramHandler();
        if (handler != null) {
            System.out.println(datagram);
            handler.handleDatagram(ctx, datagram);
        } else {
            throw new Exception("TCPServerHandler need a DatagramHandler");
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

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

}
