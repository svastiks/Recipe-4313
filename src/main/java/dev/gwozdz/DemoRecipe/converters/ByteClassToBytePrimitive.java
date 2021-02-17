package dev.gwozdz.DemoRecipe.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ByteClassToBytePrimitive implements Converter<Byte[], byte[]> {

    @Override
    public byte[] convert(Byte[] classSource) {
        byte[] primitiveOutput = new byte[classSource.length];
        int i=0;
        for(byte sourceByte : classSource){
            primitiveOutput[i++] = sourceByte;
        }
        return primitiveOutput;
    }
}
