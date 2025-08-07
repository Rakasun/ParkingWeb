package com.parking.backend.controller;

import com.parking.backend.model.Vehiculo;
import com.parking.backend.dto.VehiculoDTO;
import com.parking.backend.repository.VehiculoRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.parking.backend.repository.UsuarioRepository;
import com.parking.backend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import com.parking.backend.repository.EstanciaRepository;
import com.parking.backend.dto.EstanciaDTO;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoRepository vehiculoRepository;

    public VehiculoController(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstanciaRepository estanciaRepository;

    @GetMapping
    public List<VehiculoDTO> getAllVehiculos() {
        return vehiculoRepository.findAll().stream()
        .map(v -> new VehiculoDTO(v.getId(), v.getMatricula(), v.getUsuario().getId()))
        .toList();
    }

    @GetMapping("/{id}")
    public VehiculoDTO getVehiculoById(@PathVariable Integer id) {
        Vehiculo v = vehiculoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con id " + id));
        return new VehiculoDTO(v.getId(), v.getMatricula(), v.getUsuario().getId());
    }

    @GetMapping("/{id}/estancias")
    public List<EstanciaDTO> getEstanciasByVehiculo(@PathVariable Integer id) {
        return estanciaRepository.findAll().stream()
            .filter(e -> e.getVehiculo().getId().equals(id))
            .map(e -> new EstanciaDTO(
                e.getId(),
                e.getVehiculo().getId(),
                e.getHoraEntrada(),
                e.getHoraSalida(),
                e.getHorasPagadas()))
            .toList();
    }

    @PostMapping
    public VehiculoDTO createVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
        Integer usuarioId = vehiculo.getUsuario().getId();
        Usuario usuarioCompleto = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + usuarioId));
        vehiculo.setUsuario(usuarioCompleto);

        Vehiculo saved = vehiculoRepository.save(vehiculo);
        return new VehiculoDTO(saved.getId(), saved.getMatricula(), saved.getUsuario().getId());
    }

    @PutMapping("/{id}")
    public VehiculoDTO updateVehiculo(@PathVariable Integer id, @Valid @RequestBody Vehiculo vehiculoActualizado) {
        Vehiculo updated = vehiculoRepository.findById(id)
            .map(vehiculo -> {
                vehiculo.setMatricula(vehiculoActualizado.getMatricula());
                Integer usuarioId = vehiculoActualizado.getUsuario().getId();
                Usuario usuarioCompleto = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + usuarioId));
                vehiculo.setUsuario(usuarioCompleto);
                return vehiculoRepository.save(vehiculo);
            })
            .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con id " + id));
        return new VehiculoDTO(updated.getId(), updated.getMatricula(), updated.getUsuario().getId());
    }

    @DeleteMapping("/{id}")
    public void deleteVehiculo(@PathVariable Integer id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("Vehiculo no encontrado con id " + id);
        }
        vehiculoRepository.deleteById(id);
    }
}