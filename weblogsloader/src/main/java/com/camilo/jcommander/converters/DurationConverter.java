package com.camilo.jcommander.converters;

import com.beust.jcommander.IStringConverter;
import com.camilo.enums.Duration;

import static java.lang.String.format;

public class DurationConverter implements IStringConverter<Duration> {

    @Override
    public Duration convert(String value) {
        return Duration.fromString(value).orElseThrow(() -> new IllegalArgumentException(format("String '%s' is not a valid duration", value)));
    }
}