package com.parking.backend.dto;

public class VehiculoDTO {
    private Integer id;
    private String matricula;
    private UsuarioId usuario;

    public VehiculoDTO(Integer id, String matricula, Integer usuarioId) {
        this.id = id;
        this.matricula = matricula;
        this.usuario = new UsuarioId(usuarioId);
    }

    public Integer getId() { return id; }
    public String getMatricula() { return matricula; }
    public UsuarioId getUsuario() { return usuario; }

    public static class UsuarioId {
        private Integer id;
        public UsuarioId(Integer id) { this.id = id; }
        public Integer getId() { return id; }
    }
}
