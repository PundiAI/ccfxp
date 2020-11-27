package io.functionx.util;

import io.netty.buffer.ByteBuf;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.GZIPInputStream;


public class GZipUtils {


    private static final int BUFFER = 1024;


    public static String decodeByteBuf(ByteBuf buf) {
        byte[] temp = new byte[buf.readableBytes()];
        buf.readBytes(temp);

        return new String(Objects.requireNonNull(decompress(temp)), StandardCharsets.UTF_8);
    }


    public static byte[] decompress(byte[] data) {
        try (
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            decompress(bais, baos);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void decompress(InputStream is, OutputStream os) throws IOException {

        try (GZIPInputStream gis = new GZIPInputStream(is)) {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = gis.read(data, 0, BUFFER)) != -1) {
                os.write(data, 0, count);
            }
        }
    }

}
