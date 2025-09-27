package br.com.gdev.spring_auth_server.security.utils;

import jakarta.servlet.http.HttpServletRequest;

public interface SecurityInformationGetter {
    default String recoveryBearerToken(HttpServletRequest request){
        String authentication = request.getHeader("Authorization");
        if (authentication == null) return null;

        return authentication.replace("Bearer ","");
    }

}
