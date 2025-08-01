package com.parking.backend.controller;

import com.parking.backend.model.Estancia;
import com.parking.backend.dto.EstanciaDTO;
import com.parking.backend.repository.EstanciaRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.parking.backend.repository.VehiculoRepository;
import com.parking.backend.model.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/estancias")
public class EstanciaController {
    private final EstanciaRepository estanciaRepository;

    public EstanciaController(EstanciaRepository estanciaRepository) {
        this.estanciaRepository = estanciaRepository;
    }

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @GetMapping
    public List<EstanciaDTO> getAllEstancias() {
        return estanciaRepository.findAll().stream()
            .map(e -> new EstanciaDTO(
                e.getId(),
                e.getVehiculo().getId(),
                e.getHoraEntrada(),
                e.getHoraSalida(),
                e.getHorasPagadas()))
            .toList();
    }

    @GetMapping("/{id}")
    public EstanciaDTO getEstanciaById(@PathVariable Integer id) {
        Estancia e = estanciaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estancia no encontrada con id " + id));
        return new EstanciaDTO(
            e.getId(),
            e.getVehiculo().getId(),
            e.getHoraEntrada(),
            e.getHoraSalida(),
            e.getHorasPagadas());
    }

    @PostMapping
    public EstanciaDTO createEstancia(@Valid @RequestBody Estancia estancia) {
        Integer vehiculoId = estancia.getVehiculo().getId();
        Vehiculo vehiculoCompleto = vehiculoRepository.findById(vehiculoId)
            .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con id " + vehiculoId));
        estancia.setVehiculo(vehiculoCompleto);

        Estancia saved = estanciaRepository.save(estancia);
        return new EstanciaDTO(
            saved.getId(),
            saved.getVehiculo().getId(),
            saved.getHoraEntrada(),
            saved.getHoraSalida(),
            saved.getHorasPagadas());
    }

    @PutMapping("/{id}")
    public EstanciaDTO updateEstancia(@PathVariable Integer id, @Valid @RequestBody Estancia estanciaActualizada) {
        Estancia updated = estanciaRepository.findById(id)
            .map(estancia -> {
                Integer vehiculoId = estanciaActualizada.getVehiculo().getId();
                Vehiculo vehiculoCompleto = vehiculoRepository.findById(vehiculoId)
                    .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con id " + vehiculoId));
                estancia.setVehiculo(vehiculoCompleto);
                estancia.setHoraEntrada(estanciaActualizada.getHoraEntrada());
                estancia.setHoraSalida(estanciaActualizada.getHoraSalida());
                estancia.setHorasPagadas(estanciaActualizada.getHorasPagadas());
                return estanciaRepository.save(estancia);
            })
            .orElseThrow(() -> new RuntimeException("Estancia no encontrada con id " + id));
        return new EstanciaDTO(
            updated.getId(),
            updated.getVehiculo().getId(),
            updated.getHoraEntrada(),
            updated.getHoraSalida(),
            updated.getHorasPagadas());
    }

    @DeleteMapping("/{id}")
    public void deleteEstancia(@PathVariable Integer id) {
        if (!estanciaRepository.existsById(id)) {
            throw new RuntimeException("Estancia no encontrado con id " + id);
        }
        estanciaRepository.deleteById(id);
    }
}