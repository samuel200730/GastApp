package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.Comercio;
import com.example.Gastapp.servicios.ComercioServicio;

@RestController
@RequestMapping("/api/comercios")
public class ComercioControlador {

    @Autowired
    private ComercioServicio servicio;

    // GUARDAR
    @PostMapping
    public Comercio guardar(@RequestBody Comercio datos){

        return servicio.guardar(datos);
    }

    // LISTAR
    @GetMapping
    public List<Comercio> listar(){

        return servicio.listar();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public Comercio buscarPorId(@PathVariable Long id){

        return servicio.buscarPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public Comercio actualizar(
            @PathVariable Long id,
            @RequestBody Comercio datos){

        return servicio.actualizar(id, datos);
    }

    // ELIMINAR
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Long id){
    servicio.eliminar(id);
    return ResponseEntity.noContent().build();
}
}