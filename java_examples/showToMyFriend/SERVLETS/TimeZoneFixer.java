package fixer;

import java.util.TimeZone;

public class TimeZoneFixer {
    private TimeZoneFixer() {}
    
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
    }
}