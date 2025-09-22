package br.com.gdev.spring_auth_server.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties("storage")
public class StoragePropertiesConfig {
    private String location = "upload-files";
}
