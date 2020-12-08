package handler;

public enum Machine {

    SOURCE("CRT"),
    DEFAULT("");

    private String header;

    Machine(String header) {
        this.header = header;
    }

    public static Machine match(String datagram) {
        for (Machine machine: Machine.values()) {
            if (datagram.indexOf(machine.header) == 0) {
                return machine;
            }
        }
        return DEFAULT;
    }
}
