package net.toujoustudios.antithreat.log;

public enum LogLevel {

    DEBUG(0, "\u001B[36m"), INFORMATION(1, "\u001B[32m"), WARNING(2, "\u001B[33m"), ERROR(3, "\u001b[31;1m"), FATAL(4, "\u001B[31m");

    private final int level;
    private final String color;

    LogLevel(int level, String color) {
        this.level = level;
        this.color = color;
    }

    public int getLevel() {
        return this.level;
    }

    public String getColor() {
        return this.color;
    }

}