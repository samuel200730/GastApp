package com.example.Gastapp.modelos;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "gastos")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Double valor;

    private String icono;
    private String referenciaTransaccion;
    private String canalCompra;

    @Column(nullable = false)
    private Integer cantidadItems;

    @Column(nullable = false)
    private String estadoGasto;

    private String numeroComprobante;

    // ❌ NO hay relación con Usuario

    @JsonIgnore
    @OneToMany(mappedBy = "gasto")
    private List<Comercio> comercios;

    @JsonIgnore
    @OneToMany(mappedBy = "gasto")
    private List<Categoria> categorias;

    public Gasto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getReferenciaTransaccion() {
        return referenciaTransaccion;
    }

    public void setReferenciaTransaccion(String referenciaTransaccion) {
        this.referenciaTransaccion = referenciaTransaccion;
    }

    public String getCanalCompra() {
        return canalCompra;
    }

    public void setCanalCompra(String canalCompra) {
        this.canalCompra = canalCompra;
    }

    public Integer getCantidadItems() {
        return cantidadItems;
    }

    public void setCantidadItems(Integer cantidadItems) {
        this.cantidadItems = cantidadItems;
    }

    public String getEstadoGasto() {
        return estadoGasto;
    }

    public void setEstadoGasto(String estadoGasto) {
        this.estadoGasto = estadoGasto;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public List<Comercio> getComercios() {
        return comercios;
    }

    public void setComercios(List<Comercio> comercios) {
        this.comercios = comercios;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}