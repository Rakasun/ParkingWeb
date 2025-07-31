package com.parking.backend.controller;

import com.parking.backend.model.Vehiculo;
import com.parking.backend.repository.VehiculoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoRepository vehiculoRepository;

    public VehiculoController(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @GetMapping
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }

    @PostMapping
    public Vehiculo createVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }
}