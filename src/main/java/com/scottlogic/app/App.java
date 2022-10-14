package com.scottlogic.app;

import java.io.InputStream;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.component.CalendarComponent;

public class App {
    public static void main(String[] args) {
        // replace this string with the URL to the iCalendar stream
        final String STREAM_URL = "CHANGEME";

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
                    String deskName = booking.getProperty("DESCRIPTION").getValue();
                    // remove the leading "Spaces: " string
                    deskName = deskName.replace("Spaces: ", "");

                    // we want date times in UTC
                    final ZoneId timeZone = ZoneId.of("UTC");
                    // get the end time property datetime
                    final DateProperty endDateTimeProperty = booking.getProperty("DTEND");
                    final Date endDateTime = endDateTimeProperty.getDate();
                    // add zone information
                    final ZonedDateTime endDateTimeUtc = ZonedDateTime.ofInstant(endDateTime.toInstant(), timeZone);
                    // do the same for start time
                    final DateProperty startDateTimeProperty = booking.getProperty("DTSTART");
                    final Date startDateTime = startDateTimeProperty.getDate();
                    final ZonedDateTime startDateTimeUtc = ZonedDateTime.ofInstant(startDateTime.toInstant(), timeZone);

                    System.out.println(String.format("%s has booked %s from %s to %s", userName, deskName,
                            startDateTimeUtc.toString(), endDateTimeUtc.toString()));
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
