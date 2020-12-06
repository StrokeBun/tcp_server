package handler;

import config.GlobalConfig;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;

public abstract class AbstractDatagramHandler implements DatagramHandler {

    /**
     * the message stands for exiting.
     */
    protected String exitMsg;
    /**
     * charset of encoder.
     */
    protected Charset charset;

    public AbstractDatagramHandler() {
        this(GlobalConfig.EXIT_MSG, GlobalConfig.CHARSET);
    }

    public AbstractDatagramHandler(String exitMsg, Charset charset) {
        this.exitMsg = exitMsg;
        this.charset = charset;
    }

    @Override
    public void handleDatagram(ChannelHandlerContext ctx, String datagram) {
        if (datagram.equals(exitMsg)) {
            ctx.disconnect();
        } else {
            String response = handlerDatagramCore(datagram);
            ctx.writeAndFlush(Unpooled.copiedBuffer(response, charset));
        }
    }

    /**
     * 通讯协议处理的流程，交由子类实现
     * @param datagram 数据报字符串
     * @return 结果字符串
     */
    protected abstract String handlerDatagramCore(String datagram);
}
