package handler;

public enum Machine {

    /**
     * Enum implements singleton pattern.
     */
    SOURCE("电源","CRT", new SourceDatagramHandler()),
    DEFAULT("默认无协议","", new DefaultDatagramHandler());

    private String name;
    private String header;
    private DatagramHandler datagramHandler;

    Machine(String name, String header, DatagramHandler datagramHandler) {
        this.name = name;
        this.header = header;
        this.datagramHandler = datagramHandler;
    }

    public static Machine match(String datagram) {
        for (Machine machine: Machine.values()) {
            if (machine != DEFAULT && datagram.indexOf(machine.header) == 0) {
                return machine;
            }
        }
        return DEFAULT;
    }

    public DatagramHandler getDatagramHandler() {
        return datagramHandler;
    }
}
