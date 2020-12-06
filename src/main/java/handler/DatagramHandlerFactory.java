package handler;

public final class DatagramHandlerFactory {

    public static DatagramHandler getMessageHandler(Machine machine) {
        // TODO: 此处添加枚举到实体类的映射
        switch (machine) {
            case SOURCE:
                return new SourceDatagramHandler();
        }
        return new DefaultDatagramHandler();
    }

    private DatagramHandlerFactory() {
        throw new UnsupportedOperationException("handler.MessageHandlerFactory can not be constructed.");
    }
}
