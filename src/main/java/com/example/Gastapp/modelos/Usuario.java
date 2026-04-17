package com.example.Gastapp.modelos;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "usuarios")
public class Usuario {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //id, nombre, tipodoc, documento, edad, correoElectronico, telefono, direccion, estadoCuenta, fechaRegistro
   @Column(name = "id", nullable = false, unique = true)
private Long id;

@Column(name = "nombre", nullable = false, length = 100)
private String nombre;

@Column(name = "tipo_documento", nullable = false, length = 20)
private String tipodoc;

@Column(name = "documento", nullable = false, unique = true, length = 20)
private String documento;

@Column(name = "edad")
private Integer edad;

@Column(name = "correo_electronico", nullable = false, unique = true, length = 100)
private String correoElectronico;

@Column(name = "telefono", length = 20)
private String telefono;

@Column(name = "direccion", length = 150)
private String direccion;

@Column(name = "estado_cuenta", nullable = false, length = 50)
private String estadoCuenta;

@Column(name = "fecha_registro", nullable = false)
private LocalDate fechaRegistro;

    public Usuario() {
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
    public String getTipodoc() {
        return tipodoc;
    }
    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getEstadoCuenta() {
        return estadoCuenta;
    }
    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
