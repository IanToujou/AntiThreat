package net.toujoustudios.antithreat.link;

public enum LinkStatus {


    NOT_VERIFIED("NOT_VERIFIED"),
    VERIFIED("VERIFIED"),
    ACTION("ACTION"),
    DOWN("DOWN"),
    REJECTED("REJECTED");

    private final String name;

    LinkStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
