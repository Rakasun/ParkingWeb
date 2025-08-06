package com.parking.backend.controller;

import com.parking.backend.model.Usuario;
import com.parking.backend.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.parking.backend.model.Vehiculo;
import com.parking.backend.repository.VehiculoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;

    public UsuarioController(UsuarioRepository usuarioRepository, VehiculoRepository vehiculoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.vehiculoRepository = vehiculoRepository;
    }
    
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Integer id) {
    return usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }

    @GetMapping("/{id}/vehiculos")
    public List<Vehiculo> getVehiculosByUsuario(@PathVariable Integer id) {
        return vehiculoRepository.findByUsuarioId(id);
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("El nombre de usuario ya existe");
        }
        Usuario nuevo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Integer id, @Valid @RequestBody Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setUsername(usuarioActualizado.getUsername());
                usuario.setPassword(usuarioActualizado.getPassword());
                usuario.setNombre(usuarioActualizado.getNombre());
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id " + id);
        }
        usuarioRepository.deleteById(id);
    }
}