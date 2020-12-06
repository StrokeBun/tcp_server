package handler;

public enum Machine {

    SOURCE("电源"),
    DEFAULT("默认，无通信规则");

    private String desc;

    Machine(String desc) {
        this.desc = desc;
    }

}
