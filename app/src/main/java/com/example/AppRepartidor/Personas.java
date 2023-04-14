package com.example.AppRepartidor;

public class Personas {
    String fecha,id;

    private String estadoPedido;
    private float calificacion;

    public Personas(String id, String estadoPedido,String fecha, float calificacion) {
        this.fecha = fecha;
        this.id = id;
        this.estadoPedido = estadoPedido;
        this.calificacion = calificacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

}
