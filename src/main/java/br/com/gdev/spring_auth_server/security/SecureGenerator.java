package br.com.gdev.spring_auth_server.security;

import java.security.SecureRandom;
import java.util.UUID;

public abstract class SecureGenerator {
    private final SecureRandom RANDOM = new SecureRandom();
    private final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final Integer SECRET_LENGTH = 30;

    protected String generateClientId() {
        String suffix = ".springauthserver";
        Integer prefix = RANDOM.nextInt();
        String center = UUID.randomUUID().toString().replaceAll("[^a-zA-Z0-9]", "");
        return String.format("%s.%s.%s", prefix, center, suffix);
    }

    protected String generateClientSecret(){
        StringBuilder sb = new StringBuilder("GDEVAUTH-");
        for (int index = 0; index <SECRET_LENGTH ; index++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}