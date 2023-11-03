package projetJEE.ProjetEE.Controllers;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileToByteArrayConverter {
    public byte[] convert(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Gérer l'erreur comme vous le souhaitez
        }
    }
}