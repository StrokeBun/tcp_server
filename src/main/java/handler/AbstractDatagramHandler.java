package handler;

import config.GlobalConfig;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

public abstract class AbstractDatagramHandler implements DatagramHandler {

    protected static final String NO_RESPONSE = "";
    /**
     * Default config of datagram
     */
    private static final String DEFAULT_EXIT_MESSAGE = "exit";
    private static final String DEFAULT_DATAGRAM_REGEX = ";";
    private static final String DEFAULT_KEY_VALUE_REGEX = "=";


    /**
     * charset of encoder.
     */
    protected Charset charset;

    public AbstractDatagramHandler() {
        this(GlobalConfig.CHARSET);
    }

    public AbstractDatagramHandler(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void handleDatagram(ChannelHandlerContext ctx, String datagram) throws Exception {
        if (datagram.equals(getExitMessage())) {
            ctx.disconnect();
        } else {
            Object request = analyzeDatagram(datagram);
            String response = handlerDatagramCore(request);
            ctx.writeAndFlush(Unpooled.copiedBuffer(response, charset));
        }
    }

    protected Object analyzeDatagram(String datagram) throws Exception {
        Class clazz = getRequestClass();
        Object obj = clazz.newInstance();
        String[] keyValue = datagram.split(getDatagramRegex());
        for (String kv : keyValue) {
            if (!kv.isEmpty()) {
                String[] str = kv.split(getKeyValueRegex());
                if (str.length < 2) {
                    continue;
                }
                String field = str[0];
                String value = str[1];
                field = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
                Method m = clazz.getDeclaredMethod(field, String.class);
                m.invoke(obj, value);
            }
        }
        return obj;
    }

    /**
     * 通讯协议处理的流程，交由子类实现
     * @param datagram 数据报字符串
     * @return 结果字符串
     */
    protected abstract String handlerDatagramCore(Object datagram);

    /**
     * 获得请求解析后存放类的Class对象，交由子类实现
     * @return 请求的Class对象
     */
    protected abstract Class getRequestClass();


    /**
     * 断开连接的数据帧字符串，子类可进行重写
     * @return 断开连接的数据帧字符串
     */
    protected String getExitMessage() {
        return DEFAULT_EXIT_MESSAGE;
    }

    /**
     * 数据帧数据之间的分割符，子类可进行重写
     * @return 数据帧数据之间的分割符
     */
    protected String getDatagramRegex() {
        return DEFAULT_DATAGRAM_REGEX;
    }

    /**
     * 数据帧数据key-value的分割符，子类可进行重写
     * @return 数据帧数据key-value的分割符
     */
    protected String getKeyValueRegex() {
        return DEFAULT_KEY_VALUE_REGEX;
    }

}
