package polish_community_group_11.polish_community.feed.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private  Path root;

    @PostConstruct
    public void init() throws IOException {
        this.root = Paths.get(uploadDir);
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
    }


    public String storeFile(MultipartFile file) throws IOException {
        // making unique file name
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // full path of file
        Path filePath = this.root.resolve(filename);

        // save file
        Files.copy(file.getInputStream(), filePath);

        // relative path usable in urls
        return "/uploads/" + filename;
    }

    public void deleteFile(String filename) throws IOException {
        Path filePath = this.root.resolve(filename);
        Files.deleteIfExists(filePath);
    }
}