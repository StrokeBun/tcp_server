package handler;

import entity.User;

public class DefaultDatagramHandler extends AbstractDatagramHandler {

    @Override
    protected String handlerDatagramCore(Object datagram) {
        return "";
    }

    @Override
    protected Class getRequestClass() {
        return User.class;
    }
}
