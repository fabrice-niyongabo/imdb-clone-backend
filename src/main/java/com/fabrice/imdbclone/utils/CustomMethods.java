package com.fabrice.imdbclone.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.Calendar;
import java.util.Date;

@Controller
public class CustomMethods {
    public String returnLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get and return the username
        return authentication.getName();
    }

    public Long getMillisInOneDay() {
        // Get the current date
        Date currentDate = new Date();

        // Set the time to midnight
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Add one day to the current date
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Calculate the difference in milliseconds
        return calendar.getTimeInMillis() - currentDate.getTime();
    }
}
