package com.ssplugins.advent.day4;

import java.util.Calendar;
import java.util.Date;

public class Sleep {
    
    private int id;
    private Date start;
    private int from, to;
    
    public Sleep(int id, Date start) {
        this.id = id;
        this.start = start;
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        from = cal.get(Calendar.MINUTE);
    }
    
    public int length() {
        return to - from;
    }
    
    public int getId() {
        return id;
    }
    
    public Date getStart() {
        return start;
    }
    
    public int getFrom() {
        return from;
    }
    
    public int getTo() {
        return to;
    }
    
    public void setTo(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.to = cal.get(Calendar.MINUTE);
    }
    
}
