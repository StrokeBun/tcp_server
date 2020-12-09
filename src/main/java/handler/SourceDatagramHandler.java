package handler;

import entity.User;

import java.nio.charset.Charset;

public class SourceDatagramHandler extends AbstractDatagramHandler {

    public SourceDatagramHandler() {
        super();
    }

    public SourceDatagramHandler(Charset charset) {
        super(charset);
    }

    @Override
    protected String handlerDatagramCore(Object datagram) {
        /**
         *  TODO: 具体协议在此处实现，如果不需要返回数据则返回NO_RESPONSE
         *  return NO_RESPONSE;
         */
        User user = (User) datagram;
        return user.toString();
    }

    @Override
    protected Class getRequestClass() {
        return User.class;
    }

    @Override
    protected String getKeyValueRegex() {
        return ":";
    }
}
