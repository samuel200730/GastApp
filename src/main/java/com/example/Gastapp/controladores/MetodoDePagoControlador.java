package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.MetodoDePago;
import com.example.Gastapp.servicios.MetodoDePagoServicio;

@RestController
@RequestMapping("/api/metodos_de_pago")
public class MetodoDePagoControlador {

    @Autowired
    private MetodoDePagoServicio servicio;

    // GUARDAR
    @PostMapping
    public MetodoDePago guardar(@RequestBody MetodoDePago datos){

        return servicio.guardar(datos);
    }

    // LISTAR
    @GetMapping
    public List<MetodoDePago> listar(){

        return servicio.listar();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public MetodoDePago buscarPorId(@PathVariable Long id){

        return servicio.buscarPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public MetodoDePago actualizar(
            @PathVariable Long id,
            @RequestBody MetodoDePago datos){

        return servicio.actualizar(id, datos);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){

        servicio.eliminar(id);
    }
}