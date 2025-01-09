package com.ohgiraffers.jenkins_test_app.trip.common;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ConvertStringToDate {
    // 날짜 변환 로직 (공통 메서드로 분리)
    public static LocalDate convertStringToDate(String dateString) {
        try {
            if (dateString == null || dateString.isEmpty()) {
                return null;
            }
            String parsedDate = dateString.split("T")[0];
            return LocalDate.parse(parsedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }
}
