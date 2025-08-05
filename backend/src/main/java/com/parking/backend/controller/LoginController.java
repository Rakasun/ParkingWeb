package com.parking.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.parking.backend.repository.UsuarioRepository;
import com.parking.backend.model.Usuario;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Usuario loginData) {
        Usuario usuario = usuarioRepository.findByUsername(loginData.getUsername());
        Map<String, Object> response = new HashMap<>();
        if (usuario == null || !usuario.getPassword().equals(loginData.getPassword())) {
            response.put("success", false);
            response.put("message", "Credenciales incorrectas");
            return response;
        }
        response.put("success", true);
        response.put("username", usuario.getUsername());
        response.put("nombre", usuario.getNombre());
        return response;
    }
}