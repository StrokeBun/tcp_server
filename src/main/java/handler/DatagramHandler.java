package handler;

import io.netty.channel.ChannelHandlerContext;

public interface DatagramHandler {
     default void handleDatagram(ChannelHandlerContext ctx, String datagram) throws Exception {

     }
}
