package com.zup.nosso.banco.digital.util;

import com.zup.nosso.banco.digital.exception.InvalidInputDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static LocalDateTime validateBirthdayAndConvertToLocalDateTime(String date) {
        validateDateFormat(date);
        validateDate(date);
        validateIfUserHasMoreThan18YearsOld(date);
        return convertStringFromDDmmYYYYToLocalDateTime(date);
    }

    private static void validateDateFormat(String date) {
        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new InvalidInputDataException("Date input must be in the DD/MM/YYYY format");
        }
    }

    public static void validateDate(String date) {
        if (!validateIfDateExists(date)) {
            throw new InvalidInputDataException("Invalid date input");
        }
        LocalDateTime birthday = convertStringFromDDmmYYYYToLocalDateTime(date);
        LocalDateTime today = LocalDateTime.now();
        if (today.isBefore(birthday)) {
            throw new InvalidInputDataException("Date of birth must be before today");
        }
    }

    public static LocalDateTime convertStringFromDDmmYYYYToLocalDateTime(String date) {
        int day = Integer.parseInt(date.split("/")[0]);
        int month = Integer.parseInt(date.split("/")[1]);
        int year = Integer.parseInt(date.split("/")[2]);
        return LocalDateTime.of(year, month, day, 0, 0, 0);
    }

    public static boolean validateIfDateExists(String date)  {
        String formatString = "dd/mm/yyyy";
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            format.setLenient(false);
            format.parse(date);
        } catch (ParseException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static void validateIfUserHasMoreThan18YearsOld(String date) {
        LocalDateTime birthday = convertStringFromDDmmYYYYToLocalDateTime(date);
        LocalDateTime today = LocalDateTime.now();
        long years = ChronoUnit.YEARS.between(birthday, today);
        if (years <18) {
            throw new InvalidInputDataException("Account owner must be at least 18 years old");
        }
    }

}
