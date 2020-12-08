package server;

import config.GlobalConfig;
import handler.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private Map<Machine, DatagramHandler> handlers = new HashMap<Machine, DatagramHandler>() {
        {
            put(Machine.SOURCE, new SourceDatagramHandler());
            put(Machine.DEFAULT, new DefaultDatagramHandler());
        }
    };
    private Charset charset = GlobalConfig.CHARSET;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String datagram = in.toString(charset);
        Machine machine = Machine.match(datagram);
        DatagramHandler handler = handlers.get(machine);
        System.out.println(handler.toString());
        if (handler != null) {
            handler.handleDatagram(ctx, datagram);
        } else {
            throw new Exception("TCPServerHandler need a messageHandler");
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
