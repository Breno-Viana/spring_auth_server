package br.com.gdev.spring_auth_server.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ConfigurationProperties("key")
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class KeyConfigure {
    private Resource public_key;
    private Resource private_key;
    private String public_key_content;
    private String private_key_content;

    @PostConstruct
    private void init() throws IOException {
        this.public_key_content = Files.readString(public_key.getFile().toPath());
        this.private_key_content = Files.readString(private_key.getFile().toPath());
    }


    public RSAPrivateKey getRSAPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String key = private_key_content.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        KeyFactory keyFactor = KeyFactory.getInstance("RSA");
        byte[] decode = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decode);
        return (RSAPrivateKey) keyFactor.generatePrivate(keySpec);
    }

    public RSAPublicKey getRSAPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String key = public_key_content.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decode = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);

        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

}
