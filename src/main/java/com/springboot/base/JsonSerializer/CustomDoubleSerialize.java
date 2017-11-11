package com.springboot.base.JsonSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CustomDoubleSerialize extends JsonSerializer<BigDecimal> {

    private DecimalFormat df = new DecimalFormat("##.00");

    @Override
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException{
        jsonGenerator.writeString(df.format(value));
    }
}
