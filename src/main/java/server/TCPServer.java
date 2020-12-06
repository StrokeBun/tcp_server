package server;

import config.GlobalConfig;
import handler.Machine;
import handler.DatagramHandler;
import handler.DatagramHandlerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class TCPServer {
    private int port;

    public TCPServer() {
        this(GlobalConfig.PORT);
    }

    public TCPServer(int port) {
        this.port = port;
    }

    public void start(Machine machine) throws Exception {
        final TCPServerHandler handler = new TCPServerHandler();
        DatagramHandler messageHandler = DatagramHandlerFactory.getMessageHandler(machine);
        handler.setMessageHandler(messageHandler);
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
