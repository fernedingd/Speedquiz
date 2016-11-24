package de.davidferneding.speedquiz;

public class Time {
    private int HOUR;
    private int MINUTE;
    private int SECOND;

    public Time(int hour, int minute, int second) {
        HOUR = hour;
        MINUTE = minute;
        SECOND = second;
    }

    public void setTime(int hour, int minute, int second) {
        HOUR = hour;
        MINUTE = minute;
        SECOND = second;
    }

    public void setHour(int hour) {
        HOUR = hour;
    }

    public void setMinute(int minute) {
        MINUTE = minute;
    }

    public void setSecond(int second) {
        SECOND = second;
    }

    public int getHour() {
        return HOUR;
    }

    public int getMinute() {
        return MINUTE;
    }

    public int getSecond() {
        return SECOND;
    }
}
