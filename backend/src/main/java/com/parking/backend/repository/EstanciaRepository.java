package com.parking.backend.repository;

import com.parking.backend.model.Estancia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstanciaRepository extends JpaRepository<Estancia, Integer> {
    
}