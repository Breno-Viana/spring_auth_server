package br.com.gdev.spring_auth_server.security.utils;

public class SecurityUrlSettings {

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/user/login",
            "/user/save",
            "/public/key"
    };

    public static final String[] ENDPOINTS_OF_OWNERS = {
            "/client/save",
            "/client/search",
            "/client/delete"
    };

}
