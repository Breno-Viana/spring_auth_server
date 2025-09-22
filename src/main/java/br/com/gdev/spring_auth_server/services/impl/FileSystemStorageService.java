package br.com.gdev.spring_auth_server.services.impl;

import br.com.gdev.spring_auth_server.infra.exception.StorageException;
import br.com.gdev.spring_auth_server.infra.exception.StorageLocationException;
import br.com.gdev.spring_auth_server.services.StorageService;
import br.com.gdev.spring_auth_server.config.StoragePropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path root;

    @Autowired
    public FileSystemStorageService(StoragePropertiesConfig properties) {
        if (properties.getLocation().trim().isEmpty()) {
            throw new StorageLocationException("storage location must not be blank");
        }

        this.root = Path.of(properties.getLocation());
    }


    @Override
    public void init() throws IOException {
        Files.createDirectory(root);
    }

    @Override
    public void deleteAll() throws IOException {
        FileSystemUtils.deleteRecursively(root);
    }

    @Override
    public Map<String, Path> store(MultipartFile file, String userIdentifier) {
        if (file.isEmpty()) throw new StorageException("empty files are not permitted");
        try(InputStream inputStream = file.getInputStream()) {
            BufferedImage read = ImageIO.read(inputStream);
            if (read == null)throw new StorageException(("file must be a image"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String fileName = GenerateFileName(file.getOriginalFilename(), userIdentifier);

        Path destinationFile = root.resolve(Paths.get(fileName))
                .normalize();
        if (!destinationFile.getParent().equals(root)) throw new StorageException("error");

        try (InputStream fileInputStream = file.getInputStream()) {
            Files.copy(fileInputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            return Map.of(fileName, destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Path loadPath(String fileName) {
        return null;
    }


    private String GenerateFileName(String fileName, String Prefix){
        return String.format("%s-%s", Prefix, fileName);
    }
}
