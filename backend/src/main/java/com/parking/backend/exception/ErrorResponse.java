package com.parking.backend.exception;

public class ErrorResponse {
    private String mensaje;
    private String detalle;

    public ErrorResponse(String mensaje, String detalle) {
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public String getMensaje() { return mensaje; }
    public String getDetalle() { return detalle; }
}