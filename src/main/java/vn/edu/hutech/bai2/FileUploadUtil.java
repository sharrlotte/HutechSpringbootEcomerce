package vn.edu.hutech.bai2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    public static String saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {

        var uploadPath = Path
                .of("D:\\Project\\Hutech\\hutech-java-ecommerce\\bai2\\src\\main\\resources\\static\\images");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName + ".png");
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString()
                    .replace("D:\\Project\\Hutech\\hutech-java-ecommerce\\bai2\\src\\main\\resources\\", "");
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

    }
}
