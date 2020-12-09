package handler;

public enum Machine {

    /**
     * Enum implements singleton pattern.
     */
    SOURCE("CRT", new SourceDatagramHandler()),
    DEFAULT("", new DefaultDatagramHandler());

    private String header;
    private DatagramHandler datagramHandler;

    Machine(String header, DatagramHandler datagramHandler) {
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
