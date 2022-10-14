package com.scottlogic.app;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.component.CalendarComponent;

public class App {
    public static void main(String[] args) {
        // replace this string with the URL to the iCalendar stream
        final String STREAM_URL = "CHANGE ME";

        try {
            // get the input stream
            InputStream in = new URL(STREAM_URL).openStream();
            // parse stream to calendar
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(in);
            // print out the calendar
            System.out.println(calendar.toString());

            // get each booking
            for (CalendarComponent booking : calendar.getComponents()) {
                // only consider components marked as 'events'
                if (booking.getName().equals("VEVENT")) {
                    // parse out desk name, user's name, start and end time
                    final String userName = booking.getProperty("ATTENDEE").getParameter("CN").getValue();
                    final String deskName = booking.getProperty("DESCRIPTION").getValue();

                    final DateProperty endDateTimeProperty = booking.getProperty("DTEND");
                    final Date endDateTime = endDateTimeProperty.getDate();
                    final DateProperty startDateTimeProperty = booking.getProperty("DTSTART");
                    final Date startDateTime = startDateTimeProperty.getDate();

                    System.out.println(String.format("%s has booked %s from %s to %s", userName, deskName,
                            startDateTime.toString(), endDateTime.toString()));
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
