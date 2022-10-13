package com.scottlogic.app;

import java.io.FileInputStream;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            FileInputStream fin = new FileInputStream("mycalendar.ics");
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(fin);
            System.out.println(calendar.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
