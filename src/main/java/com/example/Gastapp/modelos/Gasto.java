package com.example.Gastapp.modelos;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "gastos")

public class Gasto {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY )
     //id, descripcion, fecha, valor, icono, referenciaTransaccion, canalCompra, cantidadItems, estadoGasto, numeroComprobante
    private Long id;
@Column(nullable = false, length = 255)
private String descripcion;
@Column(nullable = false)
private LocalDate fecha;
@Column(nullable = false)
private Double valor;
@Column(length = 50)
private String icono;
@Column(length = 100)
private String referenciaTransaccion;
@Column(length = 50)
private String canalCompra;
@Column(nullable = false)
private Integer cantidadItems;
@Column(nullable = false, length = 50)
private String estadoGasto;
@Column(length = 100)
private String numeroComprobante;
     public Gasto() {
     }
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
    
    
}
