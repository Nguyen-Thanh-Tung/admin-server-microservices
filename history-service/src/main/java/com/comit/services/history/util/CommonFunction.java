package com.comit.services.history.util;

import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonFunction {
    public static String convertListToString(String delimiter, Set<String> list) {
        String result = list.stream().map(n -> String.valueOf(n))
                .collect(Collectors.joining(delimiter));
        return result;
    }

    public static <T> byte[] serializeObj(T obj) throws IOException {

        byte[] result = null;
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            result = baos.toByteArray();
        }

        return result;
    }

    public static <T> T deserializeObj(byte[] input) throws IOException {
        if (input == null) {
            return null;
        }

        try (
                ByteArrayInputStream bais = new ByteArrayInputStream(input);
                ObjectInputStream ois = new ObjectInputStream(bais)
        ) {
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("can not deserialize obj!");
        }
    }
}
