package br.com.gdev.spring_auth_server.controllers;

import br.com.gdev.spring_auth_server.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/public")
@RequiredArgsConstructor
public class SignPublicKeyController {
    private final JwtService service;


    @GetMapping(value = "/key", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String get_public_key(Model model) throws IOException {
        String publicKey =  service.publicKey();
        model.addAttribute("publicKey", publicKey);
        return "keyview";
    }

}
