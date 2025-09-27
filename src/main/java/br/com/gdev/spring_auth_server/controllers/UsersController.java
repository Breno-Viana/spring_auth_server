package br.com.gdev.spring_auth_server.controllers;

import br.com.gdev.spring_auth_server.model.dtos.Login;
import br.com.gdev.spring_auth_server.model.dtos.Token;
import br.com.gdev.spring_auth_server.model.dtos.UserDTO;
import br.com.gdev.spring_auth_server.model.dtos.UserGetResponseDTO;
import br.com.gdev.spring_auth_server.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ResponseEntity<?> register_account(@Valid @ModelAttribute UserDTO dto){
        return service.registerAccount(dto);
    }


    @GetMapping("/search")
    public ResponseEntity<?> find_user(@RequestParam String id){
        return service.findUser(id);
    }

    @GetMapping("/ls")
    public ResponseEntity<List<UserGetResponseDTO>> find_all(){
        return service.findAll();
    }

    @PostMapping(value = "/login", consumes = "multipart/form-data")
    public ResponseEntity<Token> login(@ModelAttribute Login login){
        Token authenticate = service.authenticate(login);
        return ResponseEntity.ok(authenticate);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<Void> delete_user(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "email", required = false) String email){
//        System.out.println("EMAIL AND ID: "+email+"  "+id);
        return service.deleteUser(id, email);
    }


    @PutMapping("/.own/turn")
    public ResponseEntity<?> getOwn(HttpServletRequest request){
        return service.getOwn(request);
    }

}
