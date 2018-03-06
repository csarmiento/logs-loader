package com.camilo.jcommander.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import com.camilo.enums.Duration;

import java.util.Optional;


public class DurationValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        Optional<Duration> durationOpt = Duration.fromString(value);
        if (!durationOpt.isPresent()) {
            throw new ParameterException("Parameter " + name + " should be positive (found " + value + ")");
        }
    }
}