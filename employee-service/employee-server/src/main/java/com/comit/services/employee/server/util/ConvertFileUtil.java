package com.comit.services.employee.server.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ConvertFileUtil {
    public static String UNKNOWN_TYPE = "unknown";

    public static String getFileExtension(String base64String) {
        String data = base64String.substring(0, 5);
        return switch (data.toUpperCase()) {
            case "IVBOR" -> "png";
            case "/9J/4", "/9J/2" -> "jpg";
            default -> UNKNOWN_TYPE;
        };
    }

    public static String convertFileToBase64(String filePath) throws IOException {
        byte[] tmgFileContent = FileUtils.readFileToByteArray(new File(filePath));
        return Base64.getEncoder().encodeToString(tmgFileContent);
    }

    public static String convertFileToBase64(MultipartFile file) {
        try {
            byte[] image = Base64.getEncoder().encode(file.getBytes());
            return new String(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
