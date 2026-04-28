package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.Comercio;
import com.example.Gastapp.servicios.ComercioServicio;

@RestController
@RequestMapping("/api/comercios")
public class ComercioControlador {

    @Autowired
    private ComercioServicio servicio;

    @PostMapping
    public Comercio guardar(@RequestBody Comercio datos){
        return servicio.guardar(datos);
    }

    @GetMapping
    public List<Comercio> listar(){
        return servicio.listar();
    }
}