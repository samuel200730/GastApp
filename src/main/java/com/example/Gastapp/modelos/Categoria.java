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
@Table(name = "categorias")
public class Categoria {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
//id, nombre, fechaCreacion, responsable, justificacion, presupuestoAsignado, colorIdentificador, limiteMensual, tipoCategoria, nivelImportancia

private Long id;

@Column(name = "nombre", nullable = false, length = 100)
private String nombre;

@Column(name = "fecha_creacion", nullable = false)
private LocalDate fechaCreacion;

@Column(name = "responsable", nullable = false, length = 100)
private String responsable;

@Column(name = "justificacion", nullable = false, length = 255)
private String justificacion;

@Column(name = "presupuesto_asignado", nullable = false)
private Double presupuestoAsignado;

@Column(name = "color_identificador", length = 50)
private String colorIdentificador;

@Column(name = "limite_mensual", nullable = false)
private Double limiteMensual;

@Column(name = "tipo_categoria", nullable = false, length = 50)
private String tipoCategoria;

@Column(name = "nivel_importancia", nullable = false, length = 50)
private String nivelImportancia;

@ManyToOne
@JoinColumn(name = "fk_gasto", referencedColumnName = "id")
private Gasto gasto;

public Categoria() {
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
public LocalDate getFechaCreacion() {
    return fechaCreacion;
}
public void setFechaCreacion(LocalDate fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
}
public String getResponsable() {
    return responsable;
}
public void setResponsable(String responsable) {
    this.responsable = responsable;
}
public String getJustificacion() {
    return justificacion;
}
public void setJustificacion(String justificacion) {
    this.justificacion = justificacion;
}
public Double getPresupuestoAsignado() {
    return presupuestoAsignado;
}
public void setPresupuestoAsignado(Double presupuestoAsignado) {
    this.presupuestoAsignado = presupuestoAsignado;
}
public String getColorIdentificador() {
    return colorIdentificador;
}
public void setColorIdentificador(String colorIdentificador) {
    this.colorIdentificador = colorIdentificador;
}
public Double getLimiteMensual() {
    return limiteMensual;
}
public void setLimiteMensual(Double limiteMensual) {
    this.limiteMensual = limiteMensual;
}
public String getTipoCategoria() {
    return tipoCategoria;
}
public void setTipoCategoria(String tipoCategoria) {
    this.tipoCategoria = tipoCategoria;
}
public String getNivelImportancia() {
    return nivelImportancia;
}
public void setNivelImportancia(String nivelImportancia) {
    this.nivelImportancia = nivelImportancia;
}

}
