package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.Gasto;
import com.example.Gastapp.servicios.GastoServicio;

@RestController
@RequestMapping("/api/gastos")
public class GastoControlador {

    @Autowired
    private GastoServicio servicio;

    // GUARDAR
    @PostMapping
    public Gasto guardar(@RequestBody Gasto datos){

        return servicio.guardar(datos);
    }

    // LISTAR
    @GetMapping
    public List<Gasto> listar(){

        return servicio.listar();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public Gasto buscarPorId(@PathVariable Long id){

        return servicio.buscarPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public Gasto actualizar(
            @PathVariable Long id,
            @RequestBody Gasto datos){

        return servicio.actualizar(id, datos);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){

        servicio.eliminar(id);
    }
}