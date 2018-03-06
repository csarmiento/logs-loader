package com.camilo.jcommander.converters;

import com.beust.jcommander.IStringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter implements IStringConverter<LocalDateTime> {
    @Override
    public LocalDateTime convert(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
    }
}