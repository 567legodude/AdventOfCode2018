package com.ssplugins.advent.day4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry {
    
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd kk:mm");
    
    private Date date;
    private int shift = -1;
    private int sleep = -1;
    
    public Entry(String entry) {
        String date = entry.substring(1, entry.lastIndexOf(']'));
        try {
            this.date = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date.", e);
        }
        entry = entry.substring(entry.lastIndexOf(']') + 1).trim();
        if (entry.equals("wakes up")) {
            sleep = 0;
        }
        else if (entry.equals("falls asleep")) {
            sleep = 1;
        }
        else {
            for (String s : entry.split(" ")) {
                if (s.startsWith("#")) {
                    shift = Integer.parseInt(s.substring(1));
                    break;
                }
            }
        }
    }
    
    public boolean isNewShift() {
        return shift != -1;
    }
    
    public boolean isSleepState() {
        return sleep != -1;
    }
    
    public Date getDate() {
        return date;
    }
    
    public int getShift() {
        return shift;
    }
    
    public boolean getSleepState() {
        return sleep == 1;
    }
    
}
