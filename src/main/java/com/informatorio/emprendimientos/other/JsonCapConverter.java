package com.informatorio.emprendimientos.other;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.util.StringUtils;

public class JsonCapConverter extends StdConverter<String, String> {
    @Override
    public String convert(String value) {
        return StringUtils.capitalize(value);
    }
}