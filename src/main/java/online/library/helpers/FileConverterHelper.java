package online.library.helpers;

import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileConverterHelper {
    public static String convertImageToBase64(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(imageBytes);
    }


    public static String getPdf(String url) throws IOException {
        byte[] pdfBytes = Files.readAllBytes(Paths.get(url));
        String base64Pdf = Base64Utils.encodeToString(pdfBytes);
        return base64Pdf;
    }
}
