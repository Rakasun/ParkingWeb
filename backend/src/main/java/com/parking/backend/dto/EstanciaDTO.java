package com.parking.backend.dto;

import java.time.LocalDateTime;

public class EstanciaDTO {
    private Integer id;
    private VehiculoId vehiculo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private Integer horasPagadas;

    public EstanciaDTO(Integer id, Integer vehiculoId, LocalDateTime horaEntrada, LocalDateTime horaSalida, Integer horasPagadas) {
        this.id = id;
        this.vehiculo = new VehiculoId(vehiculoId);
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasPagadas = horasPagadas;
    }

    public Integer getId() { return id; }
    public VehiculoId getVehiculo() { return vehiculo; }
    public LocalDateTime getHoraEntrada() { return horaEntrada; }
    public LocalDateTime getHoraSalida() { return horaSalida; }
    public Integer getHorasPagadas() { return horasPagadas; }

    public static class VehiculoId {
        private Integer id;
        public VehiculoId(Integer id) { this.id = id; }
        public Integer getId() { return id; }
    }
}
