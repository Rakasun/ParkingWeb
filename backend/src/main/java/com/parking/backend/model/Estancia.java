package com.parking.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="estancias")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Estancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El vehículo no puede ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @NotNull(message = "La hora de entrada no puede ser nula")
    @Column(name = "hora_entrada", nullable = false)
    private LocalDateTime horaEntrada;

    @Column(name = "hora_salida", insertable = false, updatable = false)
    private LocalDateTime horaSalida;

    @Min(value = 1, message = "Las horas pagadas deben ser al menos 1")
    @Column(name = "horas_pagadas", nullable = false)
    private Integer horasPagadas;

    @Column(name = "hora_limite_salida", insertable = false, updatable = false)
    private LocalDateTime horaLimiteSalida;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public LocalDateTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalDateTime horaEntrada) { this.horaEntrada = horaEntrada; }

    public LocalDateTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalDateTime horaSalida) { this.horaSalida = horaSalida; }

    public Integer getHorasPagadas() { return horasPagadas; }
    public void setHorasPagadas(Integer horasPagadas) { this.horasPagadas = horasPagadas; }

    public LocalDateTime getHoraLimiteSalida() { return horaLimiteSalida; }
    public void setHoraLimiteSalida(LocalDateTime horaLimiteSalida) { this.horaLimiteSalida = horaLimiteSalida; }
}
