package com.example.historian_api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class ImageUtils {

    @Value("${images.host}")
    private String host;

    @Value("${api.version}")
    private String apiVersion;

    public byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }


    public byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public String generateImagePath(String imageTitle) {
        return host + "/api/v1/storage/" + imageTitle;
    }

    public String generateImagePath(String path, String imageTitle) {
        return host + apiVersion + "/" + path + "/" + imageTitle;
    }

    public static String generateUniqueImageTitle(String name) {
        return name + "-" + UUID.randomUUID();
    }

}
