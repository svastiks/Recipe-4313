package dev.gwozdz.DemoRecipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BytePrimitiveToByteClass implements Converter<byte[], Byte[]> {

    @Override
    public Byte[] convert(byte[] primitiveSource) {
        Byte[] classOutput = new Byte[primitiveSource.length];
        int i = 0;
        for (byte sourceByte : primitiveSource) {
            classOutput[i++] = sourceByte;
        }
        return classOutput;
    }
}
