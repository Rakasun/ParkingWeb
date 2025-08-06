package com.parking.backend.repository;

import com.parking.backend.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    List<Vehiculo> findByUsuarioId(Integer usuarioId);
}