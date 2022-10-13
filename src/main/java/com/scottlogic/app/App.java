package com.scottlogic.app;

import java.io.InputStream;
import java.net.URL;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;

public class App {
    public static void main(String[] args) {
        // replace this string with the URL to the iCalendar stream
        final String STREAM_URL = "<replace_me>";

        try {
            // get the input stream
            InputStream in = new URL(STREAM_URL).openStream();
            // parse stream to calendar
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(in);
            // print out the calendar
            System.out.println(calendar.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
