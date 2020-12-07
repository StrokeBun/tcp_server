package handler;

import entity.User;

import java.nio.charset.Charset;

public class SourceDatagramHandler extends AbstractDatagramHandler {

    private static final String NO_RESPONSE = "";

    public SourceDatagramHandler() {
        super();
    }

    public SourceDatagramHandler(String exitMsg, Charset charset) {
        super(exitMsg, charset);
    }

    @Override
    protected String handlerDatagramCore(Object datagram) {
        /**
         *  TODO: 具体协议在此处实现，如果不需要返回数据则返回NO_RESPONSE
         *  return NO_RESPONSE;
         */
        return ((User)datagram).toString();
    }

    @Override
    protected Class getRequestClass() {
        /**
         * TODO: 定义解析后存储的request类型
         */
        return User.class;
    }
}
