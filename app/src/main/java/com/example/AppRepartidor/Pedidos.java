package com.example.AppRepartidor;

public class Pedidos {
    private String estadoPedido,fecha,total,nombre,telefono;
    private Integer idpedido,idcliente;
    private float calificacion;

    public Pedidos(Integer idpedido, String estadoPedido, float calificacion,String fecha,String total,String nombre, String telefono,Integer idcliente) {
        this.estadoPedido = estadoPedido;
        this.calificacion = calificacion;
        this.fecha = fecha;
        this.idpedido = idpedido;
        this.total = total;
        this.nombre = nombre;
        this.telefono = telefono;
        this.idcliente =idcliente;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public Integer getIdpedido() {
        return idpedido;
    }

    public String getTotal() {
        return total;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public Integer getIdcliente() {
        return idcliente;
    }
}
