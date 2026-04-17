package com.example.Gastapp.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "comercios")
public class Comercio {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //id, nit, nombre, actividad, contacto, direccion, ciudad, telefono, estadoComercio, tipoEstablecimiento
@Column(name = "id", nullable = false, unique = true)
private Long id;

@Column(name = "nit", nullable = false, unique = true, length = 20)
private String nit;

@Column(name = "nombre", nullable = false, length = 100)
private String nombre;

@Column(name = "actividad", nullable = false, length = 100)
private String actividad;

@Column(name = "contacto", length = 100)
private String contacto;

@Column(name = "direccion", length = 150)
private String direccion;

@Column(name = "ciudad", length = 100)
private String ciudad;

@Column(name = "telefono", length = 20)
private String telefono;

@Column(name = "estado_comercio", nullable = false, length = 50)
private String estadoComercio;

@Column(name = "tipo_establecimiento", nullable = false, length = 50)
private String tipoEstablecimiento;
    public Comercio() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getActividad() {
        return actividad;
    }
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
    public String getContacto() {
        return contacto;
    }
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEstadoComercio() {
        return estadoComercio;
    }
    public void setEstadoComercio(String estadoComercio) {
        this.estadoComercio = estadoComercio;
    }
    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }
    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }
    
}
