package com.springboot.base.util.annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.springboot.base.util.DoubleIntTranslateUtil;

import java.io.IOException;

/**
 * Created by jay on 16-3-28.
 */
public class MoneySerializer extends JsonSerializer {
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String result;
        if (o == null) {
            result = "0";
        } else {
            final String str = o.toString();
            result = DoubleIntTranslateUtil.addDecimal(str);
        }
        jsonGenerator.writeString(result);
    }
}