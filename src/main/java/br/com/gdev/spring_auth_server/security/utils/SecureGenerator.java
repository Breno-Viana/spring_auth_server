package br.com.gdev.spring_auth_server.security.utils;

import java.security.SecureRandom;
import java.util.UUID;

public abstract class SecureGenerator {
    private final SecureRandom RANDOM = new SecureRandom();
    private final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    protected String generateClientId() {
        String suffix = "springauthserver";
        Integer prefix = RANDOM.nextInt();
        String center = UUID.randomUUID().toString().replaceAll("[^a-zA-Z0-9]", "");
        return String.format("%s%s.%s", prefix, center, suffix);
    }

    protected String generateClientSecret(){
        StringBuilder sb = new StringBuilder("GDEVAUTH-");
        int SECRET_LENGTH = 30;
        for (int index = 0; index < SECRET_LENGTH; index++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    protected String genTokenCode(){
        StringBuilder sb = new StringBuilder();
        int CODE_LENGTH = 10;
        for (int index = 0; index < CODE_LENGTH; index++){
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }

        return sb.toString();

    }
}