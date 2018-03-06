package com.camilo.jcommander.converters;

import com.camilo.enums.Duration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DurationConverterTest {

    @Test
    public void durationConverterShouldGetDurationFromValidString() {
        DurationConverter converter = new DurationConverter();
        Duration duration = converter.convert("daily");

        assertEquals(Duration.DAILY, duration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void durationConverterShouldFailWithInvalidString() {
        DurationConverter converter = new DurationConverter();
        Duration duration = converter.convert("monthly");
    }
}
