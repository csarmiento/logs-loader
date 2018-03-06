package com.camilo.enums;

import java.util.Optional;

public enum Duration {
    DAILY("daily"),
    HOURLY("hourly");

    private final String name;

    Duration(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Optional<Duration> fromString(String text) {
        for (Duration duration : Duration.values()) {
            if (duration.name.equalsIgnoreCase(text)) {
                return Optional.of(duration);
            }
        }
        return Optional.empty();
    }
}
