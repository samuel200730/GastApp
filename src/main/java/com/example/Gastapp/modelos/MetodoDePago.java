package com.example.Gastapp.modelos;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "metodos_de_pago")
public class MetodoDePago {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      //id, nombre, franquicia, estado, descripcion, cupoDisponible, fechaVencimiento, numeroReferencia, bancoEmisor, tipoMetodo

private Long id;

@Column(name = "nombre", nullable = false, length = 100)
private String nombre;

@Column(name = "franquicia", nullable = false, length = 50)
private String franquicia;

@Column(name = "estado", nullable = false, length = 50)
private String estado;

@Column(name = "descripcion", length = 255)
private String descripcion;

@Column(name = "cupo_disponible", nullable = false)
private Double cupoDisponible;

@Column(name = "fecha_vencimiento", nullable = false)
private LocalDate fechaVencimiento;

@Column(name = "numero_referencia", unique = true, length = 50)
private String numeroReferencia;

@Column(name = "banco_emisor", length = 100)
private String bancoEmisor;

@Column(name = "tipo_metodo", nullable = false, length = 50)
private String tipoMetodo;

@ManyToOne
@JoinColumn(name = "fk_usuario", referencedColumnName = "id")
private Usuario usuario;


    public MetodoDePago() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getFranquicia() {
        return franquicia;
    }
    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Double getCupoDisponible() {
        return cupoDisponible;
    }
    public void setCupoDisponible(Double cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public String getNumeroReferencia() {
        return numeroReferencia;
    }
    public void setNumeroReferencia(String numeroReferencia) {
        this.numeroReferencia = numeroReferencia;
    }
    public String getBancoEmisor() {
        return bancoEmisor;
    }
    public void setBancoEmisor(String bancoEmisor) {
        this.bancoEmisor = bancoEmisor;
    }
    public String getTipoMetodo() {
        return tipoMetodo;
    }
    public void setTipoMetodo(String tipoMetodo) {
        this.tipoMetodo = tipoMetodo;
    }
  
    
}