package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.Categoria;
import com.example.Gastapp.servicios.CategoriaServicio;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio servicio;

    // GUARDAR
    @PostMapping
    public Categoria guardar(@RequestBody Categoria datos){

        return servicio.guardar(datos);
    }

    // LISTAR
    @GetMapping
    public List<Categoria> listar(){

        return servicio.listar();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id){

        return servicio.buscarPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public Categoria actualizar(
            @PathVariable Long id,
            @RequestBody Categoria datos){

        return servicio.actualizar(id, datos);
    }

    // ELIMINAR
  @DeleteMapping("/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Long id){
    servicio.eliminar(id);
    return ResponseEntity.noContent().build();
}
}