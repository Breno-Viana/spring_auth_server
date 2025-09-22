package br.com.gdev.spring_auth_server.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface StorageService {

    void init() throws IOException;

    void deleteAll() throws IOException;

    Map<String, Path>store(MultipartFile file, String userIdentifier);

     Path loadPath(String fileName);

}
