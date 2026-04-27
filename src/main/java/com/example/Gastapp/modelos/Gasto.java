package com.example.Gastapp.modelos;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "gastos")

public class Gasto {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY )
     //id, descripcion, fecha, valor, icono, referenciaTransaccion, canalCompra, cantidadItems, estadoGasto, numeroComprobante

private Long id;

@Column(name = "descripcion", nullable = false, length = 255)
private String descripcion;

@Column(name = "fecha", nullable = false)
private LocalDate fecha;

@Column(name = "valor", nullable = false)
private Double valor;

@Column(name = "icono", length = 50)
private String icono;

@Column(name = "referencia_transaccion", length = 100)
private String referenciaTransaccion;

@Column(name = "canal_compra", length = 50)
private String canalCompra;

@Column(name = "cantidad_items", nullable = false)
private Integer cantidadItems;

@Column(name = "estado_gasto", nullable = false, length = 50)
private String estadoGasto;

@Column(name = "numero_comprobante", length = 100)
private String numeroComprobante;

// creando una relacion con el modelo usuario
// yo como gasto me relaciono con un usuario
@ManyToOne
@JoinColumn(name = "fk_usuario", referencedColumnName = "id")
private Usuario usuario;

@OneToMany(mappedBy = "gasto")
private List<Comercio> comercios;


@OneToMany(mappedBy = "gasto")
private List<Categoria> categorias;

@ManyToOne
@JoinColumn(name = "usuario_id")
private Usuario usuario1;

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
